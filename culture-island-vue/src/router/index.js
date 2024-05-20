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
import CultureSearch from "@/views/CultureSearch.vue";
import CultureList from "@/components/culture/CultureList.vue"
import CultureCalendar from "@/components/culture/CultureCalendar.vue"
import CultureMap from "@/components/culture/CultureMap.vue"

import Test from "@/views/Test.vue";
import CultureDetail from "@/views/CultureDetail.vue";

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
    },
    {
      path: "/search",
      name: "search",
      component: CultureSearch,
      redirect: { name: "culturelist" },
      children: [
        {
          path: "list/:pageno",
          name: "culturelist",
          component: CultureList
        },
        {
          path: "calendar",
          name: "culturecalendar",
          component: CultureCalendar
        },
        {
          path: "map",
          name: "culturemap",
          component: CultureMap
        },
      ]
    },
    {
      path: "/view/:CODENAME/:TITLE/:DATE",
      name: "cultureview",
      component: CultureDetail
    },
    {
      path: '/test',
      name: 'test',
      component: Test
    }
  ],
});

export default router;
