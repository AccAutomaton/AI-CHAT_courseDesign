<script setup>
import {ref} from "vue";
import {Check, Delete, Edit} from "@element-plus/icons-vue";
import axiosService from "@/request";
import {ElMessageBox, ElNotification} from "element-plus";

// eslint-disable-next-line no-undef
const props = defineProps({
  id: Number,
  dialogTitle: String,
  createFlag: Boolean,
})

// eslint-disable-next-line no-undef
const emit = defineEmits(["addDialogSuccess", "updateDialogTitleSuccess", "deleteDialogSuccess"])

const isEditMode = ref(false), newTitle = ref("");

const UseEditMode = () => {
  newTitle.value = props.dialogTitle;
  isEditMode.value = true;
}

const EditComplete = () => {
  isEditMode.value = false;
  if (newTitle.value === "") {
    ElNotification({title: "标题不能为空", type: "error"});
    return;
  }
  if (props.createFlag) {
    axiosService({
      url: "/chat/create",
      method: "post",
      data: {
        "title": newTitle.value
      }
    }).then(data => {
      emit("addDialogSuccess", data);
      ElNotification({title: "创建成功", type: "success"});
    })
  } else {
    axiosService({
      url: "/chat/set/title/" + props.id,
      method: "post",
      data: {
        "newTitle": newTitle.value
      }
    }).then(data => {
      data["newTitle"] = newTitle.value;
      data["id"] = props.id;
      emit("updateDialogTitleSuccess", data);
      ElNotification({title: "修改成功", type: "success"})
    })
  }
}

if (props.createFlag) {
  UseEditMode();
}

const DeleteDialog = () => {
  const response = {};
  if (props.createFlag) {
    response["createFlag"] = true;
    emit("deleteDialogSuccess", response);
  } else {
    ElMessageBox.alert('删除后不可恢复', '确认删除?', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      callback: (action => {
        if (action === "confirm") {
          axiosService({
            url: "/chat/delete/" + props.id,
            method: "delete",
          }).then(data => {
            if (data["status"] === "success") {
              response["createFlag"] = false;
              response["id"] = props.id;
              ElNotification({title: "删除成功", type: "success"})
              emit("deleteDialogSuccess", response);
            }
          })
        }
      })
    })
  }
}
</script>

<template>
  <el-card shadow="hover" style="margin-bottom: 20px; border-radius: 10px" class="myCard">
    <el-row>
      <el-col v-if="!isEditMode" :span="16" style="padding-top: 0.5em">
        <el-text truncated>{{ props.dialogTitle }}</el-text>
      </el-col>
      <el-col :span="16" v-else>
        <el-input v-model="newTitle" placeholder="请输入标题" clearable/>
      </el-col>
      <el-col :span="8">
        <el-button v-if="!isEditMode" :icon="Edit" circle @click="UseEditMode"/>
        <el-button v-else :icon="Check" circle @click="EditComplete"/>
        <el-button circle @click="DeleteDialog">
          <el-icon color="red">
            <Delete/>
          </el-icon>
        </el-button>
      </el-col>
    </el-row>
  </el-card>
</template>

<style scoped>
.myCard:hover {
  cursor: pointer;
}
</style>