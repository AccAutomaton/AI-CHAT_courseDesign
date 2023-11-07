<script setup>
import PersonalInformation from "@/views/userCenter/PersonalInformation.vue";
import SecurityCenter from "@/views/userCenter/SecurityCenterRoot.vue";
</script>

<template>
  <el-row>
    <el-col :span="4"/>
    <el-col :span="16">
      <el-tabs tab-position="left" lazy>
        <el-tab-pane label="个人信息">
          <PersonalInformation/>
        </el-tab-pane>
        <el-tab-pane label="安全中心">
          <SecurityCenter/>
        </el-tab-pane>
      </el-tabs>
    </el-col>
    <el-col :span="4"/>
  </el-row>
</template>

<style scoped>
</style>