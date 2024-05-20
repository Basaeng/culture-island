<script setup>
import { useRoute } from "vue-router";
import { ref, onMounted } from "vue";
import { CultureAxios } from "@/util/http-culture";

const route = useRoute();
const CODENAME = route.params.CODENAME;
const TITLE = route.params.TITLE;
const DATE = route.params.DATE;

const http = CultureAxios();

const items = ref([]);
const noDataMessage = ref("");
const itemData = ref(null);

const getCultureDetail = () => {
  let apiUrl = `/1/1/${CODENAME}/${TITLE}/${DATE}`;
  console.log(apiUrl);
  http.get(apiUrl)
    .then(({ data }) => {
      console.log(data);
      if (data.culturalEventInfo && data.culturalEventInfo.row.length > 0) {
        items.value = data.culturalEventInfo.row;
        noDataMessage.value = "";
        itemData.value = items.value[0];
        console.log(itemData.value);
      } else {
        items.value = [];
        noDataMessage.value = data.RESULT.MESSAGE || "No data available.";
      }
    })
    .catch(error => {
      console.error("Error fetching culture list:", error);
      noDataMessage.value = "Error fetching data.";
    });
};

onMounted(() => {
  getCultureDetail();
});
</script>

<template>
  <div class="container-fluid d-flex justify-content-center">
    <div class="col-lg-8 col-md-8 col-sm-10 page" v-if="itemData">
      <div class="d-flex flex-column align-items-center mt-3">
        <h3>{{ itemData.TITLE }}</h3>
      </div>
      <div class="content mt-5 row">
        <div class="col-lg-6 col-md-6 col-sm-12 d-flex justify-content-center align-items-center">
          <img :src="itemData.MAIN_IMG" alt="img" class="responsive-img">
        </div>
        <div class="col-lg-6 col-md-6 col-sm-12">
          <p>위치: {{ itemData.GUNAME }} {{ itemData.PLACE }}</p>
          <p>공연 기간: {{ itemData.DATE }}</p>
        </div>
      </div>
      <!-- 나머지 내용 -->
    </div>
    <div v-else>
      <p>{{ noDataMessage }}</p>
    </div>
  </div>
</template>

<style scoped>
.responsive-img {
  max-width: 80%;
  height: auto;
  object-fit: cover;
}
</style>
