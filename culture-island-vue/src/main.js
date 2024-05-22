import { createApp } from "vue";
import { createPinia } from "pinia";
import Antd from "ant-design-vue";
import "ant-design-vue/dist/reset.css";
import "@/assets/main.css";
import '@/assets/fonts.css'

import App from "./App.vue";
import router from "./router";
import { useKakao } from "vue3-kakao-maps";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";

const { VITE_MAP_API_KEY } = import.meta.env
useKakao(`${VITE_MAP_API_KEY}`, ['clusterer', 'services', 'drawing']);
const app = createApp(App);
app.use(createPinia());
app.use(router);

app.use(Antd);

app.mount("#app");
