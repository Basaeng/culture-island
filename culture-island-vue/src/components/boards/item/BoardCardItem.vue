<script setup>
import { useRouter } from "vue-router";
defineProps({ article: Object });

const router = useRouter();

const moveDetail = (article) => {
  router.push({ name: "article-view", params: { articleno: article.articleNo } });
};
</script>

<template>
  <a-card hoverable style="width: 300px; margin: 10px" @click="moveDetail(article)">
    <template #cover>
      <img
        alt="example"
        src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
      />
    </template>
    <a-card-meta :title="article.subject">
      <template #avatar>
        <a-avatar src="https://joeschmoe.io/api/v1/random" />
      </template>
      <template #description>
        <div class="row">
          <div class="col">
            {{ article.name }}
          </div>
          <div class="col">조회수 : {{ article.hit }}</div>
        </div>
      </template>
    </a-card-meta>
  </a-card>
</template>

<style scoped></style>
