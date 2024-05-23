<script setup>
import { onMounted, ref, watchEffect } from "vue";
import { defineProps } from "vue";

const props = defineProps({
  member: {
    type: Object,
    required: true,
  },
});

const rankName = ref("");

const getRankName = (memberRank) => {
  console.log(memberRank);
  if (memberRank === 1) {
    return "newbie";
  }
  console.log(rankName);
};

const genderString = (gender) => {
  if (gender == 0) {
    return "남자";
  }
  if (gender == 1) {
    return "여자";
  }
  if (gender == 2) {
    return "기타";
  }
};

const formatDate = (dateString) => {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, "0");
  const day = date.getDate().toString().padStart(2, "0");
  return `${year}-${month}-${day}`;
};
</script>

<template>
  <div class="text-center">
    <!-- <img src="../../assets/noimg.PNG" alt="프로필"> -->
    <img
      src="../../assets/mo.jpg"
      style="border-radius: 50%; height: 170px; width: 170px"
      alt="프로필"
    />
  </div>
  <div class="info mt-5">
    <div class="d-flex justify-content-between align-items-center">
      이름: {{ member.name }}
      <button class="btn btn-secondary">프로필 수정</button>
    </div>
    <hr />
    이메일: {{ member.email }}
    <hr />
    한줄 소개: 제발 한국인이면 공연 사랑합시다.
  </div>
  <div class="userdata mt-5">
    <div class="mt-5"></div>
    <div class="mt-4">회원 등급: {{ getRankName(member.memberRank) }}</div>
    <div class="mt-4">관심 분류: 오페라, 클래식</div>
    <div class="mt-4">전화: {{ member.number }}</div>
  </div>
  <div class="mt-5"><hr /></div>
  <div class="optiondata mt-5">
    <div class="mt-3">성별: {{ genderString(member.gender) }}</div>
    <div class="mt-4">생년월일: {{ formatDate(member.birth) }}</div>
  </div>
</template>

<style scoped></style>
