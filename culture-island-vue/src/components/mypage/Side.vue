
<script setup>
import { UserOutlined, SettingOutlined, LogoutOutlined } from '@ant-design/icons-vue'
</script>

<template>
    <div class="col-lg-2 col-sm-2">
      <nav class="sidenav navbar navbar-expand-lg navbar-light bg-light flex-column">
        <ul class="navbar-nav flex-column">
          <li class="nav-item">
            <a class="nav-link active" href="#">1</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">2</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">3</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">4</a>
          </li>
        </ul>
      </nav>
    </div>
</template>


<style scoped>
/* 사이드바 */
.sidenav {
    background-color: #f1f1f1;
    height: 100vh; /* Full height */
    padding-top: 20px;
  }

  .sidenav .nav-link {
    margin: 10px 0;
  }
</style>