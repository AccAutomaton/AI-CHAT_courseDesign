<script setup>

import {ChatDotRound, House, Money, Promotion, Setting, SwitchButton, User} from "@element-plus/icons-vue";
import store from "@/store";
import {computed} from "vue";
import {GetRoughUserInformation, Logout} from "@/request/user";

GetRoughUserInformation();

const isLogin = computed(() => store.getters.getIsLogin);
const nickname = computed(() => store.getters.getNickname);
const userType = computed(() => store.getters.getUserType);

</script>

<template>
  <el-container>
    <el-header>
      <el-affix :offset="0">
        <el-menu
            :default-active="$route.path"
            class="el-menu-demo"
            mode="horizontal"
            :ellipsis="false"
            router
            popper-effect="light"
        >
          <el-menu-item>
            <el-container>
              <img src="@/assets/favicon.png" alt="" style="height: 3.5rem"/>
              <div style="font-size: x-large; font-weight: bolder; margin-left: 5px">AI Chat</div>
            </el-container>
          </el-menu-item>
          <el-menu-item index="/"><el-icon><ChatDotRound /></el-icon>对话</el-menu-item>
          <el-menu-item index="/wallet"><el-icon><Money /></el-icon>钱包</el-menu-item>
          <div class="flex-grow"/>
          <el-sub-menu v-if="isLogin">
            <template #title><el-icon><User /></el-icon>
              {{
                nickname
              }}
            </template>
            <el-menu-item index="/root" v-if="userType === '根用户'"><el-icon><Setting /></el-icon>后台管理</el-menu-item>
            <el-menu-item index="/userCenter"><el-icon><House /></el-icon>个人中心</el-menu-item>
            <el-menu-item @click="Logout"><el-icon><SwitchButton /></el-icon>退出登录</el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/login" v-else><el-icon><Promotion /></el-icon>登录</el-menu-item>
        </el-menu>
      </el-affix>
    </el-header>
    <el-main>
      <slot></slot>
    </el-main>
    <el-footer>
      <el-divider>
        <el-text class="mx-1">
          © 2023 AI Chat All Rights Reserved.
        </el-text>
      </el-divider>
    </el-footer>
  </el-container>
</template>

<style scoped>
.flex-grow {
  flex-grow: 2;
}
</style>