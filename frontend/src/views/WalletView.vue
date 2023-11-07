<script setup>
import {ref, watch} from "vue";
import axiosService from "@/request";
import {ElNotification} from "element-plus";

const balance = ref(0.0);
const tableData = ref([]);
const radio = ref("none");
const pageNum = ref(1), pageSize = ref(10);
const pageCount = ref(1);
const dialogFormVisible = ref(false);

const GetBalance = () => {
  axiosService({
    url: "/recharge/getBalance",
    method: "get"
  }).then(data => {
    balance.value = data["balance"] / 1000;
  })
}
GetBalance();

const GetRechargeList = () => {
  axiosService({
    url: "/recharge/getList/" + pageNum.value + "/" + pageSize.value,
    method: "get",
    params: {
      mode: radio.value
    }
  }).then(data => {
    tableData.value.length = 0;
    tableData.value = data["records"];
    pageCount.value = data["pages"];
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
GetRechargeList();

watch(pageNum, () => {
  GetRechargeList();
})

const Recharge = value => {
  axiosService({
    url: "/recharge/do",
    method: "post",
    data: {
      value: value
    }
  }).then(data => {
    balance.value = data["newBalance"] / 1000;
    dialogFormVisible.value = false;
    ElNotification({title: "充值成功", type: "success", message: "成功充值" + value / 1000 + "元"})
    GetRechargeList();
  })
}
</script>

<template>
  <el-row :gutter="20" style="text-align: center">
    <el-col :span="24">
      <span>余额 </span>
      <span class="money">{{ balance }}</span>
      <span> 元</span>
      <el-button style="margin-left: 20px" type="success" round size="small" @click="dialogFormVisible = true">
        充值
      </el-button>
    </el-col>
  </el-row>
  <el-divider>交易记录</el-divider>
  <el-row :gutter="20" style="text-align: center">
    <el-col :span="6"/>
    <el-col :span="12">
      <el-table :data="tableData" style="width: 100%; height: 31.4em">
        <el-table-column label="交易ID" prop="id"/>
        <el-table-column label="交易金额" prop="value"/>
        <el-table-column label="余额" prop="balance"/>
        <el-table-column label="交易时间" prop="createTime"/>
      </el-table>
      <br>
      <el-radio-group v-model="radio" class="ml-4" style="float: left" @change="GetRechargeList">
        <el-radio label="none">所有</el-radio>
        <el-radio label="in">充值</el-radio>
        <el-radio label="out">消费</el-radio>
      </el-radio-group>
      <el-pagination style="float: right" layout="prev, pager, next" :page-count="pageCount"
                     v-model:current-page="pageNum"/>
    </el-col>
    <el-col :span="6"/>
  </el-row>
  <el-dialog v-model="dialogFormVisible" title="充值">
    <el-row :gutter="20" style="text-align: center">
      <el-col :span="6">
        <el-button plain class="recharge" @click="Recharge(10000)">
          <span class="money">10</span>元
        </el-button>
      </el-col>
      <el-col :span="6">
        <el-button plain class="recharge" @click="Recharge(20000)">
          <span class="money">20</span>元
        </el-button>
      </el-col>
      <el-col :span="6">
        <el-button plain class="recharge" @click="Recharge(50000)">
          <span class="money">50</span>元
        </el-button>
      </el-col>
      <el-col :span="6">
        <el-button plain class="recharge" @click="Recharge(100000)">
          <span class="money">100</span>元
        </el-button>
      </el-col>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.recharge {
  width: 10em;
  height: 10em;
}

.money {
  font-size: xxx-large;
  font-weight: bolder
}
</style>