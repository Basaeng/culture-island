<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
const searchValue = ref("");

const router = useRouter();

const getCultureList = () => {
  router.push({ path: `/search/list/1/all/${searchValue.value}` });
};

const removeValue = () => {
  searchValue.value = "";
};
</script>

<template>
  <div class="container">
    <video id="bg-video" src="@/assets/Concert.mp4" autoplay muted loop>
      Your browser is not supported!
    </video>
    <div class="input-group-container search-layout">
      <div class="input-group input-group-lg m-5" style="max-width: 600px; width: 100%">
        <button class="btn btn-light search-button" type="button" @click="getCultureList">
          <img class="search-img" src="@/assets/search-icon.png" alt="search" />
        </button>
        <input
          class="form-control home-search"
          type="text"
          v-model="searchValue"
          placeholder="공연을 검색해보세요"
          aria-label=".form-control-lg example"
        />
        <button class="btn btn-light search-button" type="button" @click="removeValue">
          <img class="search-img" src="@/assets/close-button-icon.png" alt="search" />
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.search-layout {
  position: absolute;
  top: 50%;
  right: 15%;
  transform: translateY(-50%);
}

.search-img {
  height: 25px;
}

#bg-video {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
  z-index: -1;
  /* opacity: 0.15; */
  object-fit: cover;
}
.island_color {
  color: #920101;
}
.island_button_style {
  background-color: #920101;
  color: white;
  text-decoration-color: white;
  text-decoration: none;
}

.search-button {
  color: gray;
  background-color: white;
}
.home-search {
  width: 100%;
}
</style>
