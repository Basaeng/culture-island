<script setup>
import { ref, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { CultureAxios } from "@/util/http-culture";
import CultureCardItem from "./item/CultureCardItem.vue";

const { VITE_CULTURE_API_URL, VITE_ARTICLE_LIST_SIZE } = import.meta.env;

const http = CultureAxios();
const router = useRouter();
const route = useRoute();

const props = defineProps({
  selectedType: {
    type: Array,
    default: null,
  },
});

const items = ref([]);
const currentPage = ref(parseInt(route.params.pageno) || 1);
const totalPage = ref(0);
const noDataMessage = ref("");
const isLoading = ref(true); // 로딩 상태를 위한 변수 추가
const isSearchLoading = ref(false); // 검색 로딩 상태를 위한 변수 추가

const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: route.params.word || "",
  word: route.params.word || "",
  type: props.selectedType || route.params.type || "all",
});

const setDefaultParams = () => {
  if (!route.params.pageno) route.params.pageno = 1;
  if (!route.params.type) route.params.type = "all";
  if (!route.params.keyword) route.params.keyword = "";
};

onMounted(() => {
  setDefaultParams();
  fetchCultureList();
});

watch(
  () => route.params.pageno,
  (newPageno) => {
    currentPage.value = parseInt(newPageno) || 1;
    param.value.pgno = currentPage.value;
    fetchCultureList();
  }
);

watch(
  () => props.selectedType,
  (newType) => {
    param.value.type = newType || "all";
    fetchCultureList();
  }
);

const fetchCultureList = async () => {
  isLoading.value = true;
  const pageno = param.value.pgno;
  const pagesize = parseInt(VITE_ARTICLE_LIST_SIZE) || 10;
  const start = 1 + (pageno - 1) * pagesize;
  const end = pageno * pagesize;
  const type = param.value.type !== "all" ? param.value.type : "%20";
  const word = param.value.word || "%20";

  const apiUrl = `${VITE_CULTURE_API_URL}/${start}/${end}/${type}/${word}/`;

  try {
    const { data } = await http.get(apiUrl);
    
    if (data.culturalEventInfo && data.culturalEventInfo.row.length > 0) {
      console.log("hello:", data.culturalEventInfo)
      items.value = data.culturalEventInfo.row;
      totalPage.value = Math.ceil(data.culturalEventInfo.list_total_count / pagesize) || 0;
      noDataMessage.value = "";
    } else {
      items.value = [];
      totalPage.value = 0;
      noDataMessage.value = data.RESULT.MESSAGE || "No data available.";
    }
  } catch (error) {
    console.error("Error fetching culture list:", error);
    noDataMessage.value = "Error fetching data.";
  } finally {
    isLoading.value = false;
  }
};

const searchCultureList = async () => {
  isSearchLoading.value = true;
  param.value.pgno = 1;
  currentPage.value = 1;
  router.push({
    name: "culturelist",
    params: { pageno: 1, type: param.value.type, word: param.value.word },
  });
  await fetchCultureList();
  isSearchLoading.value = false;
};

const onPageChange = (val) => {
  currentPage.value = val;
  param.value.pgno = val;
  router.push({
    name: "culturelist",
    params: { pageno: val, type: param.value.type, word: param.value.word },
  });
  fetchCultureList();
};
</script>

<template>
  <a-spin :spinning="isLoading" tip="불러오는 중..." size="large" class="mt-5 pt-5">
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
                    @keyup.enter="searchCultureList"
                  />
                  <button class="btn btn-dark" type="button" @click="searchCultureList">
                    검색
                  </button>
                </div>
              </form>
            </div>
          </div>
          <div class="row d-flex justify-content-start">
            <div v-if="noDataMessage" class="col d-flex justify-content-center">
              <p>{{ noDataMessage }}</p>
            </div>
            <CultureCardItem v-for="(item, index) in items" :key="index" :item="item" />
          </div>
        </div>
      </div>
      <div class="row bottom">
        <div class="col text-center">
          <a-pagination
            v-model:current="currentPage"
            :total="totalPage * 10"
            :pagesize="10"
            @change="onPageChange"
            :showSizeChanger="false"
          />
        </div>
        <div class="col-1"></div>
      </div>
    </div>
  </a-spin>
  <a-spin :spinning="isSearchLoading" tip="불러오는 중..." size="large" class="mt-5 pt-5">
    <!-- 추가적인 검색 결과나 관련 내용을 여기에 표시할 수 있습니다 -->
  </a-spin>
</template>

<style scoped>
.events {
  list-style: none;
  margin: 0;
  padding: 0;
}
.events .ant-badge-status {
  overflow: hidden;
  white-space: nowrap;
  width: 100%;
  text-overflow: ellipsis;
  font-size: 12px;
}
.notes-month {
  text-align: center;
  font-size: 28px;
}
.notes-month section {
  font-size: 28px;
}
.scroll {
  display: inline-block;
  width: 250px;
  height: 250px;
  padding: 20px;
  overflow-y: scroll;
  border: 1px solid black;
  box-sizing: border-box;
  color: white;
  font-family: "Nanum Gothic";
  background-color: rgba(0, 0, 0, 0.8);
}

/* 스크롤바 설정*/
.box1::-webkit-scrollbar {
  width: 20px;
}

/* 스크롤바 막대 설정*/
.box1::-webkit-scrollbar-thumb {
  background-color: rgba(255, 255, 255, 1);
  /* 스크롤바 둥글게 설정    */
  border-radius: 10px;
  border: 7px solid rgba(0, 0, 0, 0.8);
}

/* 스크롤바 뒷 배경 설정*/
.box1::-webkit-scrollbar-track {
  background-color: rgba(0, 0, 0, 0);
}
</style>
