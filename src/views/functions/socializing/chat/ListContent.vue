<template>
  <div class="list-content">
    <el-scrollbar>
      <!-- 好友列表 -->
      <FriendList
          v-if="listType === 'friend'"
          :awaited="friendData.awaited"
          :needToBeProcessed="friendData.needToBeProcessed"
          :passed="friendData.passed"
          @item-click="handleItemClick"
          @item-dblclick="handleItemDblClick"
      />

      <!-- 群组列表 -->
      <GroupList
          v-else-if="listType === 'group'"
          :awaited="groupData.awaited"
          :needToBeProcessed="groupData.needToBeProcessed"
          :passed="groupData.passed"
          :refused="groupData.refused"
          @item-click="handleItemClick"
          @item-dblclick="handleItemDblClick"
      />
    </el-scrollbar>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { getFriendList } from '@/api/functions/friend.js';
import {getGroupList } from '@/api/functions/group.js';
import FriendList from './FriendList.vue';
import GroupList from './GroupList.vue';

const props = defineProps({
  listType: String, // 列表类型（friend/group）
});

const emit = defineEmits(['item-click', 'item-dblclick']);

const friendData = ref({
  awaited: [], // 等待验证的好友
  needToBeProcessed: [], // 需要处理的好友验证
  passed: [], // 已通过的好友
});

const groupData = ref({
  awaited: [], // 等待验证的群组
  needToBeProcessed: [], // 需要处理的群组验证
  passed: [], // 已通过的群组
  refused: [], // 被拒绝的群组
});

// 根据列表类型加载数据
watch(
    () => props.listType,
    async (type) => {
      if (type === 'friend') {
        const res = await getFriendList();
        friendData.value = res.data;
      } else if (type === 'group') {
        const res = await getGroupList();
        groupData.value = res.data;
      }
    },
    { immediate: true }
);

const handleItemClick = (item) => {
  emit('item-click', item);
};

const handleItemDblClick = (item) => {
  emit('item-dblclick', item);
};
</script>

<style scoped>
.list-content {
  flex: 1;
  padding: 16px;
}
</style>