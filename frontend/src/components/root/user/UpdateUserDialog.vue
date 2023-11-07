<script setup>

import {Aim, CreditCard, Iphone, Lock, Postcard, Star, User, UserFilled} from "@element-plus/icons-vue";
import {computed, ref, watch} from "vue";
import axiosService from "@/request";

// eslint-disable-next-line no-undef
const props = defineProps({
  dialogFormVisible: Boolean,
  dialogFormData: Object
});
// eslint-disable-next-line no-undef
const emit = defineEmits(["OnClose", "UpdateSuccess"]);

const dialogFormVisible = computed(() => props.dialogFormVisible);
const dialogFormData = computed(() => props.dialogFormData);

const newNickname = ref(dialogFormData.value.nickname), newPhone = ref(dialogFormData.value.phone),
    newStatus = ref(dialogFormData.value.status === "正常"),
    newUserType = ref(dialogFormData.value.userType), newVipLevel = ref(dialogFormData.value.vipLevel);

const vipLevelList = ref([]);

const UpdateVipLevelList = () => {
  axiosService({
    url: "/root/vipLevel/get/all",
    method: "get"
  }).then(data => {
    vipLevelList.value.length = 0;
    vipLevelList.value = data;
  })
}

const UpdateData = () => {
  newNickname.value = dialogFormData.value.nickname;
  newPhone.value = dialogFormData.value.phone;
  newUserType.value = dialogFormData.value.userType;
  newVipLevel.value = dialogFormData.value.vipLevel;
  newStatus.value = dialogFormData.value.status === "正常";
  UpdateVipLevelList();
}

watch(dialogFormVisible, newValue => {
  if (newValue === true) {
    UpdateData();
  }
})

watch(dialogFormData.value, () => {
  UpdateData();
})

const OnClose = () => {
  newNickname.value = "";
  newPhone.value = "";
  newUserType.value = "";
  newVipLevel.value = "";
  newStatus.value = false;
  emit("OnClose");
}

const OnSubmit = () => {
  axiosService({
    url: "/root/user/update",
    method: "post",
    data: {
      "uid": dialogFormData.value.uid,
      "newNickname": newNickname.value,
      "newStatus": newStatus.value,
      "newPhone": newPhone.value,
      "newUserType": newUserType.value,
      "newVipLevel": newVipLevel.value
    }
  }).then(data => {
    if (data["status"] === "success") {
      OnClose();
      emit("UpdateSuccess")
    }
  })
}
</script>

<template>
  <el-dialog v-model="dialogFormVisible" title="修改用户信息" @closed="OnClose">
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
        {{ dialogFormData.uid }}
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
        {{ dialogFormData.username }}
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
        <el-input v-model="newNickname" placeholder="请输入昵称"/>
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
        <el-switch
            v-model="newStatus"
            class="mb-2"
            style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
            active-text="正常"
            inactive-text="锁定"
        />
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
        <el-input v-model="newPhone" placeholder="请输入手机号"/>
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
        {{ dialogFormData.balance + "元" }}
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
        <el-select v-model="newUserType" class="m-2" placeholder="Select">
          <el-option key="0" label="根用户" value="0"/>
          <el-option key="1" label="管理员" value="1"/>
          <el-option key="2" label="用户" value="2"/>
        </el-select>
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
        <el-select v-model="newVipLevel" class="m-2" placeholder="Select">
          <el-option v-for="item in vipLevelList" :key="item.id" :label="item.description" :value="item.description"/>
        </el-select>
      </el-descriptions-item>
    </el-descriptions>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="OnClose" type="info">取消</el-button>
        <el-button @click="OnSubmit" type="success">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>

</style>