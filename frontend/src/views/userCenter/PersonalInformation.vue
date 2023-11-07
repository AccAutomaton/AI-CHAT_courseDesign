<!--suppress JSValidateTypes -->
<script setup>

import {Aim, CreditCard, Iphone, Lock, Postcard, Star, User, UserFilled} from "@element-plus/icons-vue";
import {ref} from "vue";
import {useStore} from "vuex";
import axiosService from "@/request";
import {ElNotification} from "element-plus";

const store = useStore();

const nickname = ref(""), status = ref(""), phone = ref(""), userType = ref(""),
    balance = ref(0), vipLevel = ref("");

const getInformation = () => {
  axiosService({
    url: "/user/get/Information",
    method: "get",
  }).then(data => {
    nickname.value = data["user"]["nickname"];
    status.value = data["user"]["status"];
    phone.value = data["user"]["phone"];
    userType.value = data["user"]["userType"];
    balance.value = data["user"]["balance"];
    vipLevel.value = data["vipLevel"];
  })
}

getInformation();

const reviseNicknameButtonText = ref("修改"), reviseNicknameInputDisabled = ref(true);
const revisePhoneButtonText = ref("修改"), revisePhoneInputDisabled = ref(true);
let tempNickname, tempPhone;
const ClickReviseNicknameButton = () => {
  if (reviseNicknameInputDisabled.value) {
    tempNickname = nickname.value;
    reviseNicknameInputDisabled.value = false;
    reviseNicknameButtonText.value = "确认";
    return;
  }
  if (nickname.value === "") {
    ElNotification({title: "昵称不能为空", type: "warning"});
    return;
  }
  if (nickname.value === tempNickname) {
    ElNotification({title: "修改前后不能一致", type: "warning"});
    reviseNicknameInputDisabled.value = true;
    reviseNicknameButtonText.value = "修改";
    return;
  }
  axiosService({
    url: "/user/set/nickname",
    method: "post",
    data: {
      "newNickname": nickname.value
    }
  }).then(data => {
    if (data["status"] === "success") {
      ElNotification({title: "修改成功", type: "success"});
      reviseNicknameInputDisabled.value = true;
      reviseNicknameButtonText.value = "修改";
    }
  })
}
const ClickRevisePhoneButton = () => {
  if (revisePhoneInputDisabled.value) {
    tempPhone = phone.value
    revisePhoneInputDisabled.value = false;
    revisePhoneButtonText.value = "确认";
    return;
  }
  if (phone.value === "") {
    ElNotification({title: "手机号不能为空", type: "warning"});
    return;
  }
  if (!phone.value.match(/^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\d{8}$/)) {
    ElNotification({title: "手机号不合法", type: "warning"});
    return;
  }
  if (phone.value === tempPhone) {
    ElNotification({title: "修改前后不能一致", type: "warning"});
    revisePhoneInputDisabled.value = true;
    revisePhoneButtonText.value = "修改";
    return;
  }
  axiosService({
    url: "/user/set/phone",
    method: "post",
    data: {
      "newPhone": phone.value
    }
  }).then(data => {
    if (data["status"] === "success") {
      ElNotification({title: "修改成功", type: "success"});
      revisePhoneInputDisabled.value = true;
      revisePhoneButtonText.value = "修改";
    }
  })
}
</script>

<template>
  <div style="margin-left: 2rem; margin-top: 0.5rem">
    <h3>个人信息</h3>
    <el-descriptions
        class="margin-top"
        :column="2"
        size="default"
        border
    >
      <el-descriptions-item width="15rem">
        <template #label>
          <div class="cell-item">
            <el-icon>
              <User/>
            </el-icon>
            UID
          </div>
        </template>
        {{ store.getters.getUid }}
      </el-descriptions-item>
      <el-descriptions-item width="15rem">
        <template #label>
          <div class="cell-item">
            <el-icon>
              <UserFilled/>
            </el-icon>
            用户名
          </div>
        </template>
        {{ store.getters.getUsername }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <Aim/>
            </el-icon>
            昵称
          </div>
        </template>
        <el-input
            v-model="nickname"
            placeholder="请输入昵称"
            :disabled="reviseNicknameInputDisabled"
        >
          <template #append>
            <el-button @click="ClickReviseNicknameButton">
              {{ reviseNicknameButtonText }}
            </el-button>
          </template>
        </el-input>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <Lock/>
            </el-icon>
            账户状态
          </div>
        </template>
        <el-tag size="small">{{ status }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <Iphone/>
            </el-icon>
            手机号
          </div>
        </template>
        <el-input
            v-model="phone"
            placeholder="请输入手机号"
            :disabled="revisePhoneInputDisabled"
        >
          <template #append>
            <el-button @click="ClickRevisePhoneButton">
              {{ revisePhoneButtonText }}
            </el-button>
          </template>
        </el-input>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <CreditCard/>
            </el-icon>
            余额
          </div>
        </template>
        {{ balance / 1000 + "元" }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <Postcard/>
            </el-icon>
            账户类型
          </div>
        </template>
        {{ userType }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <Star/>
            </el-icon>
            会员等级
          </div>
        </template>
        {{ vipLevel }}
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<style scoped>
</style>