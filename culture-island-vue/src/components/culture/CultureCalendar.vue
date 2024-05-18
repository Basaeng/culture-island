<script setup>
import {CultureAxios} from '@/util/http-culture';
import { onMounted, ref } from 'vue';
const { VITE_CULTURE_API_URL } = import.meta.env

const value = ref();
const events = ref({})
const selectedEvents = ref([])

const onPanelChange = (value, mode) => {
  console.log(value, mode);
};

const http = CultureAxios()
const pageno = 1;
const pagesize = 5;
// pageno에 따라 
const onSelectDate = (date) => {
    const dateString = date.format('YYYY-MM-DD');
    console.log(dateString)
    console.log(VITE_CULTURE_API_URL)
    http.get(`${VITE_CULTURE_API_URL}/${1+((pageno-1)*pagesize)}/${pageno*pagesize}/%20/%20/${dateString}`)
    .then(({data}) => {
        console.log(data)
    })
};

</script>

<template>
    <a-calendar v-model:value="value" @panelChange="onPanelChange" @select="onSelectDate" />
</template>


<style scoped>

</style>