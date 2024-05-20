<script setup>
import { CultureAxios } from "@/util/http-culture";
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
const { VITE_CULTURE_API_URL } = import.meta.env;

const http = CultureAxios();

const value = ref();
const events = ref([]);
const selectedEvents = ref([]);

onMounted(() => {
  const date = nowMonth();
  getEventList(date);
});

const getEventList = async (date) => {
  await http.get(`0/200/%20/%20/${date}`).then(({ data }) => {
    events.value = data.culturalEventInfo.row;
    console.log(events.value);
  });
};

const nowMonth = () => {
  const today = new Date();

  const year = today.getFullYear();
  const month = ("0" + (today.getMonth() + 1)).slice(-2);
  //   const day = ("0" + today.getDate()).slice(-2);

  const dateStr = year + "-" + month;
  return dateStr;
};

const onPanelChange = (value, mode) => {
  console.log("판넬 : " + value, mode);
};

const pageno = 1;
const pagesize = 5;
// pageno에 따라
const onSelectDate = (date) => {
  const dateString = date.format("YYYY-MM-DD");
  console.log(dateString);
  console.log(VITE_CULTURE_API_URL);
  http.get(`/0/6/%20/%20/${dateString}`).then(({ data }) => {
    console.log(data);
  });
};

const checkedDate = ref();

const getListData = (value) => {
  const day = value.date();
  const listData = events.value
    .filter((event) => new Date(event.STRTDATE).getDate() === day)
    .map((event) => ({
      type: event.IS_FREE === "무료" ? "success" : "warning",
      content: event.TITLE,
    }));

  //   console.log("list data : " + JSON.stringify(listData));
  return listData;
};
const getMonthData = (value) => {
  if (value.month() === 8) {
    return 1394;
  }
};
</script>

<template>
  <a-calendar v-model:value="value" @panelChange="onPanelChange" @select="onSelectDate">
    <template #dateCellRender="{ current }">
      <ul class="events">
        <li v-for="item in getListData(current)" :key="item.content">
          <a-badge :status="item.type" :text="item.content" />
        </li>
      </ul>
    </template>
  </a-calendar>
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
