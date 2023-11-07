<script setup>
import {ref, watch} from "vue";
import axiosService from "@/request";
import DoChat from "@/components/chat/DoChat.vue";
import {ElMessageBox, ElNotification} from "element-plus";

const tableData = ref([]);
const pageNum = ref(1), pageSize = ref(10);
const pageCount = ref(1);
const dialogVisible = ref(false);
const selectId = ref(0);

const GetChats = () => {
  axiosService({
    url: "/root/chat/getList/" + pageNum.value + "/" + pageSize.value,
    method: "get"
  }).then(data => {
    tableData.value.length = 0;
    pageCount.value = data["pages"];
    tableData.value = data["records"];
  })
}
GetChats();

watch(pageNum, () => {
  GetChats();
})

const handleView = data => {
  selectId.value = data.id;
  dialogVisible.value = true;
}

const handleDelete = data => {
  ElMessageBox.confirm(
      "真的要删除对话 ID: " + data.id + " 吗?",
      "警告: 即将删除对话",
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
      axiosService({
        "url": "/chat/delete/" + data.id,
        "method": "delete",
      }).then(data => {
        if (data["status"] === "success") {
          GetChats();
          ElNotification({title: "删除成功", type: "success"});
        }
      })
  }).catch(() => {
  });
}
</script>

<template>
  <el-table :data="tableData" style="width: 100%; height: 32.8em">
    <el-table-column label="对话ID" prop="id"/>
    <el-table-column label="用户ID" prop="uid"/>
    <el-table-column label="标题" prop="title"/>
    <el-table-column label="创建时间" prop="createTime"/>
    <el-table-column label="更新时间" prop="updateTime"/>
    <el-table-column label="操作" fixed="right" width="200">
      <template #default="scope">
        <el-button size="small" @click="handleView(scope.row)">
          查看详情
        </el-button>
        <el-button size="small" type="danger" @click="handleDelete(scope.row)">
          删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination layout="prev, pager, next" :page-count="pageCount" v-model:current-page="pageNum"
                 style="float: right"/>
  <el-dialog
    v-model="dialogVisible"
    title="Tips"
  >
    <DoChat v-if="dialogVisible" :dialog-id="selectId" :needInput="false"/>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>

</style>