<script setup>
import { useRoute } from "vue-router";
import { ref, onMounted } from "vue";
import { CultureAxios } from "@/util/http-culture";
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';

import emptyHeart from '../assets/emptyheart.png';
import filledHeart from '../assets/filledheart.png';
import { Axios } from "@/util/http-common";

const route = useRoute();
const CODENAME = route.params.CODENAME;
const TITLE = route.params.TITLE;
const DATE = route.params.DATE;

const serverhttp = Axios();
const http = CultureAxios();

const items = ref([]);
const noDataMessage = ref("");
const itemData = ref(null);
const isHeartFilled = ref(false);  // 하트 상태를 관리하는 ref
const member = ref(null)

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

        checkIfLiked();

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

const getMemberDetails = () => {
  serverhttp.get(`member/me`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('jwt')}`
    }
  }).then(({ data }) => {
    member.value = data;
    console.log(data);
    getCultureDetail();
  }).catch((error) => {
    console.error('Failed to fetch user details', error);
  });
};

const toggleHeart = () => {
  if (!isHeartFilled.value) {
    checkAndAddCulture().then(() => {
      alert('관심 공연에 등록했습니다')
      isHeartFilled.value = true;
    })
  } else {
    alert('관심 공연에서 제외했습니다.')
  }
  isHeartFilled.value = !isHeartFilled.value;  // 클릭 시 상태를 토글
};

const checkAndAddCulture = () => {
  return new Promise((resolve, reject) => {
    serverhttp.get(`/culture/check-culture/${CODENAME}/${TITLE}/${DATE}`)
      .then(({ data }) => {
        console.log(data);
        if (!data.exists) {
          console.log('추가를 원해요');
          let cultureData = {
            title: itemData.value.TITLE,
            date: itemData.value.DATE,
            codename: CODENAME,
            guname: itemData.value.GUNAME,
            place: itemData.value.PLACE,
            useTrgt: itemData.value.USE_TRGT,
            useFee: itemData.value.USE_FEE,
            startDate: itemData.value.STRTDATE,
            log: parseFloat(itemData.value.LOG),
            lat: parseFloat(itemData.value.LAT),
            isFree: itemData.value.IS_FREE,
            hmpgAddr: itemData.value.HMPG_ADDR,
            score: parseFloat(itemData.value.SCORE)
          };
          console.log("문화데이터 전송 출력", cultureData);
          serverhttp.post(`/culture/add-culture`, cultureData).then(() => {
            addLike().then(resolve).catch(reject);
            console.log("추가가 되써요");
            resolve();
          }).catch(error => {
            console.log("Error adding culture data:", error);
            reject();
          });
        } else {
          addLike().then(resolve).catch(reject);
        }
      }).catch(error => {
        console.log("Error checking culture data:", error);
        reject();
      });
  });
};

const addLike = () => {
  return new Promise((resolve, reject) => {
    const likeData = {
      memberId: member.value.id,
      cultureCodename: itemData.value.CODENAME,
      cultureTitle: itemData.value.TITLE,
      cultureDate: itemData.value.DATE
    };
    serverhttp.post(`/culture/add_like`, likeData).then(() => {
      resolve();
    }).catch(error => {
      console.log("Error adding like data:", error);
      reject();
    });
  });
};

const checkIfLiked = () => {
  serverhttp.get(`/culture/check_like/${member.value.id}/${CODENAME}/${TITLE}/${DATE}`)
    .then(({ data }) => {
      isHeartFilled.value = data.liked;
    }).catch(error => {
    console.error("Errer checking like status:", error)
  })
}

const coordinate = ref({
  lat: 37.566826,
  lng: 126.9786567
});

onMounted(() => {
  getMemberDetails();
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
