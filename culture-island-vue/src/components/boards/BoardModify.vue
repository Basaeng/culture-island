<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
import { Axios } from "@/util/http-common";
import { onMounted, ref } from "vue";

onMounted(() => {
  getMemberDetails();
});

const http = Axios();
const member = ref({});

const getMemberDetails = () => {
  http.get(`member/me`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('jwt')}`
    }
  }).then(({data})=>{
      member.value = data;
      // console.log("member : " + member.value.id)
  }).catch ((error)=>{
    console.error('Failed to fetch user details', error);
  });
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center mt-5">
      <div class="col-lg-8 col-md-6 col-sm-8">
        <div class="my-5">
          <h2>글 수정하기</h2>
          <hr />
        </div>
        <BoardFormItem type="modify" :member="`${member}`"/>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
