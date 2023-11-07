<script setup>
import {ref, watch} from "vue";
import axiosService from "@/request";
import {ElMessageBox, ElNotification} from "element-plus";
import {Plus} from "@element-plus/icons-vue";

const tableData = ref([]);
const pageNum = ref(1), pageSize = ref(10);
const pageCount = ref(1);

const GetVips = () => {
  axiosService({
    url: "/root/vipLevel/get/all/" + pageNum.value + "/" + pageSize.value,
    method: "get"
  }).then(data => {
    tableData.value.length = 0;
    tableData.value = data["records"];
    pageCount.value = data["pages"];
  })
}
GetVips();

watch(pageNum, () => {
  GetVips();
})

const handleEdit = data => {
  ElMessageBox.prompt("请输入新等级名称", "修改会员等级", {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    inputValue: data.description,
  }).then(({value}) => {
    axiosService({
      url: "/root/vipLevel/update/" + data.id,
      method: "post",
      data: {
        "newName": value
      }
    }).then(data => {
      if (data["status"] === "success") {
        GetVips();
        ElNotification({title: "添加成功", type: "success"});
      }
    })
  }).catch(() => {
  })
}

const handleDelete = data => {
  ElMessageBox.confirm(
      "真的要删除会员等级: " + data.description + " 吗?",
      "警告: 即将删除会员等级",
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    axiosService({
      "url": "/root/vipLevel/delete/" + data.id,
      "method": "delete",
    }).then(data => {
      if (data["status"] === "success") {
        GetVips();
        ElNotification({title: "删除成功", type: "success"});
      }
    })
  }).catch(() => {
  });
}

const handleAddVipDescription = () => {
  ElMessageBox.prompt("请输入想要添加的等级", "添加会员等级", {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
  }).then(({value}) => {
    axiosService({
      url: "/root/vipLevel/add",
      method: "post",
      data: {
        "levelName": value
      }
    }).then(data => {
      if (data["status"] === "success") {
        GetVips();
        ElNotification({title: "添加成功", type: "success"});
      }
    })
  }).catch(() => {
  })
}
</script>

<template>
  <el-table :data="tableData" style="height: 32.8em">
    <el-table-column label="ID" prop="id"/>
    <el-table-column label="会员等级" prop="description"/>
    <el-table-column label="操作" fixed="right">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.row)">
          修改
        </el-button>
        <el-button size="small" type="danger" @click="handleDelete(scope.row)">
          删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <br>
  <el-button type="primary" style="float: left" plain @click="handleAddVipDescription">
    <el-icon>
      <Plus/>
    </el-icon>
    <span>
      添加
    </span>
  </el-button>
  <el-pagination layout="prev, pager, next" :page-count="pageCount" v-model:current-page="pageNum"
                 style="float: right"/>
</template>

<style scoped>

</style>