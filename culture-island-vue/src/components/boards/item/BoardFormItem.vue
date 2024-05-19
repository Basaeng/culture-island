<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registArticle, getModifyArticle, modifyArticle } from "@/api/board";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const isUseId = ref(false);

const article = ref({
  // articleNo: 0,
  subject: "",
  content: "",
  name: "",
  memberId: 1, // dummy data
  type: "",
  pay: "",
});

if (props.type === "modify") {
  let { articleno } = route.params;
  console.log(articleno + "번글 얻어와서 수정할거야");
  getModifyArticle(
    articleno,
    ({ data }) => {
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
  // event.preventDefault();

  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  console.log("글등록하자!!", article.value);
  article.value.name = "ssafy"; // 더미
  open.value = true;
  registArticle(
    article.value,
    (response) => {
      if (response.status == 201) msg = "글등록이 완료되었습니다.";
      // alert(msg);
    },
    (error) => {
      httpRequest.value = false;
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
      let msg = "글수정 처리시 문제 발생했습니다.";
      if (response.status == 200) msg = "글정보 수정이 완료되었습니다.";
      // alert(msg);
      moveList();
      // router.push({ name: "article-view" });
      // router.push(`/board/view/${article.value.articleNo}`);
    },
    (error) => {
      httpRequest.value = false;
      console.log(error);
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
      <div v-if="httpRequest === true">
        <a-result
          status="success"
          title="작성 완료"
          sub-title="글을 작성하였어요, 목록으로 돌아갈까요?"
        >
        </a-result>
      </div>
      <div v-else>
        <a-result
          status="error"
          title="작성 실패"
          sub-title="글을 작성하는데 실패했어요, 목록으로 돌아갈까요?"
        >
        </a-result>
      </div>
    </a-modal>
  </div>
</template>

<style scoped>
.island_button_style {
  background-color: #920101;
  color: white;
}
</style>
