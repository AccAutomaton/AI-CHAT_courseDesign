<script setup>
import {ref, watch} from "vue";
import axiosService from "@/request";
import UpdateUserDialog from "@/components/root/user/UpdateUserDialog.vue";
import {ElMessageBox, ElNotification} from "element-plus";
import UserRechargeVue from "@/components/root/user/UserRechargeVue.vue";

const tableData = ref([]);
const pageNum = ref(1), pageSize = ref(10);
const pageCount = ref(1);
const informationDialogFormVisible = ref(false);
const informationDialogFormData = ref({});
const rechargeDialogFormVisible = ref(false);
const rechargeDialogFormUid = ref(0);

const GetUserDetail = () => {
  axiosService({
    url: "/root/user/getAll/" + pageNum.value + "/" + pageSize.value,
    method: "get"
  }).then(data => {
    tableData.value.length = 0;
    tableData.value = data["records"];
    pageCount.value = data["pages"];
    for (let i = 0; i < tableData.value.length; i++) {
      tableData.value[i].balance /= 1000;
    }
  })
}
GetUserDetail();

watch(pageNum, () => {
  GetUserDetail();
})

const handleEdit = data => {
  informationDialogFormData.value = data;
  informationDialogFormVisible.value = true;
}

const OnInformationDialogClose = () => {
  informationDialogFormVisible.value = false;
}

const OnInformationUpdateSuccess = () => {
  GetUserDetail();
  ElNotification({title: "修改成功", type: "success"})
}

const handleDelete = data => {
  ElMessageBox.confirm(
      "真的要删除用户: " + data.username + ", UID: " + data.uid + " 吗?",
      "警告: 即将删除用户",
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
      axiosService({
        "url": "/root/user/delete/" + data.uid,
        "method": "delete",
      }).then(data => {
        if (data["status"] === "success") {
          GetUserDetail();
          ElNotification({title: "删除成功", type: "success"});
        }
      })
  }).catch(() => {
  });
}

const OnBalanceButtonClick = uid => {
  rechargeDialogFormUid.value = uid;
  rechargeDialogFormVisible.value = true;
}

const OnRechargeDialogFormClosed = () => {
  rechargeDialogFormVisible.value = false;
}
</script>

<template>
  <el-table :data="tableData" style="width: 100%; height: 32.8em">
    <el-table-column label="用户ID" fixed="left" prop="uid" width="100"/>
    <el-table-column label="用户名" prop="username" width="100"/>
    <el-table-column label="昵称" prop="nickname" width="100"/>
    <el-table-column label="账户状态" prop="status" width="100"/>
    <el-table-column label="手机号" prop="phone" width="125"/>
    <el-table-column label="账户类别" prop="userType" width="100"/>
    <el-table-column label="余额" prop="balance" width="100">
      <template #default="scope">
        <el-button link type="primary" @click="OnBalanceButtonClick(scope.row.uid)">
          {{ scope.row.balance }}
        </el-button>
      </template>
    </el-table-column>
    <el-table-column label="创建时间" prop="createTime" width="175"/>
    <el-table-column label="更新时间" prop="updateTime" width="175"/>
    <el-table-column label="会员等级" prop="vipLevel" width="100"/>
    <el-table-column label="操作" fixed="right" width="150">
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
  <el-pagination layout="prev, pager, next" :page-count="pageCount" v-model:current-page="pageNum"
                 style="float: right"/>
  <UpdateUserDialog :dialog-form-visible="informationDialogFormVisible" :dialog-form-data="informationDialogFormData" @OnClose="OnInformationDialogClose"
                    @UpdateSuccess="OnInformationUpdateSuccess"/>
  <el-dialog v-model="rechargeDialogFormVisible" title="交易记录" @closed="OnRechargeDialogFormClosed" destroy-on-close>
    <UserRechargeVue :uid="rechargeDialogFormUid"/>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="OnRechargeDialogFormClosed" type="info" plain>关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>

</style>