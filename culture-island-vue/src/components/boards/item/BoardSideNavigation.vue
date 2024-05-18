<template>
  <div class="col-sm-2 align-middle">
    <a-config-provider
      :theme="{
        token: {
          colorPrimary: '#920101',
        },
      }"
    >
      <a-menu
        @click="navClick"
        v-model:selectedKeys="state.selectedKeys"
        style="width: 256px"
        mode="inline"
        :open-keys="state.openKeys"
        :items="items"
        @openChange="onOpenChange"
      ></a-menu>
    </a-config-provider>
  </div>
</template>
<script setup>
import { h, reactive } from "vue";
import { MailOutlined, AppstoreOutlined, SettingOutlined } from "@ant-design/icons-vue";
import { theme } from "ant-design-vue";

const navClick = () => {
  console.log(state.selectedKeys);
};

function getItem(label, key, icon, children, type) {
  return {
    key,
    icon,
    children,
    label,
    type,
  };
}
const items = reactive([
  getItem("분류 설정", "sub1", () => h(MailOutlined), [
    getItem("클래식", "1"),
    getItem("뮤지컬", "2"),
    getItem("전시", "3"),
    getItem("연극", "4"),
    getItem("무용", "5"),
    getItem("국악", "6"),
  ]),
  getItem("유무료 설정", "sub2", () => h(AppstoreOutlined), [
    getItem("유료", "7"),
    getItem("무료", "8"),
  ]),
]);
const state = reactive({
  rootSubmenuKeys: ["sub1", "sub2"],
  openKeys: ["sub1"],
  selectedKeys: [],
});
const onOpenChange = (openKeys) => {
  const latestOpenKey = openKeys.find((key) => state.openKeys.indexOf(key) === -1);
  if (state.rootSubmenuKeys.indexOf(latestOpenKey) === -1) {
    state.openKeys = openKeys;
  } else {
    state.openKeys = latestOpenKey ? [latestOpenKey] : [];
  }
};
</script>

<style scoped>
.island_color {
  color: #920101;
}
.island_button_style {
  background-color: #920101;
  color: white;
}
</style>
