
<script setup>
import { UserOutlined, SettingOutlined, LogoutOutlined } from '@ant-design/icons-vue'
import { RouterLink } from 'vue-router';
</script>

<template>
    <div class="col-lg-2 col-sm-2 mt-5 d-flex">
      <nav class="sidenav navbar navbar-expand-lg navbar-light  flex-column">
        <ul class="navbar-nav flex-column">
          <li class="nav-item mt-5 mb-3 ">
            <a class="nav-link active" href="#">마이페이지</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">관심공연</a>
          </li>
          <li class="nav-item">
            <router-link  class="nav-link" :to="{name: 'myarticle'}">내 게시글</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{name: 'memberinfo'}">내 프로필</router-link>
          </li>
        </ul>
      </nav>
    </div>
</template>


<style scoped>
/* 사이드바 */

</style>