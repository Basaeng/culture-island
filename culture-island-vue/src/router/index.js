import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

import BoardView from "@/views/BoardView.vue";
import BoardList from "@/components/boards/BoardList.vue";
import BoardDetail from "@/components/boards/BoardDetail.vue";
import BoardModify from "@/components/boards/BoardModify.vue";
import BoardWrite from "@/components/boards/BoardWrite.vue";

import RegisterView from "@/views/RegisterView.vue";
import MyPageView from "@/views/MyPageView.vue";
import MemberInfo from "@/components/mypage/MemberInfo.vue";
import MyArticle from "@/components/mypage/MyArticle.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/register",
      name: "register",
      component: RegisterView
    },
    {
      path: "/",
      name: "calendar",
      component: HomeView,
    },
    {
      path: "/",
      name: "map",
      component: HomeView,
    },
    {
      path: "/",
      name: "search",
      component: HomeView,
    },
    {
      path: "/board",
      name: "board",
      component: BoardView,
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          component: BoardList,
        },
        {
          path: "view/:articleno",
          name: "article-view",
          component: BoardDetail,
        },
        {
          path: "write",
          name: "article-write",
          component: BoardWrite,
        },
        {
          path: "modify/:articleno",
          name: "article-modify",
          component: BoardModify,
        },
      ],
    },
    {
      path: "/",
      name: "community",
      component: HomeView,
    },
    {
      path: "/mypage",
      name: "mypage",
      component: MyPageView,
      redirect: { name: "memberinfo" },
      children: [
        {
          path: "memberinfo",
          name: "memberinfo",
          component: MemberInfo
        },
        {
          path: "myarticle",
          name: "myarticle",
          component: MyArticle
        }
      ]
    }
  ],
});

export default router;
