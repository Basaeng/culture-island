<script setup></script>

<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
      <a class="navbar-brand col-md-3 ms-md-5" href="#">Culture.island</a>
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
          <li class="nav-item">
            <a class="nav-link" id="island_color" href="#">로그인</a>
          </li>
          <button class="btn" id="island_button_style" type="submit">회원가입</button>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped></style>
