<template>

  群成员列表
  <div class="group-member-list">
    <el-scrollbar>
      <div
        v-for="member in props.groupMembers"
        :key="member.memberId"
        class="member-item"
      >
        <!-- 左边：用户头像 -->
        <el-avatar :src="getImage(member.user.avatar)" class="avatar" />

        <!-- 中间：用户名和成员角色 -->
        <div class="member-info">
          <div class="username">{{ member.user.nickName }}</div>
          <div class="role">{{ roleMap[member.role] }}</div>
        </div>

        <!-- 右边：移除按钮（仅当自己的角色 > 当前成员角色时显示） -->
        <el-button
          v-if="self.relationShip.data.role > member.role"
          type="danger"
          size="small"
          @click="handleRemove(member)"
        >
          移除
        </el-button>
      </div>
    </el-scrollbar>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { ElMessageBox } from 'element-plus';
import {deleteMember} from '@/api/functions/groupMember.js'
import {getImage}from "@/api/functions/image.js"
const props = defineProps({
  groupMembers: {
    required: true,
  },
  self: {
    type: Object,
    required: true,
  },
});
console.log(">>>GroupMemberList.vue: props", props.groupMembers, props.self)
// 角色映射
const roleMap = {
  '0': '普通成员',
  '1': '管理员',
  '2': '群主',
};

// 处理移除成员
const handleRemove = (member) => {
  ElMessageBox.confirm(`确定要移除成员 ${member.user.nickName} 吗？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      // 调用 API 移除成员
      console.log('移除成员:', member);
      // 这里可以调用 API 执行移除操作
      deleteMember(member.memberId)
    })
    .catch(() => {
      // 用户取消操作
    });
};
</script>

<style scoped>
.group-member-list {
  padding: 16px;
}

.member-item {
  display: flex;
  align-items: center;
  padding: 8px;
  border-bottom: 1px solid #eee;
}

.avatar {
  width: 40px;
  height: 40px;
  margin-right: 12px;
}

.member-info {
  flex: 1;
}

.username {
  font-weight: bold;
}

.role {
  font-size: 12px;
  color: #666;
}

.el-button {
  margin-left: auto;
}
</style>