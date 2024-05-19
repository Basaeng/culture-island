<script setup>
defineProps({ comment: Object });
</script>

<template>
  <a-comment>
    <template #actions>
      <span key="comment-nested-reply-to">Reply to</span>
    </template>
    <template #author>
      <a>{{ comment.name }}</a>
    </template>
    <template #avatar>
      <a-avatar alt="ssafy">
        <template #icon>
          <UserOutlined />
        </template>
      </a-avatar>
    </template>
    <!-- <template #avatar>
      <a-avatar>
        <template #icon>
          <SmileOutlined />
        </template>
      </a-avatar>
    </template> -->
    <template #content>
      <p>{{ comment.comment }}</p>
    </template>
    <template #datetime>
      <span>{{ comment.registerTime }}</span>
    </template>
    <!-- 대댓글 시 -->
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
