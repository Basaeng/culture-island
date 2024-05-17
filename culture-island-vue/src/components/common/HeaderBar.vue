
<script setup>
import { useAuthStore } from '@/stores/auth';
import { Axios } from '@/util/http-common';
import { Modal } from 'bootstrap';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter()
const http = Axios();

const username = ref('')
const password = ref('')
const authStore = useAuthStore()

const { isAuthenticated } = storeToRefs(authStore)

const login = () => {
  const request = {
    username: username.value,
    password: password.value
  }

  http.post(`/member/login`, request)
    .then((response) => {
      authStore.setToken(response.data.token)
      closeModal();
      alert("환영합니다")
      console.log(isAuthenticated.value)
      const redirect = router.currentRoute.value.query.redirect || '/'
      router.push(redirect)
    })
    .catch((error) => {
      console.log('Error logging in:', error)
    })
}

const logout = () => {
  authStore.clearToken();
      alert("안녕히가세요.")
      const redirect = router.currentRoute.value.query.redirect || '/'
  router.push(redirect)
      //추후 필요시 블랙 리스트 처리
  // http.post(`/member/logout`)
  //   .then((response) => {
  //     alert("안녕히가세요.")
  //     const redirect = router.currentRoute.value.query.redirect || '/'
  //     router.push(redirect)
  // })    .catch((error) => {
  //     console.log('Error during logout:', error)
  //   })
}

const closeModal = () => {
  const modalElement = document.getElementById('loginModal');
  const modalInstance = Modal.getInstance(modalElement) || new Modal(modalElement);
  modalInstance.hide()
}

const moveToRegisterPage = () => {
  router.push({ name: 'register' })
}
</script>

<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
      <a class="navbar-brand col-md-3 ms-md-5" href="/">Culture.island</a>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav col-md-5 ms-md-auto mb-3 mb-lg-0">
          <li class="nav-item col-md-3">
            <a class="nav-link active" aria-current="page" href="#">공연 달력</a>
          </li>
          <li class="nav-item col-md-3">
            <a class="nav-link active" href="#">공연 지도</a>
          </li>
          <li class="nav-item col-md-3">
            <a class="nav-link active" href="#">공연 검색</a>
          </li>
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle active"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              커뮤니티
            </a>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" href="#">게시판</a></li>
              <li><a class="dropdown-item" href="#">공지사항</a></li>
            </ul>
          </li>
        </ul>
        <a-badge class="ms-md-auto" count="1">
          <img src="@/assets/Bell.png" alt="" />
        </a-badge>
        <ul class="navbar-nav me-5 mb-2 ms-md-auto mb-lg-0">
          <li v-if="isAuthenticated" class="nav-item">
            <a class="nav-link island_color" @click="logout" href="/">로그아웃</a>
          </li>
          <li v-if="!isAuthenticated" class="nav-item">
            <a
              class="nav-link island_color"
              id="island_color"
              aria-current="page"
              href="#"
              data-bs-toggle="modal"
              data-bs-target="#loginModal"
            >
              로그인
            </a>
          </li>
          <button v-if="!isAuthenticated" class="btn island_button_style" type="button" @click="moveToRegisterPage">회원가입</button>
          <button v-if="isAuthenticated" class="btn island_button_style" type="button" @click="moveToRegisterPage">마이페이지</button>
        </ul>
      </div>
    </div>
  </nav>

  <!-- 로그인 모달 -->
  <div
    class="modal fade"
    id="loginModal"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="d-flex justify-content-center">
          <div>
            <br>
            <h4>로그인</h4>
          </div>
        </div>

        <!-- 입력 폼 -->
        <div class="modal-body">
          <form @submit.prevent="login">
            <div class="mb-3">
              <label for="loginId">아이디 : </label>
              <input type="text" name="loginId" class="form-control" id="loginId" v-model="username" required />
              <div class="invalid-feedback">아이디를 입력해주세요.</div>
            </div>

            <div class="mb-3">
              <label for="loginPwd">비밀번호 : </label>
              <input type="password" name="loginPwd" class="form-control" id="loginPwd" v-model="password" required />
              <div class="invalid-feedback">비밀번호를 입력해주세요.</div>
            </div>
            <div class="d-flex justify-content-between align-items-center">
              <span class="ms-1"><a class="text-secondary text-decoration-underline" href="/register">회원가입</a></span>
              <button type="me-1 submit" class="btn island_button_style " id="LOGIN">로그인</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.island_color {
  color: #920101;
}
.island_button_style {
  background-color: #920101;
  color: white;
}
</style>
