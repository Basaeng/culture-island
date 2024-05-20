<script setup>
import { Axios } from '@/util/http-common';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const http = Axios()

const name = ref('')
const email = ref('')
const password = ref('')
const confirmPassword = ref('')
const number = ref('')
const gender = ref('')
const birth = ref('')

const router = useRouter()

const register = async() => {
  if (password.value !== confirmPassword.value) {
    alert('비밀번호가 일치하지 않습니다.')
    return;
  }

  const payload = {
    name: name.value,
    email: email.value,
    password: password.value,
    number: number.value,
    gender: gender.value,
    birth: birth.value,
  }

  try {
    const response = await http.post(`/member`, payload);
    alert('회원가입 성공! ㅋㅅㅋ')
    console.log(response.data);
    router.replace({name: 'home'})
  } catch (error) {
    console.error('회원가입 실패: ', error);
    alert('회원 가입 실패 ㅠㅅㅠ')
  }
}
</script>

<template>
  <div class="container d-flex flex-column align-items-center mt-5">
    <div class="title-container mb-4 col-8">
      <h3 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="island_mark_style">회원 가입</mark>
      </h3>
    </div>
    <div class="form-container">
      <form class="col-8" @submit.prevent="register">
        <div class="mb-3">
          <label for="username" class="form-label">아이디</label>
          <input type="text" v-model="name" class="form-control" id="username" required />
        </div>
        <div class="mb-3">
          <label for="email" class="form-label">이메일</label>
          <input type="email" v-model="email" class="form-control" id="email" required />
        </div>
        <div class="mb-3">
          <label for="password" class="form-label">비밀번호</label>
          <input type="password" v-model="password" class="form-control" id="password" required />
        </div>
        <div class="mb-3">
          <label for="confirmPassword" class="form-label">비밀번호 확인</label>
          <input type="password" v-model="confirmPassword" class="form-control" id="confirmPassword" required />
        </div>
        <div class="mb-3">
          <label for="phoneNumber" class="form-label">전화번호</label>
          <input type="tel" v-model="number" class="form-control" id="phoneNumber" required />
        </div>
        <div class="mb-3">
          <label for="gender" class="form-label">[선택] 성별</label>
          <select v-model="gender" class="form-select" id="gender">
            <option value=""></option>
            <option value="0">남성</option>
            <option value="1">여성</option>
            <option value="2">기타</option>
          </select>
        </div>
        <div class="mb-3">
          <label for="birthDate" class="form-label">[선택] 생일</label>
          <input type="date" v-model="birth" class="form-control" id="birthDate" />
        </div>
        <button type="submit" class="btn island_button_style w-100">회원가입</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.island_button_style {
  background-color: #920101;
  color: white;
}

.island_mark_style {
  background: linear-gradient(to top, rgb(146, 1, 1) 20%, transparent 30%);
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.title-container {
  text-align: center;
}

.form-container {
  width: 100%;
  display: flex;
  justify-content: center;
}
</style>
