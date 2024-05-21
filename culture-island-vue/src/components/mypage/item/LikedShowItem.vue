<script setup>
import { useRouter } from "vue-router";
defineProps({ likedShow: Object });

const router = useRouter();

const removeSpecialChars = (str) => {
  return str.replace(/[^a-zA-Z0-9가-힣\s]/g, ""); // 특수 문자를 제거
};

const processCODENAME = (str) => {
  // 숫자를 제거하고, '/' 이후의 문자도 제거
  return str.replace(/\d/g, "").split("/")[0];
};

const processTITLE = (str) => {
  // 특수 문자로 둘러싸인 부분 제거
  str = str.replace(/\[.*?\]/g, "");
  // 숫자로 시작하지 않는 첫 번째 문자부터 시작하는 부분을 반환
  const match = str.match(/[^\d].*/);
  return match ? match[0].trim() : "";
};

const moveDetail = (item) => {
  router.push({
    name: "cultureview",
    params: {
      CODENAME: removeSpecialChars(processCODENAME(item.cultureCodename)),
      TITLE: removeSpecialChars(processTITLE(item.cultureTitle)),
      DATE: item.cultureDate,
    },
  });
};
</script>

<template>
  <a-card hoverable style="width: 300px; margin: 10px" @click="moveDetail(likedShow)">
    <template #cover>
      <img
        alt="example"
        src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
      />
    </template>
    <a-card-meta :title="likedShow.cultureTitle">
      <template #avatar>
        <a-avatar src="@/assets/filledheart.png" />
        <!-- <a-avatar src="https://joeschmoe.io/api/v1/random" /> -->
      </template>
      <template #description>
        <div class="col">분류: {{ likedShow.cultureCodename }}</div>
        <br />
        <div class="col">공연 기간 : {{ likedShow.cultureDate }}</div>
        <div class="row"></div>
      </template>
    </a-card-meta>
  </a-card>
</template>

<style scoped></style>
