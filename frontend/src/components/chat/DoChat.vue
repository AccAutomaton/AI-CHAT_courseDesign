<script setup>

import store from "../../store";
import {nextTick, ref, watch} from "vue";
import {ElNotification, ElScrollbar} from "element-plus";
import axiosService from "@/request";

// eslint-disable-next-line no-undef
const props = defineProps({
  dialogId: Number,
  needInput: Boolean
})

const dialogList = ref([]);

const GetDialogList = dialogId => {
  axiosService({
    url: "/chat/get/" + dialogId,
    method: "get"
  }).then(data => {
    dialogList.value = data;
    DownScrollBar();
  })
}

GetDialogList(props.dialogId);
watch(() => props.dialogId, newValue => {
  GetDialogList(newValue);
})

const textarea = ref(""), isLoading = ref(false);
const scrollbarRef = ref();

const DownScrollBar = () => {
  nextTick(() => {
    let scrollBar = document.getElementById("scrollBar");
    scrollbarRef.value.setScrollTop(scrollBar.scrollHeight);
  });
}

const getNowDateTime = () => {
  let getTime = new Date().getTime();
  let time = new Date(getTime);
  let year = time.getFullYear();
  let month = (time.getMonth() + 1).toString().padStart(2, '0');
  let date = time.getDate().toString().padStart(2, '0');
  let hour = time.getHours().toString().padStart(2, '0');
  let minute = time.getMinutes().toString().padStart(2, '0');
  let second = time.getSeconds().toString().padStart(2, '0');
  return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}

const Submit = () => {
  if (textarea.value === "") {
    ElNotification({title: "内容不能为空", type: "error"});
    return;
  }
  const textareaTemp = textarea.value;
  textarea.value = "";
  dialogList.value.push({
    role: "user",
    time: getNowDateTime(),
    content: textareaTemp
  })
  DownScrollBar();
  isLoading.value = true;
  axiosService({
    url: "/chat/doChat/" + props.dialogId,
    method: "post",
    data: {
      "content": textareaTemp
    }
  }).then(data => {
    dialogList.value.push(data);
    isLoading.value = false;
    DownScrollBar();
  })
}
</script>

<template>
  <el-card shadow="never" style="border-radius: 30px; margin-bottom: 20px; height: 27.5em; text-align: left">
    <el-scrollbar height="25em" style="padding-right: 10px" ref="scrollbarRef" id="scrollBar">
      <div v-for="dialog in dialogList" v-bind:key="dialog">
        <div class="gpt" v-if="dialog.role==='assistant'">
          <div class="gpt-name">
            文心一言
            <span class="date">
              {{
                dialog.time
              }}
            </span>
          </div>
          <div class="gpt-message">
            {{
              dialog.content
            }}
          </div>
        </div>
        <div class="user" v-if="dialog.role==='user'">
          <div class="user-name">
            <span v-if="needInput">
              {{
                store.getters.getNickname
              }}
            </span>
            <span class="date">
              {{
                dialog.time
              }}
            </span>
          </div>
          <div class="user-message">
            {{
              dialog.content
            }}
          </div>
        </div>
      </div>

    </el-scrollbar>
  </el-card>
  <el-card shadow="never" style="border-radius: 30px; height: 10em" v-if="needInput">
    <el-input
        v-model="textarea"
        :rows="4"
        type="textarea"
        placeholder="请输入你想问的问题"
        v-on:keyup.shift.enter="Submit"
        autofocus
    />
    <el-button style="float: right; margin-top: 7px; width: 10%" round @click="Submit" :loading="isLoading">提交
    </el-button>
  </el-card>
</template>

<style scoped>
.date {
  font-size: 70%;
  color: rgb(128, 128, 128);
  margin-left: 10px;
}

.user {
  width: 100%;
  float: right;
  text-align: right;
}

.user-name {
  font-size: large;
  font-weight: bold;
  color: forestgreen;
  margin-right: 10px;
}

.user-message {
  background-color: whitesmoke;
  max-width: 60%;
  width: fit-content;
  margin: 10px;
  padding: 5px;
  border-radius: 5px;
  float: right;
  text-align: left;
}

.gpt {
  width: 100%;
  float: left;
}

.gpt-name {
  font-size: large;
  font-weight: bold;
  color: cornflowerblue;
}

.gpt-message {
  background-color: whitesmoke;
  max-width: 60%;
  width: fit-content;
  margin: 10px;
  padding: 5px;
  border-radius: 5px;
}
</style>