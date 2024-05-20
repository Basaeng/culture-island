<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registArticle, modifyArticle, detailArticle } from "@/api/board";
import { Axios } from "@/util/http-common";

onMounted(() => {
  getMemberDetails();
});

const http = Axios();
const member = ref({});

const getMemberDetails = () => {
  http.get(`member/me`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('jwt')}`
    }
  }).then(({data})=>{
      member.value = data;
      console.log("member : " + member.value.id + " " + member.value.name)
  }).catch ((error)=>{
    console.error('Failed to fetch user details', error);
  });
};

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String, member:Object });

const isUseId = ref(false);

const article = ref({
  subject: "",
  content: "",
  name: "",
  memberId: "", 
  type: "",
  pay: "",
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
    if (len == 0 || len > 500) {
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
      modalStatus.value = "success"
      modalTitle.value = "작성 완료"
      modalSubTitle.value = "글을 작성하였어요, 목록으로 돌아갈까요?"
    },
    (error) => {
      modalStatus.value = "error"
      modalTitle.value = "작성 실패"
      modalSubTitle.value = "글을 작성하는데 실패했어요, 목록으로 돌아갈까요?"
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
      modalStatus.value = "success"
      modalTitle.value = "수정 완료"
      modalSubTitle.value = "글을 수정하였어요, 목록으로 돌아갈까요?"
    },
    (error) => {
      console.log(error);
      modalStatus.value = "error"
      modalTitle.value = "수정 실패"
      modalSubTitle.value = "글을 수정하는데 실패했어요, 목록으로 돌아갈까요?"
    }
  );
}

function moveList() {
  router.push({ name: "article-list" });
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <!-- <div class="mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.name"
        :disabled="isUseId"
        placeholder="작성자ID..."
      />
    </div> -->
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
      <textarea class="form-control" v-model="article.content" rows="10"></textarea>
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
        <a-result
          :status="`${modalStatus}`"
          :title="`${modalTitle}`"
          :sub-title="`${modalSubTitle}`"
        >
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
