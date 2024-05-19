<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/board.js";

import BoardSideNavigation from "./item/BoardSideNavigation.vue";
import BoardCardItem from "./item/BoardCardItem.vue";
import BoardListItem from "@/components/boards/item/BoardListItem.vue";
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

onMounted(() => {
  getArticleList();
});

const getArticleList = () => {
  console.log("서버에서 글목록 얻어오자!!!", param.value);
  listArticle(
    param.value,
    ({ data }) => {
      console.log(data);
      articles.value = data.articles;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.log(error);
    }
  );
};

const selectedType = (type) => {
  param.value.type = String(type);
  // console.log(param.value);
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
</script>

<template>
  <div class="container mt-5">
    <div class="row">
      <div class="align-middle col-lg-3">
        <BoardSideNavigation @selected="selectedType" />
      </div>
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
        <!-- <PageNavigation
          :current-page="currentPage"
          :total-page="totalPage"
          @pageChange="onPageChange"
        ></PageNavigation> -->
      </div>
      <div class="col-1">
        <button
          type="button"
          class="btn btn-outline-primary btn-sm island_button_style"
          @click="moveWrite"
        >
          글쓰기
        </button>
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
