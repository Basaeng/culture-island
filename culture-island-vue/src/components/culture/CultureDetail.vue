<script setup>
import { useRoute } from "vue-router";
import { ref, onMounted } from "vue";
import axios from "axios";
import { CultureAxios } from "@/util/http-culture";
const route = useRoute();
const CODENAME = route.params.CODENAME;
const TITLE = route.params.TITLE;
const DATE = route.params.DATE;

const http = CultureAxios()

const items = ref(null);
const noDataMessage = ref("");

const getCultureDetail = (pageno) => {
  let apiUrl = `/1/1/${CODENAME}/${TITLE}/${DATE}`;
  console.log(apiUrl)
  http.get(apiUrl)
    .then(({ data }) => {
      console.log(data)
      if (data.culturalEventInfo && data.culturalEventInfo.row.length > 0) {
        items.value = data.culturalEventInfo.row;
        noDataMessage.value = "";
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
  <div>
    <div v-if="itemData">
      <h1>{{ itemData.TITLE }}</h1>
      <p>위치: {{ itemData.GUNAME }} {{ itemData.PLACE }}</p>
      <p>공연 기간: {{ itemData.DATE }}</p>
      <!-- 나머지 내용 -->
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>
  </div>
</template>

<style scoped></style>
