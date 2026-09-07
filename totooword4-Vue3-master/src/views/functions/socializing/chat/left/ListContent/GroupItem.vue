<template>
  <div class="group-item" @click="$emit('click', group)" @dblclick="$emit('dblclick', group)">
    <!-- 头像 -->
    <el-avatar :src="(type === 'needToBeProcessed' ? group.user.avatar : group.avatarUrl)" />

    <!-- 群组信息 -->
    <div class="group-info">
      <span >
	  {{ type === 'needToBeProcessed' ? group.user.userName : group.groupName }}
	  <el-text v-if="type === 'needToBeProcessed' " size="small" >{{"请求加入"+group.group.groupName}} </el-text>
	
	  </span>
      <span v-if="type === 'needToBeProcessed'" class="actions">
        <el-button type="success" @click.stop="$emit('accept')">通过</el-button>
        <el-button type="danger" @click.stop="$emit('reject')">拒绝</el-button>
      </span>
      <span v-else-if="type === 'awaited'" class="status">
        {{ group.groupMembers[0].role === '3' ? '等待处理' : '被拒绝' }}
      </span>
      <span v-else-if="type === 'passed'" class="role">
        <dict-tag :options="group_member_role" :value="group.groupMembers[0].role"/>
      </span>
    </div>
  </div>
</template>

<script setup>
const { proxy } = getCurrentInstance();
const { group_member_role } = proxy.useDict('group_member_role');
// 获取用户头像
const getAvatar = (url) => {
  if (url.includes('http://') || url.includes('https://')) {
    return url;
  } else {
    // 如果不包含http://或https://，则拼接完整的URL
    return 'http://localhost/dev-api/' + url;
  }
};
defineProps({
  group: Object, // 群组信息
  type: String, // 类型（needToBeProcessed/awaited/passed/refused）
});

defineEmits(['accept', 'reject', 'click', 'dblclick']);
</script>

<style scoped>
.group-item {
  display: flex;
  align-items: center;
  padding: 8px;
  border-bottom: 1px solid #eee;
}

.group-info {
  margin-left: 12px;
  flex: 1;
}

.group-name {
  font-weight: bold;
}

.actions {
  float: right;
}

.status {
  float: right;
  color: #999;
}

.role {
  float: right;
  color: #666;
}
</style>