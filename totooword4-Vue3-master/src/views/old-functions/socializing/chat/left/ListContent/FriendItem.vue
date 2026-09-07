<template>
  <div class="friend-item" @click="$emit('click', friend)" @dblclick="$emit('dblclick', friend)">
    <!-- 头像 -->
    <el-avatar :src="friend.avatar" />

    <!-- 昵称 -->
    <div class="friend-info">
      <span class="nickname">{{ friend.nickName }}</span>
      <span v-if="type === 'needToBeProcessed'" class="actions">
        <el-button type="success" @click.stop="$emit('accept')">通过</el-button>
        <el-button type="danger" @click.stop="$emit('reject')">拒绝</el-button>
      </span>
      <span v-else-if="type === 'awaited'" class="status">
        {{ friend.status === '0' ? '等待处理' : '被拒绝' }}
      </span>
    </div>
  </div>
</template>

<script setup>
defineProps({
  friend: Object, // 好友信息
  type: String, // 类型（needToBeProcessed/awaited/passed）
});

defineEmits(['accept', 'reject', 'click', 'dblclick']);
</script>

<style scoped>
.friend-item {
  display: flex;
  align-items: center;
  padding: 8px;
  border-bottom: 1px solid #eee;
}

.friend-info {
  margin-left: 12px;
  flex: 1;
}

.nickname {
  font-weight: bold;
}

.actions {
  float: right;
}

.status {
  float: right;
  color: #999;
}
</style>
