<script setup>

import {Lock, UserFilled} from "@element-plus/icons-vue";
import {ref} from "vue";
import router from "@/router";
import {ElMessageBox, ElNotification} from "element-plus";
import axiosService from "@/request";

const username = ref(""), password = ref(""), confirmPassword = ref("");

const CheckUsername = () => {
  if (username.value === "") {
    ElNotification({title: "用户名不能为空", type: "error"});
    return false;
  }
  if (!username.value.match(/^[a-zA-Z0-9_-]{4,16}$/)) {
    ElNotification({title: "用户名不合法", message: "应由4-16位字母,数字,'_','-'构成", type: "error"})
    return false;
  }
  return true;
}

const CheckPassword = () => {
  if (password.value === "") {
    ElNotification({title: "密码不能为空", type: "error"});
    return false;
  }
  if (!password.value.match(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/)) {
    ElNotification({title: "密码不合法", message: "至少8位,且同时包含字母和数字(不含特殊符号)", type: "error"})
    return false;
  }
  return true;
}

const CheckConfirmPassword = () => {
  if (password.value !== confirmPassword.value) {
    ElNotification({title: "两次密码不一致", type: "error"});
    return false;
  }
  return true;
}

const Register = () => {
  if (CheckUsername() && CheckPassword() && CheckConfirmPassword()) {
    axiosService({
      url: "/register",
      method: "post",
      data: {
        "username": username.value,
        "password": password.value,
      }
    }).then(data => {
      if (data["status"] === "success") {
        ElMessageBox.alert('即将前往登录页面', '注册成功', {
          confirmButtonText: '确认',
          callback: () => {
            router.push("/login")
          },
        })
      }
    })
  }
}
</script>

<template>
  <div style="width: 100%" class="root-div">
    <el-card class="box-card" style="text-align: center; margin: 0 auto">
      <h1>注册</h1>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-input v-model="username" placeholder="请输入用户名" :prefix-icon="UserFilled" size="large"
                    @change="CheckUsername"
                    minlength="4" maxlength="16"/>
        </el-col>
      </el-row>
      <br>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-input v-model="password" placeholder="请输入密码" :prefix-icon="Lock" size="large" type="password"
                    @change="CheckPassword"
                    show-password minlength="8" maxlength="20"/>
        </el-col>
      </el-row>
      <br>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-input v-model="confirmPassword" placeholder="请确认密码" :prefix-icon="Lock" size="large" type="password"
                    @change="CheckConfirmPassword"
                    show-password minlength="8" maxlength="20"/>
        </el-col>
      </el-row>
      <br>
      <el-row :gutter="20">
        <el-col :span="8"/>
        <el-col :span="8">
          <div class="flex justify-space-between mb-4 flex-wrap gap-4">
            <el-button type="primary" text @click="router.push('/login')">
              已有账号？
            </el-button>
          </div>
        </el-col>
        <el-col :span="8"/>
      </el-row>
      <br>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-button type="primary" round plain style="width: 70%;" @click="Register">注册</el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<style scoped>
.box-card {
  width: 480px;
}
</style>