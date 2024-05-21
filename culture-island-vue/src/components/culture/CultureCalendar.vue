<script setup>
import { CultureAxios } from "@/util/http-culture";
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";

import CultureCardItem from "./item/CultureCardItem.vue";

const { VITE_CULTURE_API_URL } = import.meta.env;

const props = defineProps({
  selectedType: {
    type: String,
    default: null,
  },
});

const http = CultureAxios();

const value = ref();
const events = ref([]);
const selectedEventData = ref([]);

const eventDate = ref("");
const eventType = ref("%20");

const spinning = ref(true);
const selectedSpinning = ref(false);

onMounted(() => {
  eventDate.value = nowYear();
  getEventList("", eventDate.value);
});

const getEventList = async (type, date) => {
  spinning.value = true;
  await http
    .get(`0/999/${type}/%20/${date}`)
    .then(({ data }) => {
      events.value = data.culturalEventInfo.row;
    })
    .catch((error) => {
      events.value = "";
      console.log(error);
    });
  spinning.value = false;
};

const nowYear = () => {
  const today = new Date();

  const year = today.getFullYear();
  // const month = ("0" + (today.getMonth() + 1)).slice(-2);

  // const dateStr = year + "-" + month;
  // const dateStr = year;
  const dateStr = "";
  return dateStr;
};

watch(
  () => props.selectedType,
  (newType) => {
    eventType.value = newType;
    getEventList(eventType.value, eventDate.value);
  }
);

const onPanelChange = (value, mode) => {
  console.log("판넬 : " + value, mode);
};

const onSelectDate = async (date) => {
  selectedSpinning.value = true;
  const dateString = date.format("YYYY-MM-DD");
  console.log(eventType.value + " " + dateString);
  await http
    .get(`0/999/${eventType.value}/%20/${dateString}`)
    .then(({ data }) => {
      console.log(data.culturalEventInfo.row);
      selectedEventData.value = data.culturalEventInfo.row;
    })
    .catch((error) => {
      selectedEventData.value = "";
      console.log(error);
    });

  selectedSpinning.value = false;
};

const getListData = (value) => {
  const day = value.date();
  const month = value.month();
  const year = value.year();

  const listData = events.value
    .filter((event) => {
      const eventDate = new Date(event.STRTDATE);
      return (
        eventDate.getDate() === day &&
        eventDate.getMonth() === month && // 0-based
        eventDate.getFullYear() === year
      );
    })
    .map((event) => ({
      type: event.IS_FREE === "무료" ? "success" : "warning",
      content: event.TITLE,
    }));

  return listData;
};

const getMonthData = (value) => {
  if (value.month() === 8) {
    return 1394;
  }
};
</script>

<template>
  <a-spin :spinning="spinning" tip="불러오는중..." size="large" class="mt-5 pt-5">
    <a-calendar v-model:value="value" @panelChange="onPanelChange" @select="onSelectDate">
      <template #dateCellRender="{ current }">
        <ul class="events">
          <li v-for="item in getListData(current)" :key="item.content">
            <a-badge :status="item.type" :text="item.content" />
          </li>
        </ul>
      </template>
    </a-calendar>
  </a-spin>
  <a-spin :spinning="selectedSpinning" tip="불러오는중..." size="large" class="mt-5 pt-5">
    <div class="row">
      <CultureCardItem
        v-for="(item, index) in selectedEventData"
        :key="index"
        type="calendar"
        :item="item"
      />
    </div>
  </a-spin>
</template>

<style scoped>
.events {
  list-style: none;
  margin: 0;
  padding: 0;
}
.events .ant-badge-status {
  overflow: hidden;
  white-space: nowrap;
  width: 100%;
  text-overflow: ellipsis;
  font-size: 12px;
}
.notes-month {
  text-align: center;
  font-size: 28px;
}
.notes-month section {
  font-size: 28px;
}
.scroll {
  display: inline-block;
  width: 250px;
  height: 250px;
  padding: 20px;
  overflow-y: scroll;
  border: 1px solid black;
  box-sizing: border-box;
  color: white;
  font-family: "Nanum Gothic";
  background-color: rgba(0, 0, 0, 0.8);
}

/* 스크롤바 설정*/
.box1::-webkit-scrollbar {
  width: 20px;
}

/* 스크롤바 막대 설정*/
.box1::-webkit-scrollbar-thumb {
  background-color: rgba(255, 255, 255, 1);
  /* 스크롤바 둥글게 설정    */
  border-radius: 10px;
  border: 7px solid rgba(0, 0, 0, 0.8);
}

/* 스크롤바 뒷 배경 설정*/
.box1::-webkit-scrollbar-track {
  background-color: rgba(0, 0, 0, 0);
}
</style>
