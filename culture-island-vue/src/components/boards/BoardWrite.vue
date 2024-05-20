<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";

</script>

<template>
  <div class="container">
    <div class="row justify-content-center mt-5">
      <div class="col-lg-8 col-md-6 col-sm-8">
        <div class="my-5">
          <h2>글쓰기</h2>
          <hr />
        </div>
        <BoardFormItem type="regist" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
