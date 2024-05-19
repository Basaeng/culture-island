<script setup>
import { ref, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
const { VITE_CULTURE_API_URL, VITE_ARTICLE_LIST_SIZE } = import.meta.env;
import PageNavigation from "@/components/common/PageNavigation.vue";
import CultureCardItem from "./item/CultureCardItem.vue";
import { CultureAxios } from "@/util/http-culture";

const router = useRouter();
const route = useRoute();

const articles = ref([]);
const currentPage = ref(parseInt(route.params.pageno) || 1);
const totalPage = ref(0);

const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  word: "",
});

onMounted(() => {
  getCultureList(currentPage.value);
});

watch(() => route.params.pageno, (newPageno) => {
  currentPage.value = parseInt(newPageno) || 1;
  param.value.pgno = currentPage.value;
  getCultureList(currentPage.value);
});

const http = CultureAxios();

const getCultureList = (pageno) => {
  const pagesize = parseInt() |VITE_ARTICLE_LIST_SIZE| 10;
  const start = 1 + ((pageno - 1) * pagesize);
  const end = pageno * pagesize;

  console.log(`Fetching data for page: ${pageno}`);
  console.log(`${VITE_CULTURE_API_URL}/${start}/${end}/`);

  let apiUrl = `${VITE_CULTURE_API_URL}/${start}/${end}/`;

  http.get(apiUrl)
    .then(({ data }) => {
      console.log(data);
      articles.value = data.articles;
      totalPage.value = Math.ceil(data.total / pagesize);
    })
    .catch(error => {
      console.error("Error fetching culture list:", error);
    });
};

const searchCultureList = () => {
  const pageno = 1;
  const pagesize = parseInt() |VITE_ARTICLE_LIST_SIZE| 10;
  const start = 1 + ((pageno - 1) * pagesize);
  const end = pageno * pagesize;
  const searchWord = param.value.word;

  console.log(`Fetching data for page: ${pageno}`);
  console.log(`${VITE_CULTURE_API_URL}/${start}/${end}/${searchWord}`);

  let apiUrl = `${VITE_CULTURE_API_URL}/${start}/${end}/%20/${searchWord}`;

  http.get(apiUrl)
    .then(({ data }) => {
      console.log(data);
      articles.value = data.articles;
      totalPage.value = Math.ceil(data.total / pagesize);
    })
    .catch(error => {
      console.error("Error fetching culture list:", error);
    });
};


const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  currentPage.value = val;
  param.value.pgno = val;
  router.push({ name: "culturelist", params: { pageno: val } });
  getCultureList(val);
};

</script>

<template>
  <div class="container-fluid">
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
                <button class="btn btn-dark" type="button" @click="searchCultureList()">검색</button>
              </div>
            </form>
          </div>
        </div>
        <div class="row">
          <CultureCardItem v-for="article in articles" :key="article.id" :article="article" />
        </div>
      </div>
    </div>
    <div class="row bottom">
      <div class="col">
        <PageNavigation
          :current-page="currentPage"
          :total-page="totalPage"
          @pageChange="onPageChange"
        ></PageNavigation>
      </div>
      <div class="col-1">
        <button type="button" class="btn btn-outline-primary btn-sm">
          글쓰기
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
