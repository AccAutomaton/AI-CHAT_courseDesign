<script setup>
import NavBar from "@/components/NavBar.vue";
</script>

<template>
  <NavBar>
    <router-view/>
  </NavBar>
</template>

<style>
@font-face {
  font-family: 小米兰亭;
  src: url('assets/css/MI_LanTing_Regular.ttf');
}
body {
  font-family: 小米兰亭, 微软雅黑, system-ui;
}
</style>