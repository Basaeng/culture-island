<script setup>
import { ref } from 'vue';
import Side from '@/components/culture/item/CultureSide.vue';

const selectedType = ref(null);

const selectedTypeHandler = (type) => {
  selectedType.value = type;
};
</script>

<template>
  <div class="container-fluid d-flex justify-content-center">
    <Side class="col-2 side-nav" @selected="selectedTypeHandler" />
    <div class="col-lg-6 col-md-6 col-sm-8 page">
      <RouterView :selectedType="selectedType" />
    </div>
    <div class="col-2"></div>
  </div>
</template>

<style scoped>
/* Add any scoped styles here if necessary */
</style>
