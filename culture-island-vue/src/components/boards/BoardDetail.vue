<script setup>
import CommentDetailItem from "@/components/boards/comments/CommentDetailItem.vue";
import { h, ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/board";
import { listComment, registComment } from "@/api/comment";
import { UserOutlined, WarningOutlined } from "@ant-design/icons-vue";
import { notification } from "ant-design-vue";
import { Axios } from "@/util/http-common";

import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";

const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);
const { articleno } = route.params;

dayjs.extend(relativeTime);
const comments = ref([]);
const submitting = ref(false);
const value = ref("");

const comment = ref({});
const article = ref({});

const http = Axios();

onMounted(() => {
  getMemberDetails();
  getArticle();
  getComments();
});

const member = ref({});

const getMemberDetails = () => {
  http
    .get(`member/me`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("jwt")}`,
      },
    })
    .then(({ data }) => {
      member.value = data;
      // console.log("member : " + member.value.id)
    })
    .catch((error) => {
      console.error("Failed to fetch user details", error);
    });
};

const getArticle = () => {
  // const { articleno } = route.params;
  console.log(articleno + "번글 얻으러 가자!!!");
  detailArticle(
    articleno,
    ({ data }) => {
      article.value = data;
      // console.log("article : " + article.value.memberId);
    },
    (error) => {
      console.log(error);
    }
  );
};

const getComments = () => {
  console.log(articleno + "번 글의 댓글을 얻자");
  listComment(
    articleno,
    ({ data }) => {
      comments.value = data;
      // console.log(comments.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

const commentSubmit = () => {
  if (!value.value) {
    return;
  }
  submitting.value = true;

  setTimeout(() => {
    submitting.value = false;

    comment.value = {
      parentNo: "",
      depth: "",
      comment: value.value,
      articleNo: article.value.articleNo,
      name: member.value.name, // 댓글 작성자 이름
      memberNo: member.value.id, // 댓글 작성자 고유 번호
    };

    registComment(
      comment.value,
      (response) => {
        value.value = "";
        console.log("댓글 작성 완료");
        getComments();
      },
      (error) => {
        console.log(error);
      }
    );
  }, 1000);
};

function moveList() {
  router.push({ name: "article-list" });
}

function moveModify() {
  router.push({ name: "article-modify", params: { articleno } });
}

function onDeleteArticle() {
  // const { articleno } = route.params;
  console.log(articleno + "번글 삭제하러 가자!!!");
  deleteArticle(
    articleno,
    (response) => {
      moveList();
    },
    (error) => {
      console.log(error);
    }
  );
}

const [api, contextHolder] = notification.useNotification();
const open = (placement) => openNotification(placement);
const openNotification = (placement) => {
  if (member.value != "") {
    commentSubmit();
  } else {
    api.info({
      message: `로그인 필요`,
      description: "로그인이 필요한 기능입니다 로그인을 해주세요",
      placement,
      icon: () =>
        h(WarningOutlined, {
          style: "color: #920101",
        }),
    });
  }
};
</script>

<template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-6 col-sm-8">
        <div class="row my-2">
          <h2 class="text-center px-5">{{ article.subject }}</h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p>
                <span class="fw-bold">{{ article.name }}</span> <br />
                <span class="text-secondary fw-light">
                  {{ article.registerTime }} 조회 : {{ article.hit }}
                </span>
              </p>
            </div>
          </div>
          <div class="col-md-4 align-self-center text-end">댓글 : {{ comments.length }}</div>
          <div class="divider mb-3"></div>

          <div v-html="article.content"></div>

          <div class="divider mt-3 mb-3 ms-1"></div>
          <div class="d-flex justify-content-end">
            <a-button type="primary" class="mb-3 ms-1" @click="moveList">글목록</a-button>
            <div v-if="member.id == article.memberId">
              <a-button type="primary" class="mb-3 ms-1" @click="moveModify">글수정</a-button>
              <a-button danger class="mb-3 ms-1" @click="onDeleteArticle">글삭제</a-button>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-lg-8 col-md-6 col-sm-8">
            <CommentDetailItem v-for="item in comments" :key="item.commentNo" :comment="item" />
          </div>
        </div>
        <div class="row">
          <div class="col-lg-8 col-md-6 col-sm-8">
            <a-comment>
              <template #avatar>
                <a-avatar alt="ssafy">
                  <template #icon>
                    <UserOutlined />
                  </template>
                </a-avatar>
              </template>
              <template #content>
                <a-form-item>
                  <a-textarea v-model:value="value" :rows="4" />
                </a-form-item>
                <a-form-item>
                  <!-- <a-button
                    html-type="submit"
                    :loading="submitting"
                    type="primary"
                    @click="commentSubmit"
                  >
                    댓글 작성
                  </a-button> -->
                  <a-space>
                    <a-button
                      html-type="submit"
                      :loading="submitting"
                      type="primary"
                      @click="() => open('topLeft')"
                    >
                      <RadiusUpleftOutlined />
                      댓글 작성
                    </a-button>
                  </a-space>
                  <contextHolder />
                </a-form-item>
              </template>
            </a-comment>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
