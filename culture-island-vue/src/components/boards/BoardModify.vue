<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
</script>

<template>
  <div class="container">
    <div class="row justify-content-center mt-5">
      <!-- <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글수정</mark>
        </h2>
      </div> -->
      <div class="col-lg-8 col-md-6 col-sm-8">
        <div class="my-5">
          <h2>글 수정하기</h2>
          <hr />
        </div>
        <BoardFormItem type="modify" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
