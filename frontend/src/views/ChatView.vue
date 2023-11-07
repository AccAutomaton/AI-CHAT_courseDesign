<script setup>

import {Plus} from "@element-plus/icons-vue";
import TitleButton from "@/components/chat/TitleButton.vue";
import DoChat from "@/components/chat/DoChat.vue";
import {ref} from "vue";
import axiosService from "@/request";

const selectDialogId = ref(0);
const loadCompleteFlag = ref(false), currentLoadPageNum = ref(0);
const chatList = ref([]);
const isAddMode = ref(false);
const infiniteDisabled = ref(false);

const load = () => {
  if (loadCompleteFlag.value) {
    return;
  }
  currentLoadPageNum.value++;
  infiniteDisabled.value = true;
  axiosService({
    url: "/chat/getList/" + currentLoadPageNum.value + "/8",
    method: "get"
  }).then(data => {
    loadCompleteFlag.value = data["isLastPage"];
    chatList.value.push(...data["records"]);
    infiniteDisabled.value = false;
  })
};

const ClickDialog = id => {
  selectDialogId.value = id;
}

const ClickAddButton = () => {
  if (isAddMode.value) {
    return;
  }
  chatList.value.unshift({
    title: "",
    createFlag: true,
    id: 0
  })
  isAddMode.value = true;
}

const AddDialogSuccessCallback = data => {
  // noinspection JSUnresolvedReference
  const index = chatList.value.findIndex(value => value.id === 0);
  chatList.value[index].id = data["id"];
  chatList.value[index].title = data["title"];
  chatList.value[index].createFlag = false;
  isAddMode.value = false;
}

const UpdateDialogTitleSuccessCallback = data => {
  if (data["status"] === "success") {
    // noinspection JSUnresolvedReference
    const index = chatList.value.findIndex(value => value.id === data["id"]);
    chatList.value[index].title = data["newTitle"];
  }
}
const DeleteDialogSuccessCallback = response => {
  let index;
  if (response["createFlag"] === true) {
    // noinspection JSUnresolvedReference
    index = chatList.value.findIndex(value => value.id === 0);
    isAddMode.value = false;
  } else {
    // noinspection JSUnresolvedReference
    index = chatList.value.findIndex(value => value.id === response["id"]);
  }
  chatList.value.splice(index, 1);
}
</script>

<template>
  <el-row :gutter="20" style="text-align: center">
    <el-col :span="6">
      <el-card shadow="always" style="border-radius: 30px">
        <el-button round style="width: 90%; height: 3rem" @click="ClickAddButton">
          <el-icon>
            <Plus/>
          </el-icon>&nbsp;&nbsp;创建
        </el-button>
        <el-divider>
          <div style="color: rgb(128,128,128)">历史对话</div>
        </el-divider>
        <el-scrollbar height="33rem">
          <ul v-infinite-scroll="load" :infinite-scroll-disabled="infiniteDisabled" class="infinite-list">
            <li v-for="value in chatList" :key="value">
              <TitleButton :dialogTitle="value.title" :create-flag="value.createFlag || false" :id="value.id"
                           @click="ClickDialog(value.id)" @addDialogSuccess="AddDialogSuccessCallback"
                           @updateDialogTitleSuccess="UpdateDialogTitleSuccessCallback"
                           @deleteDialogSuccess="DeleteDialogSuccessCallback"/>
            </li>
          </ul>
        </el-scrollbar>
      </el-card>
    </el-col>
    <el-col :span="18">
      <el-card shadow="always" style="border-radius: 30px; height: 41.6rem">
        <DoChat v-if="selectDialogId" :dialogId="selectDialogId" :needInput="true"/>
      </el-card>
    </el-col>
  </el-row>
</template>

<style scoped>
.infinite-list {
  height: 300px;
  padding: 0;
  margin: 0;
  list-style: none;
}
</style>