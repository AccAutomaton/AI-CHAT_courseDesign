<script setup>

import {UserFilled, Lock} from "@element-plus/icons-vue";
import {ref} from "vue";
import router from "@/router";
import {ElNotification} from "element-plus";
import axiosService from "@/request";
import {useStorage} from "@vueuse/core";
import store from "@/store";

const username = ref(""), password = ref("");
const loginButtonDisabled = ref(true);

const checkIfEnableLoginButton = () => {
  loginButtonDisabled.value = !(username.value.length >= 4 && username.value.length <= 16 &&
      password.value.length >= 8 && password.value.length <= 20);
}

const Login = () => {
  axiosService({
    url: "/login",
    method: "post",
    data: {
      "username": username.value,
      "password": password.value
    }
  }).then(data => {
    if (data != null) {
      const authorization = useStorage("Authorization", "");
      authorization.value = data["Authorization"];
      store.commit("setIsLogin", true);
      store.commit("setAuthorization", data["Authorization"]);
      store.commit("setUid", data["uid"]);
      store.commit("setNickname", data["nickname"]);
      store.commit("setUsername", data["username"]);
      store.commit("setUserType", data["userType"]);
      ElNotification({
        "title": "登录成功",
        "type": "success"
      });
      router.push("/");
    }
  })
}
</script>

<template>
  <div style="width: 100%" class="root-div">
    <el-card class="box-card" style="text-align: center; margin: 0 auto">
      <h1>登录</h1>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-input v-model="username" placeholder="请输入用户名" :prefix-icon="UserFilled" size="large"
                    @input="checkIfEnableLoginButton" minlength="4" maxlength="16"/>
        </el-col>
      </el-row>
      <br>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-input v-model="password" placeholder="请输入密码" :prefix-icon="Lock" size="large" type="password"
                    @input="checkIfEnableLoginButton"
                    show-password minlength="8" maxlength="20"/>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="8"/>
        <el-col :span="8">
          <div class="flex justify-space-between mb-4 flex-wrap gap-4">
            <el-button type="primary" text @click="router.push('/register')">
              还没有账号？
            </el-button>
          </div>
        </el-col>
        <el-col :span="8"/>
      </el-row>
      <br>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-button type="primary" round plain style="width: 70%;" v-bind:disabled="loginButtonDisabled"
                     @click="Login">登录
          </el-button>
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