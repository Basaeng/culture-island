<script setup>
import Side from '@/components/culture/item/CultureSide.vue';

</script>

<template>
  <div class="container-fluid d-flex justify-content-center">
    <Side class="col-2 side-nav"/>
    <div class="col-lg-6 col-md-6 col-sm-8 page">
        <RouterView/>
    </div>
    <div class="col-2"></div>
  </div>
</template>

<style scoped>

</style>