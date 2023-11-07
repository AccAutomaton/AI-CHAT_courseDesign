<script setup>

import {ChatRound, ShoppingCart, Star, User} from "@element-plus/icons-vue";

const defaultOpened = ["0","1","2","3"];
</script>

<template>
  <el-container>
    <el-aside>
      <el-row class="tac">
        <el-col :span="20">
          <h3 class="mb-2">后台管理</h3>
          <el-menu class="el-menu-vertical-demo" :default-active="$route.path" router :default-openeds="defaultOpened">
            <el-sub-menu index="0">
              <template #title>
                <el-icon>
                  <User/>
                </el-icon>
                <span>用户管理</span>
              </template>
              <el-menu-item index="/root/user/information">
                <template #title>用户信息</template>
              </el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="1">
              <template #title>
                <el-icon>
                  <Star/>
                </el-icon>
                <span>会员管理</span>
              </template>
              <el-menu-item index="/root/vip/description">
                <template #title>会员等级设置</template>
              </el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="2">
              <template #title>
                <el-icon>
                  <ChatRound/>
                </el-icon>
                <span>对话管理</span>
              </template>
              <el-menu-item index="/root/chat/management">
                <template #title>所有聊天</template>
              </el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="3">
              <template #title>
                <el-icon>
                  <ShoppingCart/>
                </el-icon>
                <span>消费管理</span>
              </template>
              <el-menu-item index="/root/recharge/rechargeRecords">
                <template #title>消费记录</template>
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-col>
      </el-row>
    </el-aside>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<style scoped>

</style>