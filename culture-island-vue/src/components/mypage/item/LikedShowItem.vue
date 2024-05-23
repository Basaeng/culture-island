<script setup>
import { onMounted } from "vue";
import { useRouter } from "vue-router";
const props = defineProps({ likedShow: Object });

const router = useRouter();

const removeSpecialChars = (str) => {
  return str.replace(/[^a-zA-Z0-9가-힣,:-\s]/g, "")
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

onMounted(() => {
  console.log(props.likedShow);
});
</script>

<template>
  <a-card hoverable style="width: 300px; margin: 10px" @click="moveDetail(props.likedShow)">
    <div class="card-content">
      <div class="image-container">
        <img alt="example" :src="props.likedShow.mainImg" class="cover-image" />
      </div>
      <a-card-meta class="meta-content">
        <template #title>
          <div class="meta-title">{{ props.likedShow.cultureTitle }}</div>
        </template>
        <template #description>
          <div class="col">분류: {{ props.likedShow.cultureCodename }}</div>
          <br />
          <div class="col">공연 기간 : {{ props.likedShow.cultureDate }}</div>
          <div class="row"></div>
        </template>
      </a-card-meta>
    </div>
  </a-card>
</template>

<style scoped>
.card-content {
  display: flex;
  align-items: center;
  overflow: hidden;
}

.image-container {
  flex: 0 0 auto;
}

.cover-image {
  width: 100px;
  height: auto;
}

.meta-content {
  flex: 1;
  padding-left: 10px;
  overflow: hidden;
}

.meta-title {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.col {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>
