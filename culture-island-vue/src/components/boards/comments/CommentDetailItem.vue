<script setup></script>

<template>
  <a-comment>
    <template #actions>
      <span key="comment-nested-reply-to">Reply to</span>
    </template>
    <template #author>
      <a>ssafy</a>
    </template>
    <template #avatar>
      <a-avatar src="https://joeschmoe.io/api/v1/random" alt="ssafy" />
    </template>
    <template #content>
      <p>우와 최고!</p>
    </template>
    <!-- <a-comment>
      <template #actions>
        <span>Reply to</span>
      </template>
      <template #author>
        <a>Han Solo</a>
      </template>
      <template #avatar>
        <a-avatar src="https://joeschmoe.io/api/v1/random" alt="Han Solo" />
      </template>
      <template #content>
        <p>
          We supply a series of design principles, practical patterns and high quality design
          resources (Sketch and Axure).
        </p>
      </template>
    </a-comment> -->
  </a-comment>
</template>

<style scoped></style>
