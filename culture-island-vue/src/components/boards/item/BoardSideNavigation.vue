<script setup>
import { h, reactive } from "vue";
import { PieChartOutlined, CreditCardOutlined } from "@ant-design/icons-vue";
import { theme } from "ant-design-vue";

const emit = defineEmits(["seleted"]);

function getItem(label, key, icon, children, type) {
  return {
    key,
    icon,
    children,
    label,
    type,
  };
}

const state = reactive({
  rootSubmenuKeys: ["sub1", "sub2"],
  openKeys: ["sub1"],
  selectedKeys: [],
});

const navClick = () => {
  emit("selected", state.selectedKeys);
};

const items = reactive([
  getItem("분류 설정", "sub1", () => h(PieChartOutlined), [
    getItem("클래식", "클래식"),
    getItem("뮤지컬", "뮤지컬"),
    getItem("전시", "전시"),
    getItem("연극", "연극"),
    getItem("무용", "무용"),
    getItem("국악", "국악"),
  ]),
  getItem("유무료 설정", "sub2", () => h(CreditCardOutlined), [
    getItem("유료", 1),
    getItem("무료", 0),
  ]),
]);

const onOpenChange = (openKeys) => {
  const latestOpenKey = openKeys.find((key) => state.openKeys.indexOf(key) === -1);
  if (state.rootSubmenuKeys.indexOf(latestOpenKey) === -1) {
    state.openKeys = openKeys;
  } else {
    state.openKeys = latestOpenKey ? [latestOpenKey] : [];
  }
};
</script>

<template>
  <div class="col-sm-2 align-middle" @click="navClick">
    <a-config-provider
      :theme="{
        token: {
          colorPrimary: '#920101',
        },
      }"
    >
      <a-menu
        v-model:selectedKeys="state.selectedKeys"
        class="island_color"
        style="width: 256px"
        mode="inline"
        :open-keys="state.openKeys"
        :items="items"
        @openChange="onOpenChange"
      ></a-menu>
    </a-config-provider>
  </div>
</template>

<style scoped>
.island_color {
  color: #920101;
}
.island_button_style {
  background-color: #920101;
  color: white;
}
</style>
