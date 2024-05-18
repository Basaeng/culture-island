<script setup>
defineProps({ article: Object });
</script>

<template>
  <a-card hoverable style="width: 300px; margin: 10px">
    <template #cover>
      <img
        alt="example"
        src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
      />
    </template>
    <a-card-meta :title="article.subject" :description="article.name">
      <template #avatar>
        <a-avatar src="https://joeschmoe.io/api/v1/random" />
      </template>
    </a-card-meta>
  </a-card>
</template>

<style scoped></style>
