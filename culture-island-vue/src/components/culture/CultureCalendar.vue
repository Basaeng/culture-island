<script setup>
import { ref } from 'vue';
const value = ref();
const onPanelChange = (value, mode) => {
  console.log(value, mode);
};
</script>

<template>
    <a-calendar v-model:value="value" @panelChange="onPanelChange" />
</template>


<style scoped>

</style>