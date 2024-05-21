<script setup>
import { Axios } from '@/util/http-common';
import { onMounted, ref, computed } from "vue";
import LikedShowsItem from "@/components/mypage/item/LikedShowItem.vue"
const http = Axios()
const member = ref(null)

const likedShows = ref([]);
const currentPage = ref(1);
const pageSize = ref(6); // 한 페이지당 항목 수

const totalPage = computed(() => {
  return Math.ceil(likedShows.value.length / pageSize.value);
});

const paginatedLikedShows = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return likedShows.value.slice(start, end);
});

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

const onPageChange = (page) => {
  currentPage.value = page;
};

onMounted(() => {
    getMemberDetails();
});
</script>

<template>
  <div class="container">
    <div class="mb-5">
      <h3 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="island_mark_style">관심 공연</mark>
      </h3>
    </div>
    <div class="row">
      <div class="col-12 col-sm-6 col-md-4 mb-4 liked-show-item" v-for="likedShow in paginatedLikedShows" :key="likedShow.id">
        <LikedShowsItem :likedShow="likedShow"/>
      </div>
    </div>
    <div class="row bottom mt-3">
      <div class="col text-center">
        <a-pagination
          v-model:current="currentPage"
          :total="totalPage * 10"
          @change="onPageChange"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.island_mark_style {
  background: linear-gradient(to top, rgb(146, 1, 1) 20%, transparent 30%);
}
</style>
