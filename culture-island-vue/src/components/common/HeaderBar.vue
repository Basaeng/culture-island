<script setup></script>

<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top">
    <div class="container-fluid">
      <a class="navbar-brand col-sm-6 col-md-3" href="#">Culture.island</a>

      <div class="collapse navbar-collapse" id="navbarScroll">
        <ul
          class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll"
          style="--bs-scroll-height: 100px"
        >
          <li class="nav-item">
            <a class="nav-link" href="#">공연 달력</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">공연 지도</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">공연 검색</a>
          </li>
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
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
          <li class="nav-item">
            <a></a>
          </li>
          <li class="nav-item">
            <a></a>
          </li>
        </ul>
      </div>

      <div></div> 
    </div>
  </nav>
</template>

<style scoped>
.logo {
  width: 120px;
  height: 31px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px 24px 16px 0;
  float: left;
}

/* 헤더를 상단에 붙이는 스타일 */
a-layout-header {
  position: fixed;
  width: 100%;
  z-index: 1000; /* 다른 요소 위로 헤더를 올리기 위해 z-index 설정 */
}
</style>
