<script setup>
import { ref, onMounted } from 'vue';
import { CultureAxios } from '@/util/http-culture';
import { KakaoMap, KakaoMapInfoWindow, KakaoMapMarker } from 'vue3-kakao-maps';
import { Spin } from 'ant-design-vue';

const http = CultureAxios();
const markerList = ref([]);
const isLoading = ref(true); // 로딩 상태를 위한 변수 추가

const coordinate = ref({
  lat: 37.566826,
  lng: 126.9786567,
});

const successCallback = (position) => {
  coordinate.value = {
    lat: position.coords.latitude,
    lng: position.coords.longitude,
  };
};

const errorCallback = (err) => {
  console.error(`Error: ${err.message}`);
};

const infoWindow = ref(null);

const closeInfoWindow = () => {
  if (infoWindow.value) {
    infoWindow.value.close();
  }
};

const getShowInfos = () => {
  http.get(`/1/1000`)
    .then(({ data }) => {
      markerList.value = data.culturalEventInfo.row.map(itemData => ({
        lat: parseFloat(itemData.LOT),
        lng: parseFloat(itemData.LAT),
        infoWindow: {
          content: `<div>hello</div>`, // 인포윈도우의 내용 설정
          visible: true
        },
        draggable: true,
        clickable: true,
        visible: true,
      }));
      console.log('Markers loaded:', markerList.value); // 마커 목록 출력
    })
    .catch(error => {
      console.error('Error fetching show infos:', error);
    })
    .finally(() => {
      isLoading.value = false; // 데이터 로드가 완료되면 로딩 상태를 false로 설정
    });
};

const map = ref(null);
const clusterer = ref(null);

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
};

const onLoadKakaoMapMarkerCluster = (clustererRef) => {
  clusterer.value = clustererRef;

  kakao.maps.event.addListener(clusterer.value, 'clusterclick', function (cluster) {
    console.log('Cluster clicked1:', clusterer.value);
    const currentLevel = map?.value?.getLevel();
    if (currentLevel !== undefined && !isNaN(currentLevel)) {
      const newLevel = currentLevel - 1; // 클러스터 클릭 시 1레벨 zoom in
      map.value?.setLevel(newLevel, { anchor: cluster.getCenter() });
    }
    console.log('Cluster clicked2:', cluster);
    console.log('Markers in cluster:', cluster.getMarkers()); // 클러스터 내 마커 출력
  });
};

onMounted(() => {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(successCallback, errorCallback);
  } else {
    console.error('Geolocation is not supported by this browser.');
  }

  getShowInfos();
});

const param = ref({
  key: "",
  word: "",
  type: "",
});
</script>

<template>
  <a-spin :spinning="isLoading" tip="불러오는 중..." size="large">
    <div v-if="markerList.length > 0">
      <div class="form-container d-flex justify-content-center">
        <form class="d-flex justify-content-center mt-2" style="width:50%">
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
      <div class="map-container mt-5">
        <KakaoMap
          :lat="coordinate.lat"
          :lng="coordinate.lng"
          :level="14"
          class="kakao-map"
          :markerCluster="{ markers: markerList }"
          :disableClickZoom="true"
          @onLoadKakaoMapMarkerCluster="onLoadKakaoMapMarkerCluster"
          @onLoadKakaoMap="onLoadKakaoMap"
        >
        </KakaoMap>
      </div>
    </div>
    <div v-else>
      Loading...
    </div>
  </a-spin>
</template>


<style scoped>
.ant-spin {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

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
