<script setup>

import {Check, Lock} from "@element-plus/icons-vue";
import {computed, ref} from "vue";
import {ElNotification} from "element-plus";
import axiosService from "@/request";

const oldPassword = ref(""), newPassword = ref(""), confirmPassword = ref("");
const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

const CheckPassword = (password) => {
  if (password === "") {
    ElNotification({title: "密码不能为空", type: "error"});
    return false;
  }
  if (!password.match(regex)) {
    ElNotification({title: "密码不合法", message: "至少8位,且同时包含字母和数字(不含特殊符号)", type: "error"})
    return false;
  }
  return true;
}

const CheckOldPassword = () => {
  return CheckPassword(oldPassword.value);
}

const CheckNewPassword = () => {
  return CheckPassword(newPassword.value);
}

const CheckConfirmPassword = () => {
  if (newPassword.value !== confirmPassword.value) {
    ElNotification({title: "两次新密码不一致", type: "error"});
    return false;
  }
  return true;
}

const EnableButton = computed(() => {
  return oldPassword.value.match(regex) && newPassword.value.match(regex) && confirmPassword.value.match(regex)
})

const Submit = () => {
  if (!(CheckOldPassword() && CheckNewPassword() && CheckConfirmPassword())) {
    return;
  }
  if (newPassword.value === oldPassword.value) {
    ElNotification({title: "新密码不能与旧密码一致", type: "error"});
    return;
  }
  axiosService({
    url: "/user/set/password",
    method: "post",
    data: {
      "oldPassword": oldPassword.value,
      "newPassword": newPassword.value
    }
  }).then(data => {
    if (data["status"] === "success") {
      ElNotification({title: "修改成功", type: "success"});
      oldPassword.value = "";
      newPassword.value = "";
      confirmPassword.value = "";
    }
  })
}
</script>

<template>
  <div style="margin: 0 auto; text-align: center">
    <el-row :gutter="20" align="middle">
      <el-col :span="3"/>
      <el-col :span="3">
        原密码
      </el-col>
      <el-col :span="12">
        <el-input v-model="oldPassword" placeholder="请输入原密码" :prefix-icon="Lock" size="large" type="password"
                  @change="CheckPassword"
                  show-password minlength="8" maxlength="20"/>
      </el-col>
      <el-col :span="6"/>
    </el-row>
    <br>
    <el-row :gutter="20" align="middle">
      <el-col :span="3"/>
      <el-col :span="3">
        新密码
      </el-col>
      <el-col :span="12">
        <el-input v-model="newPassword" placeholder="请输入新密码" :prefix-icon="Lock" size="large" type="password"
                  @change="CheckPassword"
                  show-password minlength="8" maxlength="20"/>
      </el-col>
      <el-col :span="6"/>
    </el-row>
    <br>
    <el-row :gutter="20" align="middle">
      <el-col :span="3"/>
      <el-col :span="3">
        确认密码
      </el-col>
      <el-col :span="12">
        <el-input v-model="confirmPassword" placeholder="请确认密码" :prefix-icon="Lock" size="large" type="password"
                  @change="CheckConfirmPassword"
                  show-password minlength="8" maxlength="20"/>
      </el-col>
      <el-col :span="6"/>
    </el-row>
    <br>
    <el-row :gutter="20" align="middle">
      <el-col :span="22">
        <el-button :icon="Check" type="primary" round @click="Submit" :disabled="!EnableButton">修改密码</el-button>
      </el-col>
      <el-col :span="2"/>
    </el-row>
  </div>
</template>

<style scoped>

</style>