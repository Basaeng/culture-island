<script setup>
import { Axios } from '@/util/http-common';
import { onMounted, ref } from "vue";

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
      console.log(data)
      getLikedShows()
  }).catch ((error)=>{
    console.error('Failed to fetch user details', error);
  })
}

const getLikedShows = () => {
    http.get(`/culture/like/${member.value.id}`)
    .then(({data}) => {
        likedShows.value = data
        console.log(likedShows.value)
    })
}



onMounted (() => {
    getMemberDetails()
})

</script>

<template>

</template>

<style scope>
</style>