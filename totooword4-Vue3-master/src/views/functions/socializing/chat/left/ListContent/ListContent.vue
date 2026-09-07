<template>
  <div class="list-content">
    <el-scrollbar>
      <MessageList
          v-if="props.listType === 'message'&&refresh "
          :messages="messageData"
          @item-click="handleItemClick"
          @item-dblclick="handleItemDblClick"
      />
      <!-- 好友列表 -->
      <FriendList
          v-if="props.listType === 'friend'"
          :awaited="friendData.awaited"
          :needToBeProcessed="friendData.needToBeProcessed"
          :passed="friendData.passed"
          @item-click="handleItemClick"
          @item-dblclick="handleItemDblClick"
      />

      <!-- 群组列表 -->
      <GroupList
          v-else-if="props.listType === 'group'"
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
import { defineProps, ref } from 'vue';
import FriendList from './FriendList.vue';
import GroupList from './GroupList.vue';
import MessageList from './MessageList.vue';

let refresh = true;
const props = defineProps({
  listType: String, // 列表类型（friend/group/message）
  messageData: Array,
  friendData: Object,
  groupData: Object
});

watch(
  () => props.messageData,
  async (newValue) => {
    refresh = false;
    console.log(">>>ListContent>>>newVal",newValue,props.listType)
      refresh = true;
  },
  { deep: true }
);
const emit = defineEmits(['item-click', 'item-dblclick']);

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
  position: relative;
}


</style>
