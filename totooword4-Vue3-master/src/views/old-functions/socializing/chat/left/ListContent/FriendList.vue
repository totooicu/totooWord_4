<template>
  <div>
    <!-- 需要处理的好友验证 -->
    <div v-if="needToBeProcessed.length > 0">
      <h4>需要处理的好友验证</h4>
      <FriendItem
          v-for="friend in needToBeProcessed"
          :key="friend.userId"
          :friend="friend"
          type="needToBeProcessed"
          @accept="handleAccept(friend)"
          @reject="handleReject(friend)"
          @click="handleItemClick(friend)"
          @dblclick="handleItemDblClick(friend)"
      />
    </div>

    <!-- 等待验证的好友 -->
    <div v-if="awaited.length > 0">
      <h4>等待验证的好友</h4>
      <FriendItem
          v-for="friend in awaited"
          :key="friend.userId"
          :friend="friend"
          type="awaited"
          @click="handleItemClick(friend)"
          @dblclick="handleItemDblClick(friend)"
      />
    </div>

    <!-- 已通过的好友 -->
    <div v-if="passed.length > 0">
      <h4>已通过的好友</h4>
      <FriendItem
          v-for="friend in passed"
          :key="friend.userId"
          :friend="friend"
          type="passed"
          @click="handleItemClick(friend)"
          @dblclick="handleItemDblClick(friend)"
      />
    </div>
  </div>
</template>

<script setup>
import FriendItem from './FriendItem.vue';

const props = defineProps({
  awaited: Array, // 等待验证的好友
  needToBeProcessed: Array, // 需要处理的好友验证
  passed: Array, // 已通过的好友
});

const emit = defineEmits(['item-click', 'item-dblclick']);

const handleAccept = (friend) => {
  console.log('接受好友请求:', friend);
  // 调用 API 处理接受逻辑
};

const handleReject = (friend) => {
  console.log('拒绝好友请求:', friend);
  // 调用 API 处理拒绝逻辑
};

const handleItemClick = (friend) => {
  emit('item-click', friend);
};

const handleItemDblClick = (friend) => {
  emit('item-dblclick', friend);
};
</script>
