<script setup>
import { Axios } from '@/util/http-common';
import { onMounted } from 'vue';

const http = Axios()

const getMyArticles = () => {
    http.get(`/member/myarticles`, {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('jwt')}`
        }
    }).then(({ data }) => {
        console.log(data)
    })
}

onMounted(() => {
    getMyArticles()
})
</script>

<template>
    <div>
        myArticle
    </div>
</template>

<script scope>
</script>