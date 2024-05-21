<script setup>
import { Axios } from '@/util/http-common';
import { onMounted, ref } from "vue";
import LikedShowsItem from "@/components/mypage/item/LikedShowItem.vue"
const http = Axios()
const member = ref(null)

const likedShows = ref(null);

const getMemberDetails = () => {
  http.get(`/member/me`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('jwt')}`
    }
  }).then(({data})=>{
      member.value = data;
      getLikedShows();
  }).catch ((error)=>{
    console.error('Failed to fetch user details', error);
  })
}

const getLikedShows = () => {
    http.get(`/culture/like/${member.value.id}`)
    .then(({data}) => {
        likedShows.value = data;
    })
}

onMounted(() => {
    getMemberDetails();
})
</script>

<template>
  <div class="container">
    <div class="mb-5">
      <h3 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="island_mark_style">내 프로필</mark>
      </h3>
    </div>
    <div class="row">
      <div class="col-12 col-sm-6 col-md-4 mb-4 liked-show-item" v-for="likedShow in likedShows" :key="likedShow.id">
        <LikedShowsItem :likedShow="likedShow"/>
      </div>
    </div>
  </div>
</template>

<style scoped>
.liked-show-item {
  display: flex;
  justify-content: center;
  align-items: center;
}

.liked-show-item > * {
  width: 100%;
  height: auto;
}

.island_mark_style {
  background: linear-gradient(to top, rgb(146, 1, 1) 20%, transparent 30%);
}
</style>
