<script setup>
import { h, ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { Axios } from "@/util/http-common";
import { WarningOutlined } from "@ant-design/icons-vue";
import { Empty, notification } from "ant-design-vue";

import BoardSideNavigation from "@/components/boards/item/BoardSideNavigation.vue";
import BoardCardItem from "@/components/boards/item/BoardCardItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

const router = useRouter();

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_LIST_SIZE } = import.meta.env;
const param = ref({
  pgno: currentPage.value,
  spp: VITE_LIST_SIZE,
  key: "",
  word: "",
  type: "",
});

const simpleImage = Empty.PRESENTED_IMAGE_SIMPLE;

onMounted(() => {
  getArticleList();
});

const http = Axios();
const member = ref({});

const getArticleList = () => {
  console.log("서버에서 글목록 얻어오자!!!", param.value);
  http.get(`/member/myarticles`, {
    params: {
      pgno: param.value.pgno,
      spp: param.value.spp,
      key: param.value.key,
      word: param.value.word,
      type: param.value.type,
    },
    headers: {
      'Authorization': `Bearer ${localStorage.getItem("jwt")}`,
      'Content-Type': 'application/json',
    }
  })
  .then(({ data }) => {
    console.log(data);
    articles.value = data.articles;
    currentPage.value = data.currentPage;
    totalPage.value = data.totalPageCount;
  })
  .catch((error) => {
    console.error("Error while getting Article", error);
  });
};

const selectedType = (type) => {
  param.value.type = String(type);
  getArticleList();
};

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  currentPage.value = val;
  param.value.pgno = val;
  getArticleList();
};

const moveWrite = () => {
  router.push({ name: "article-write" });
};

const [api, contextHolder] = notification.useNotification();
const open = (placement) => openNotification(placement);
const openNotification = (placement) => {
  http.get(`member/me`, {
    headers: {
      'Authorization': `Bearer ${localStorage.getItem("jwt")}`,
    },
  })
  .then(({ data }) => {
    member.value = data;
    moveWrite();
  })
  .catch((error) => {
    api.info({
      message: `로그인 필요`,
      description: "로그인이 필요한 기능입니다 로그인을 해주세요",
      placement,
      icon: () =>
        h(WarningOutlined, {
          style: "color: #920101",
        }),
    });
  });
};
</script>

<template>
  <div class="container mt-5">
    <div class="mb-5">
      <h3 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="island_mark_style">내 게시글</mark>
      </h3>
    </div>
    <div class="row">
      <div class="col">
        <div class="row align-self-center mb-2">
          <div class="col offset-8">
            <form class="d-flex">
              <div class="input-group input-group-sm">
                <input
                  type="text"
                  class="form-control"
                  v-model="param.word"
                  placeholder="검색어..."
                />
                <button class="btn island_button_style" type="button" @click="getArticleList">
                  검색
                </button>
              </div>
            </form>
          </div>
        </div>
        <div class="row">
          <div v-if="articles == ''" class="col-lg-8 mt-5">
            <a-empty :image="simpleImage" />
            <a-alert
              message="글이 없어요"
              description="새로운 글을 만들어 주시겠어요?"
              type="error"
              show-icon
            >
              <template #icon><smile-outlined /></template>
              <template #action>
                <a-button size="small" danger @click="() => open('topLeft')">글쓰러 가기</a-button>
              </template>
            </a-alert>
          </div>
          <BoardCardItem v-for="article in articles" :key="article.articleNo" :article="article">
          </BoardCardItem>
        </div>
      </div>
    </div>
    <div class="row bottom mt-3">
      <div class="col text-center">
        <a-pagination
          v-model:current="currentPage"
          :total="totalPage * 10"
          @change="onPageChange"
        />
      </div>
      <div class="col-1">
        <a-space>
          <a-button type="primary" @click="() => open('topLeft')">
            <RadiusUpleftOutlined />
            글쓰기
          </a-button>
        </a-space>
        <contextHolder />
      </div>
    </div>
  </div>
</template>

<style scoped>
.island_color {
  color: #920101;
}
.island_button_style {
  background-color: #920101;
  color: white;
}

.nav {
  margin: 0;
  padding: 0;
  list-style: none;
}
</style>
