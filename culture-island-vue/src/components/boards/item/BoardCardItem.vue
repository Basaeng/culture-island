<script setup>
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import { listFile } from "@/api/board.js";
import { Empty } from "ant-design-vue";
const props = defineProps({ article: Object });

const fileInfos = ref("");
const simpleImage = Empty.PRESENTED_IMAGE_SIMPLE;

onMounted(() => {
  listFile(
    props.article.articleNo,
    ({ data }) => {
      console.log("data : " + data);
      fileInfos.value = data;
      console.log("fileInfo : " + fileInfos[0].value);
    },
    (error) => {
      console.log(error);
    }
  );
});

const router = useRouter();

const moveDetail = (article) => {
  router.push({ name: "article-view", params: { articleno: article.articleNo } });
};
</script>

<template>
  <a-card class="card p-1" hoverable @click="moveDetail(article)">
    <template #cover v-if="fileInfos != ''">
      <img class="card-img" alt="example" :src="fileInfos[0].saveFile" />
    </template>
    <template #cover v-else>
      <!-- <img class="card-img" alt="example" :src="simpleImage" /> -->
      <a-empty class="card-img" alt="example" :image="simpleImage" />
    </template>
    <a-card-meta :title="article.subject">
      <template #avatar>
        <a-avatar>
          <template #icon>
            <UserOutlined />
          </template>
        </a-avatar>
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

<style scoped>
.card {
  width: 300px;
  height: 270px;
  margin: 10px;
}
.card-img {
  height: 150px;
  width: 100%;
  object-fit: cover;
}
</style>
