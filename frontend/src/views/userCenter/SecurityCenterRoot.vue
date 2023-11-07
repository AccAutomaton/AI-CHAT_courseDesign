<script setup>
import PasswordControllerView from "@/views/userCenter/securityCenter/PasswordControllerView.vue";
</script>

<template>
  <div style="margin-left: 2rem">
    <h3>安全中心</h3>
    <el-tabs tab-position="top">
      <el-tab-pane label="密码管理" >
        <PasswordControllerView />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style scoped>

</style>