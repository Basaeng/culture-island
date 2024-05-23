<script setup>
import { reactive, ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registArticle, modifyArticle, detailArticle, uploadImage } from "@/api/board";
import { QuillEditor } from "@vueup/vue-quill";
import { Axios } from "@/util/http-common";
import "@vueup/vue-quill/dist/vue-quill.snow.css";

const { VITE_API_URL } = import.meta.env;

onMounted(() => {
  getMemberDetails();
});

const http = Axios();
const member = ref({});

const getMemberDetails = () => {
  http
    .get(`member/me`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("jwt")}`,
      },
    })
    .then(({ data }) => {
      member.value = data;
      console.log("member : " + member.value.id + " " + member.value.name);
    })
    .catch((error) => {
      console.error("Failed to fetch user details", error);
    });
};

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String, member: Object });

const isUseId = ref(false);

const article = ref({
  subject: "",
  content: "",
  name: "",
  memberId: "",
  type: "",
  pay: "",
  fileInfos: [],
});

if (props.type === "modify") {
  let { articleno } = route.params;
  console.log(articleno + "번글 얻어와서 수정할거야");
  detailArticle(
    articleno,
    ({ data }) => {
      console.log("data : " + data);
      article.value = data;
      isUseId.value = true;
    },
    (error) => {
      console.log(error);
    }
  );
  isUseId.value = true;
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

const selectTypeOptions = ref([
  {
    value: "클래식",
    label: "클래식",
  },
  {
    value: "뮤지컬",
    label: "뮤지컬",
  },
  {
    value: "전시",
    label: "전시",
  },
  {
    value: "연극",
    label: "연극",
  },
  {
    value: "무용",
    label: "무용",
  },
  {
    value: "국악",
    label: "국악",
  },
]);

const selectPayOptions = ref([
  {
    value: 0,
    label: "무료",
  },
  {
    value: 1,
    label: "유료",
  },
]);

const quillState = reactive({
  text: "",
  content: "",
  editorOption: {
    placeholder: "내용을 입력해주세요...",
    modules: {
      toolbar: [
        ["bold", "italic", "underline", "strike"],
        ["blockquote", "code-block"],
        [{ header: 1 }, { header: 2 }],
        [{ list: "ordered" }, { list: "bullet" }],
        [{ script: "sub" }, { script: "super" }],
        [{ indent: "-1" }, { indent: "+1" }],
        [{ direction: "rtl" }],
        [{ size: ["small", false, "large", "huge"] }],
        [{ header: [1, 2, 3, 4, 5, 6, false] }],
        [{ color: [] }, { background: [] }],
        [{ font: [] }],
        [{ align: [] }],
        ["clean"],
        ["link", "image", "video"],
      ],
    },
  },
  editorInstance: null,
  disabled: false,
});

const onTextChange = (delta, oldDelta, source) => {
  const quill = quillState.editorInstance;
  quillState.content = quill.root.innerHTML;
  quillState.text = quill.getText();

  article.value.content = quillState.content;
};

function imageHandler() {
  const input = document.createElement("input");
  input.setAttribute("type", "file");
  input.setAttribute("accept", "image/*");
  input.click();

  input.onchange = async () => {
    const file = input.files[0];
    if (file) {
      const formData = new FormData();
      formData.append("image", file);

      try {
        const response = await uploadImage(formData);
        const imageUrl = response.data;

        const quill = quillState.editorInstance;
        const range = quill.getSelection();
        const fullUrl = VITE_API_URL + "/board/files/" + imageUrl;
        quill.insertEmbed(range.index, "image", fullUrl);
        const url = "";

        article.value.fileInfos.push({
          originalFile: imageUrl,
          saveFile: fullUrl,
          saveFolder: url,
        });
        console.log(fullUrl);
      } catch (error) {
        console.log(error);
      }
    }
  };
}

const selectType = ref();
const selectPay = ref();

const typeChange = (type) => {
  article.value.type = type;
};

const payChange = (pay) => {
  article.value.pay = pay;
};

const open = ref(false);
const confirmLoading = ref(false);
const httpRequest = ref(true);

const handleOk = () => {
  confirmLoading.value = true;
  setTimeout(() => {
    open.value = false;
    confirmLoading.value = false;
    moveList();
  }, 1500);
};

function onSubmit() {
  article.value.name = member.value.name;
  article.value.memberId = member.value.id;
  article.value.content = quillState.content;

  console.log(article.value);

  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

const modalStatus = ref("");
const modalTitle = ref("");
const modalSubTitle = ref("");

function writeArticle() {
  console.log("글등록하자!!", article.value);
  open.value = true;
  registArticle(
    article.value,
    (response) => {
      modalStatus.value = "success";
      modalTitle.value = "작성 완료";
      modalSubTitle.value = "글을 작성하였어요, 목록으로 돌아갈까요?";
    },
    (error) => {
      modalStatus.value = "error";
      modalTitle.value = "작성 실패";
      modalSubTitle.value = "글을 작성하는데 실패했어요, 목록으로 돌아갈까요?";
      console.log(error);
    }
  );
}

function updateArticle() {
  console.log(article.value.articleNo + "번글 수정하자!!", article.value);
  open.value = true;
  modifyArticle(
    article.value,
    (response) => {
      modalStatus.value = "success";
      modalTitle.value = "수정 완료";
      modalSubTitle.value = "글을 수정하였어요, 목록으로 돌아갈까요?";
    },
    (error) => {
      console.log(error);
      modalStatus.value = "error";
      modalTitle.value = "수정 실패";
      modalSubTitle.value = "글을 수정하는데 실패했어요, 목록으로 돌아갈까요?";
    }
  );
}

function moveList() {
  router.push({ name: "article-list" });
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="row">
      <div class="col">
        <label for="subject" class="form-label">제목 : </label>
        <input type="text" class="form-control" v-model="article.subject" placeholder="제목..." />
      </div>
      <div class="col-sm-3">
        <label for="subject" class="form-label">구분 : </label>
        <a-select
          v-model:value="selectType"
          show-search
          placeholder="구분..."
          style="width: 100%"
          :options="selectTypeOptions"
          @change="typeChange"
        ></a-select>
      </div>
      <div class="col-sm-3">
        <label for="subject" class="form-label">가격 : </label>
        <a-select
          v-model:value="selectPay"
          show-search
          placeholder="유무료 여부..."
          style="width: 100%"
          :options="selectPayOptions"
          @change="payChange"
        ></a-select>
      </div>
    </div>
    <div class="mb-3 mt-3">
      <label for="content" class="form-label">내용 : </label>
      <!-- <textarea class="form-control" v-model="article.content" rows="10"></textarea> -->
      <QuillEditor
        theme="snow"
        v-model:value="quillState.content"
        :options="quillState.editorOption"
        @text-change="onTextChange"
        @ready="
          (quill) => {
            quillState.editorInstance = quill;
            quill.getModule('toolbar').addHandler('image', function () {
              imageHandler();
            });
          }
        "
        style="height: 400px"
      />
    </div>
    <div class="col-auto text-center">
      <button
        type="submit"
        class="btn btn-outline-primary mb-3 island_button_style"
        v-if="type === 'regist'"
      >
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
      <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">
        목록으로
      </button>
    </div>
  </form>
  <div>
    <a-modal v-model:open="open" title="" :confirm-loading="confirmLoading" @ok="handleOk">
      <a-result :status="`${modalStatus}`" :title="`${modalTitle}`" :sub-title="`${modalSubTitle}`">
      </a-result>
    </a-modal>
  </div>
</template>

<style scoped>
.island_button_style {
  background-color: #920101;
  color: white;
}
</style>
