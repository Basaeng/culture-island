<script setup></script>

<template>
  <div>
      <video id="bg-video" src="@/assets/Concert.mp4" autoplay muted loop>
        Your browser is not supported!
      </video>
  </div>
</template>

<style scoped>

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

</style>
