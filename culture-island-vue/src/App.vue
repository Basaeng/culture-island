<script setup>
import { RouterLink, RouterView } from "vue-router";
import HeaderBar from "./components/common/HeaderBar.vue";
</script>

<template>
  <div>
    <a-config-provider
      :theme="{
        token: {
          colorPrimary: '#920101',
        },
      }"
    >
      <HeaderBar />
      <RouterView />
    </a-config-provider>
  </div>
</template>
<style></style>
