<script setup>
import { onMounted, ref } from 'vue';
import { defineProps } from 'vue';

const props = defineProps({
  member: {
    type: Object,
    required: true
  }
})

onMounted(() => {
  getRankName(member.memberRank)
})

const rankName = ref('')

const getRankName = (memberRank) => {
  console.log(memberRank)
  if (memberRank === 1) {
    rankName = 'newbie'
  } 
  console.log(rankName)
} 

</script>

<template>
      <div class="text-center">
        <img src="../../assets/noimg.PNG" alt="프로필">
      </div>
      <div class="info mt-5">
          <div class="d-flex justify-content-between align-items-center">
          이름: {{ member.name }}
          <button class="btn btn-secondary">프로필 수정</button>
          </div>
          <hr>
          이메일: {{ member.email }}
          <hr>
          한줄 소개: 
      </div>
      <div class="userdata mt-5">
        <div class="mt-5"></div>
        <div class="mt-4">회원 등급: {{ rankName }}</div>
        <div class="mt-4">관심 분류:</div>
        <div class="mt-4">전화: {{ member.number }}</div>
      </div>
      <div class="mt-5"><hr></div>
      <div class="optiondata mt-5">
        <div class="">선택</div>
        <div class="mt-3">성별: {{ member.gender }}</div>
        <div class="mt-4">생년월일: {{ member.birth }}</div>
      </div>
</template>

<style scoped>

</style>