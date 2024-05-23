<template>
  <div>
    <star-rating :increment="0.5" />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import StarRating from 'vue-star-rating';

const rating = ref(0);

const handleRatingChange = (newRating) => {
  rating.value = newRating;
};
</script>
