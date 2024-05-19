<script setup>
import { ref, onMounted } from 'vue';
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';

const coordinate = ref({
  lat: 37.566826,
  lng: 126.9786567
});

const successCallback = (position) => {
  coordinate.value = {
    lat: position.coords.latitude,
    lng: position.coords.longitude
  };
};

const errorCallback = (err) => {
  console.error(`Error: ${err.message}`);
};

onMounted(() => {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(successCallback, errorCallback);
  } else {
    console.error('Geolocation is not supported by this browser.');
  }
});
</script>

<template>
  <KakaoMap :lat="coordinate.lat" :lng="coordinate.lng" :draggable="true">
    <KakaoMapMarker :lat="coordinate.lat" :lng="coordinate.lng"></KakaoMapMarker>
  </KakaoMap>
</template>
