<script setup>
import MemberInfo from '@/components/mypage/MemberInfo.vue';
import Profile from '@/components/mypage/Profile.vue';
import Side from '@/components/mypage/Side.vue';
</script>

<template>
  <div class="container-fluid d-flex justify-content-center">
    <Side class="col-2 side-nav"/>
    <div class="col-lg-6 col-md-6 col-sm-8 page">
      <div class="mb-5">
      <h3 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="island_mark_style">내 프로필</mark>
      </h3>
    </div>
      <Profile/>
    </div>
    <div class="col-2"></div>
  </div>
</template>

<style>
  /* 본문 */
  
  .page {
    height: 100vh;
  }

  .island_color {
  color: #920101;
}
.island_button_style {
  background-color: #920101;
  color: white;
}

.island_mark_style {
  background: linear-gradient(to top, rgb(146, 1, 1) 20%, transparent 30%);
}

.small-text {
    font-size: 0.8em; /* 텍스트 크기를 작게 조정 */
  }

</style>
