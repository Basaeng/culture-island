<script setup>
import { useRoute } from "vue-router";
import { ref, onMounted } from "vue";
import { CultureAxios } from "@/util/http-culture";
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';
import emptyHeart from '../assets/emptyheart.png';
import filledHeart from '../assets/filledheart.png';

const route = useRoute();
const CODENAME = route.params.CODENAME;
const TITLE = route.params.TITLE;
const DATE = route.params.DATE;

const http = CultureAxios();

const items = ref([]);
const noDataMessage = ref("");
const itemData = ref(null);
const isHeartFilled = ref(false);  // 하트 상태를 관리하는 ref

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

        coordinate.value = {
          lat: parseFloat(itemData.value.LOT),
          lng: parseFloat(itemData.value.LAT)
        };

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

const toggleHeart = () => {
  if (!isHeartFilled.value) {
    alert('관심 공연에 등록했습니다')
  } else {
    alert('관심 공연에서 제외했습니다.')
  }
  isHeartFilled.value = !isHeartFilled.value;  // 클릭 시 상태를 토글
};

const coordinate = ref({
  lat: 37.566826,
  lng: 126.9786567
});

onMounted(() => {
  getCultureDetail();
});
</script>

<template>
  <div class="container-fluid d-flex justify-content-center">
    <div class="col-lg-8 col-md-8 col-sm-10 page" v-if="itemData">
      <div class="d-flex flex-column align-items-center mt-3">
        <div class="title-container d-flex align-items-center">
          <h3 class="me-3">{{ itemData.TITLE }}</h3>
          <img 
            :src="isHeartFilled ? filledHeart : emptyHeart" 
            alt="하트 이미지" 
            class="heart-img" 
            @click="toggleHeart"
          >
        </div>
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
      <div class="mt-5">
        <KakaoMap :lat="coordinate.lat" :lng="coordinate.lng" :draggable="true">
          <KakaoMapMarker :lat="coordinate.lat" :lng="coordinate.lng"></KakaoMapMarker>
        </KakaoMap>
      </div>
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
.title-container {
  display: flex;
  align-items: center;
}
.heart-img {
  max-width: 24px;
  max-height: 24px;
  margin-left: 8px;
  cursor: pointer; 
}
</style>
