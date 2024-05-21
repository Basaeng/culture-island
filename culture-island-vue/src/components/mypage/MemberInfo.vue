<script setup>
import Profile from '@/components/mypage/Profile.vue';
import { useAuthStore } from '@/stores/auth';
import { Axios } from '@/util/http-common';
import { storeToRefs } from 'pinia';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

const authStore = useAuthStore()
const { isAuthenticated } = storeToRefs(authStore)
const http = Axios()
const member = ref(null)
const router = useRouter()

const getMemberDetails = () => {
  http.get(`member/me`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('jwt')}`
    }
  }).then(({data})=>{
      member.value = data;
      console.log(data)
  }).catch((error) => {
    authStore.clearToken();
      alert("잘못된 접근이거나 토큰이 만료되었습니다.")
    router.push({name: "home"})
    console.error('Failed to fetch user details', error);
  })
}


onMounted(() => {
  if (isAuthenticated.value) {
    getMemberDetails();
  } else {
    authStore.clearToken();
      alert("로그인을 하지 않았거나 토큰이 만료되었습니다.")
    router.push({name: "home"})
    console.error('User is not authenticated');
  }
});
</script>

<template>
    <div class="mb-5">
      <h3 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="island_mark_style">내 프로필</mark>
      </h3>
    </div>
    <Profile v-if="member" :member="member"/>
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
