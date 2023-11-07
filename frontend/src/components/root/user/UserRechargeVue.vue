<script setup>
import axiosService from "@/request";
import {ref, watch} from "vue";

// eslint-disable-next-line no-undef
const props = defineProps(["uid"])

const tableData = ref([]);
const pageNum = ref(1), pageSize = ref(10);
const pageCount = ref(1);
const radio = ref("none");

const GetRechargeRecords = () => {
  axiosService({
    url: "/root/recharge/getList/" + pageNum.value + "/" + pageSize.value,
    method: "get",
    params: {
      mode: radio.value,
      uid: props.uid
    }
  }).then(data => {
    tableData.value.length = 0;
    pageCount.value = data["pages"];
    tableData.value = data["records"];
    for (let i = 0; i < tableData.value.length; i++) {
      if (tableData.value[i].value > 0) {
        tableData.value[i].value = "+" + tableData.value[i].value / 1000 + "元";
      } else {
        tableData.value[i].value = tableData.value[i].value / 1000 + "元";
      }
      tableData.value[i].balance = tableData.value[i].balance / 1000 + "元";
    }
  })
}
GetRechargeRecords();

watch(pageNum, () => {
  GetRechargeRecords();
})
</script>

<template>
  <el-table :data="tableData" style="width: 100%; height: 31.4em">
    <el-table-column label="交易ID" prop="id"/>
    <el-table-column label="交易金额" prop="value"/>
    <el-table-column label="余额" prop="balance"/>
    <el-table-column label="交易时间" prop="createTime"/>
  </el-table>
  <br>
  <el-radio-group v-model="radio" class="ml-4" style="float: left" @change="GetRechargeRecords">
    <el-radio label="none">所有</el-radio>
    <el-radio label="in">充值</el-radio>
    <el-radio label="out">消费</el-radio>
  </el-radio-group>
  <el-pagination style="float: right" layout="prev, pager, next" :page-count="pageCount"
                 v-model:current-page="pageNum"/>
</template>

<style scoped>

</style>