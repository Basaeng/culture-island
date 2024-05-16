import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
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
      path: "/",
      name: "community",
      component: HomeView,
    },
  ],
});

export default router;
