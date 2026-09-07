<template>
  <div class="group-item" @click="$emit('click', group)" @dblclick="$emit('dblclick', group)">
    <!-- 头像 -->
    <el-avatar :src="group.avatarUrl" />

    <!-- 群组信息 -->
    <div class="group-info">
      <span class="group-name">{{ group.groupName }}</span>
      <span v-if="type === 'needToBeProcessed'" class="actions">
        <el-button type="success" @click.stop="$emit('accept')">通过</el-button>
        <el-button type="danger" @click.stop="$emit('reject')">拒绝</el-button>
      </span>
      <span v-else-if="type === 'awaited'" class="status">
        {{ group.status === '0' ? '等待处理' : '被拒绝' }}
      </span>
      <span v-else-if="type === 'passed'" class="role">
        角色：{{ group.role }}
      </span>
    </div>
  </div>
</template>

<script setup>
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