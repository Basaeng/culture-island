<template>
  <a-spin :spinning="isLoading" tip="불러오는 중..." size="large">
    <div class="container-fluid d-flex justify-content-center">
      <div class="col-lg-8 col-md-8 col-sm-10 page" v-if="itemData">
        <div class="d-flex flex-column align-items-center mt-3">
          <div class="mt-2 title-container d-flex align-items-center">
            <h3 class="me-3">{{ itemData.TITLE }}</h3>
            <img
              :src="isHeartFilled ? filledHeart : emptyHeart"
              alt="하트 이미지"
              class="heart-img"
              @click="toggleHeart"
            />
          </div>
        </div>
        <div class="content mt-5 row">
          <div class="col-lg-6 col-md-6 col-sm-12 d-flex justify-content-center align-items-center">
            <img :src="itemData.MAIN_IMG" alt="img" class="responsive-img" />
          </div>
          <div class="col-lg-6 col-md-6 col-sm-12 mt-3">
            <p>위치: {{ itemData.GUNAME }} {{ itemData.PLACE }}</p>
            <p>기간: {{ itemData.DATE }}</p>
            <p>요금: {{ itemData.USE_FEE }}</p>
            <p>대상: {{ itemData.USE_TRGT }}</p>
            <div class="mt-3 button-group">
              <a :href="itemData.ORG_LINK">
                <button class="btn island_button_style">세부 내용</button>
              </a>
              <button class="btn island_button_style" @click="fetchGPTMessage">gpt에게 공연 물어보기</button>
            </div>
          </div>
        </div>
        <!-- 나머지 내용 -->
        <div class="mt-2 mb-5 d-flex justify-content-center">
          <KakaoMap width=1500 height=500 :lat="coordinate.lat" :lng="coordinate.lng" :draggable="true">
            <KakaoMapMarker :lat="coordinate.lat" :lng="coordinate.lng"></KakaoMapMarker>
          </KakaoMap>
        </div>
      </div>
      <div v-else>
        <p>{{ noDataMessage }}</p>
      </div>
    </div>
  </a-spin>

  <!-- 모달 컴포넌트 추가 -->
  <a-modal v-model:open="isModalVisible" @cancel="closeModal" @ok="closeModal" :width="800">
  <template #title>
    <div class="d-flex align-items-center">
      GPT Response
      <img src="@/assets/gpt-icon.png" alt="GPT Icon" class="gpt-icon ms-2" />
    </div>
  </template>
  <p v-html="modalMessage"></p>
</a-modal>


</template>

<script setup>
import { useRoute } from "vue-router";
import { ref, onMounted } from "vue";
import { CultureAxios } from "@/util/http-culture";
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
import { Spin } from "ant-design-vue";
import OpenAI from "openai";

import emptyHeart from "../assets/emptyheart.png";
import filledHeart from "../assets/filledheart.png";
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
const isHeartFilled = ref(false); // 하트 상태를 관리하는 ref
const member = ref(null);
const isLoading = ref(true); // 로딩 상태를 위한 변수 추가

const modalMessage = ref(""); // 모달에 표시할 메시지를 저장하는 ref 변수
const isModalVisible = ref(false); // 모달의 열림/닫힘 상태를 저장하는 ref 변수

const { VITE_OPENAI_API_KEY } = import.meta.env;

const openai = new OpenAI({ apiKey: VITE_OPENAI_API_KEY, dangerouslyAllowBrowser: true });
openai.apiKey = VITE_OPENAI_API_KEY;

const fetchGPTMessage = async () => {
  isLoading.value = true;
  try {
    const completion = await openai.chat.completions.create({
      messages: [{ role: "user", content: `${itemData.value.TITLE}에 대해서 설명해줘` }],
      model: "gpt-4o",
    });
    const rawMessage = completion.choices[0].message.content; // GPT의 응답을 저장
    console.log(modalMessage.value)
    modalMessage.value = formatMessage(rawMessage);
    isModalVisible.value = true; // 모달을 열기
  } catch (error) {
    console.error("Error generating GPT response:", error);
  }
  isLoading.value = false;
};

const formatMessage = (message) => {
  return message.replace(/(\d+\.)|(\.)/g, (match, p1, p2) => {
    if (p1) {
      return p1; // 숫자 뒤에 오는 마침표는 그대로 둡니다.
    }
    if (p2) {
      return '.<br><br>'; // 그 외의 경우는 줄바꿈을 추가합니다.
    }
  });
};





const closeModal = () => {
  isModalVisible.value = false; // 모달을 닫기
};

const getCultureDetail = () => {
  let apiUrl = `/1/1/${CODENAME}/${TITLE}/${DATE}`;
  console.log(apiUrl);
  http
    .get(apiUrl)
    .then(({ data }) => {
      console.log(data);
      if (data.culturalEventInfo && data.culturalEventInfo.row.length > 0) {
        items.value = data.culturalEventInfo.row;
        noDataMessage.value = "";
        itemData.value = items.value[0];
        console.log(itemData.value);

        coordinate.value = {
          lat: parseFloat(itemData.value.LOT),
          lng: parseFloat(itemData.value.LAT),
        };

        checkIfLiked();
      } else {
        items.value = [];
        noDataMessage.value = data.RESULT.MESSAGE || "No data available.";
      }
    })
    .catch((error) => {
      console.error("Error fetching culture list:", error);
      noDataMessage.value = "Error fetching data.";
    })
    .finally(() => {
      isLoading.value = false; // 데이터 로드가 완료되면 로딩 상태를 false로 설정
    });
};

const getMemberDetails = () => {
  serverhttp
    .get(`member/me`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("jwt")}`,
      },
    })
    .then(({ data }) => {
      member.value = data;
      console.log(data);
      getCultureDetail();
    })
    .catch((error) => {
      console.error("Failed to fetch user details", error);
    });
};

const toggleHeart = () => {
  if (!isHeartFilled.value) {
    checkAndAddCulture()
      .then(() => {
        alert("관심 공연에 등록했습니다");
        isHeartFilled.value = true;
      })
      .catch((error) => {
        console.error("Error adding like:", error);
      });
  } else {
    removeLike()
      .then(() => {
        alert("관심 공연에서 제외했습니다");
        isHeartFilled.value = false;
      })
      .catch((error) => {
        console.error("Error removing like:", error);
      });
  }
};

const checkAndAddCulture = () => {
  return new Promise((resolve, reject) => {
    serverhttp
      .get(`/culture/check-culture/${CODENAME}/${TITLE}/${DATE}`)
      .then(({ data }) => {
        console.log(data);
        if (!data.exists) {
          console.log("추가를 원해요");
          let cultureData = {
            title: TITLE,
            date: DATE,
            codename: CODENAME,
            guname: itemData.value.GUNAME,
            place: itemData.value.PLACE,
            useTrgt: itemData.value.USE_TRGT,
            useFee: itemData.value.USE_FEE,
            startDate: itemData.value.STRTDATE,
            log: parseFloat(itemData.value.LOG),
            lat: parseFloat(itemData.value.LAT),
            isFree: itemData.value.IS_FREE,
            hmpgAddr: itemData.value.ORG_LINK,
            score: parseFloat(itemData.value.SCORE),
          };
          console.log("문화데이터 전송 출력", cultureData);
          serverhttp
            .post(`/culture/add-culture`, cultureData)
            .then(() => {
              addLike().then(resolve).catch(reject);
              console.log("추가가 되써요");
              resolve();
            })
            .catch((error) => {
              console.log("Error adding culture data:", error);
              reject();
            });
        } else {
          addLike().then(resolve).catch(reject);
        }
      })
      .catch((error) => {
        console.log("Error checking culture data:", error);
        reject();
      });
  });
};

const addLike = () => {
  return new Promise((resolve, reject) => {
    const likeData = {
      memberId: member.value.id,
      cultureCodename: CODENAME,
      cultureTitle: TITLE,
      cultureDate: DATE,
      mainImg: itemData.value.MAIN_IMG
    };
    serverhttp
      .get(`/culture/check_like/${member.value.id}/${CODENAME}/${TITLE}/${DATE}`)
      .then(({ data }) => {
        if (!data.likeResponse.exists) {
          serverhttp
            .post(`/culture/like`, likeData)
            .then(() => {
              resolve();
            })
            .catch((error) => {
              console.log("Error adding like data:", error);
              reject();
            });
        }
      });
  });
};

const removeLike = () => {
  return new Promise((resolve, reject) => {
    const likeData = {
      memberId: member.value.id,
      cultureCodename: CODENAME,
      cultureTitle: TITLE,
      cultureDate: DATE,
    };
    serverhttp
      .get(`/culture/check_like/${member.value.id}/${CODENAME}/${TITLE}/${DATE}`)
      .then(({ data }) => {
        console.log("삭제할 데이터:", data);
        if (data.likeResponse.exists) {
          serverhttp
            .delete(`/culture/like/${data.likeResponse.id}`)
            .then(() => {
              resolve();
            })
            .catch((error) => {
              console.log("Error removing like data:", error);
              reject();
            });
        }
      });
  });
};

const checkIfLiked = () => {
  serverhttp
    .get(`/culture/check_like/${member.value.id}/${CODENAME}/${TITLE}/${DATE}`)
    .then(({ data }) => {
      console.log(data);
      isHeartFilled.value = data.likeResponse.exists;
      console.log("heartstatus", isHeartFilled.value);
    })
    .catch((error) => {
      console.error("Error checking like status:", error);
    });
};

const coordinate = ref({
  lat: 37.566826,
  lng: 126.9786567,
});

onMounted(() => {
  getMemberDetails();
});
</script>

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
.island_button_style {
  background-color: #920101;
  color: white;
  text-decoration-color: white;
  text-decoration: none;
  margin-right: 10px; /* 오른쪽 마진 추가 */
  margin-bottom: 10px; /* 아래쪽 마진 추가 */
}
.gpt-icon {
  width: 24px;
  height: 24px;
  margin-left: 8px;
}
.button-group {
  display: flex;
  flex-wrap: wrap; /* 화면이 줄어들 때 버튼이 줄바꿈 되도록 설정 */
}
</style>
