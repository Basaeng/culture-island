<script setup>
import { ref, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
const { VITE_CULTURE_API_URL, VITE_ARTICLE_LIST_SIZE } = import.meta.env;
import PageNavigation from "@/components/common/PageNavigation.vue";
import CultureCardItem from "./item/CultureCardItem.vue";
import { CultureAxios } from "@/util/http-culture";

const router = useRouter();
const route = useRoute();

const props = defineProps({
  selectedType: {
    type: String,
    default: null,
  },
});

const items = ref([]);
const currentPage = ref(parseInt(route.params.pageno) || 1);
const totalPage = ref(0);
const noDataMessage = ref("");

const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  word: "",
  type: props.selectedType || "",
});

onMounted(() => {
  getCultureList(currentPage.value);
});

watch(
  () => route.params.pageno,
  (newPageno) => {
    currentPage.value = parseInt(newPageno) || 1;
    param.value.pgno = currentPage.value;
    getCultureList(currentPage.value);
  }
);

watch(
  () => props.selectedType,
  (newType) => {
    param.value.type = newType || "";
    searchCultureList();
  }
);

const http = CultureAxios();

const getCultureList = (pageno) => {
  const pagesize = parseInt() | VITE_ARTICLE_LIST_SIZE | 10;
  const start = 1 + (pageno - 1) * pagesize;
  const end = pageno * pagesize;

  // console.log(`Fetching data for page: ${pageno}`);
  // console.log(`${VITE_CULTURE_API_URL}/${start}/${end}/`);

  let apiUrl = `/${start}/${end}/`;

  http
    .get(apiUrl)
    .then(({ data }) => {
      console.log(data);
      if (data.culturalEventInfo && data.culturalEventInfo.row.length > 0) {
        items.value = data.culturalEventInfo.row;
        totalPage.value = Math.ceil(data.total / pagesize);
        noDataMessage.value = "";
      } else {
        items.value = [];
        totalPage.value = 0;
        noDataMessage.value = data.RESULT.MESSAGE || "No data available.";
      }
    })
    .catch((error) => {
      console.error("Error fetching culture list:", error);
      noDataMessage.value = "Error fetching data.";
    });
};

const searchCultureList = () => {
  const pageno = 1;
  const pagesize = parseInt() | VITE_ARTICLE_LIST_SIZE | 10;
  const start = 1 + (pageno - 1) * pagesize;
  const end = pageno * pagesize;
  const searchWord = param.value.word;

  // console.log(`Fetching data for page: ${pageno}`);
  // console.log(`${VITE_CULTURE_API_URL}/${start}/${end}/${searchWord}`);

  let apiUrl = `/${start}/${end}/`;

  if (param.value.type) {
    apiUrl += `${param.value.type}/`;
  } else {
    apiUrl += "%20/";
  }

  apiUrl += searchWord;

  http
    .get(apiUrl)
    .then(({ data }) => {
      console.log(data);
      if (data.culturalEventInfo && data.culturalEventInfo.row.length > 0) {
        items.value = data.culturalEventInfo.row;
        totalPage.value = Math.ceil(data.total / pagesize);
        noDataMessage.value = "";
      } else {
        items.value = [];
        totalPage.value = 0;
        noDataMessage.value = data.RESULT.MESSAGE || "No data available.";
      }
    })
    .catch((error) => {
      console.error("Error fetching culture list:", error);
      noDataMessage.value = "Error fetching data.";
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
  <div class="container-fluid mt-3">
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
                <button class="btn btn-dark" type="button" @click="searchCultureList()">
                  검색
                </button>
              </div>
            </form>
          </div>
        </div>
        <div class="row d-flex justify-content-end">
          <div v-if="noDataMessage" class="col d-flex justify-content-center">
            <p>{{ noDataMessage }}</p>
          </div>
          <CultureCardItem v-for="(item, index) in items" :key="index" :item="item">
          </CultureCardItem>
        </div>
      </div>
    </div>
    <div class="row bottom">
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
      <div class="col-1"></div>
    </div>
  </div>
</template>

<style scoped></style>
