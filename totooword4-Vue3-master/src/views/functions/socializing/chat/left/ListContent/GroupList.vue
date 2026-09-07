<template>
  <div>
    <!-- 需要处理的群组验证 -->
    <div v-if="needToBeProcessed.length > 0">
      <h4>需要处理的群组验证</h4>
      <GroupItem
          v-for="group in needToBeProcessed"
          :key="group.groupId"
          :group="group"
          type="needToBeProcessed"
          @accept="handleAccept(group)"
          @reject="handleReject(group)"
          @click="handleItemClick(group)"
          @dblclick="handleItemDblClick(group)"
      />
    </div>

    <!-- 等待验证的群组 -->
    <div v-if="awaited.length > 0">
      <h4>等待验证的群组</h4>
      <GroupItem
          v-for="group in awaited"
          :key="group.groupId"
          :group="group"
          type="awaited"
          @click="handleItemClick(group)"
          @dblclick="handleItemDblClick(group)"
      />
    </div>

    <!-- 已通过的群组 -->
    <div v-if="passed.length > 0">
      <h4>已通过的群组</h4>
      <GroupItem
          v-for="group in passed"
          :key="group.groupId"
          :group="group"
          type="passed"
          @click="handleItemClick(group)"
          @dblclick="handleItemDblClick(group)"
      />
    </div>

    <!-- 被拒绝的群组 -->
    <div v-if="refused.length > 0">
      <h4>被拒绝的群组</h4>
      <GroupItem
          v-for="group in refused"
          :key="group.groupId"
          :group="group"
          type="refused"
          @click="handleItemClick(group)"
          @dblclick="handleItemDblClick(group)"
      />
    </div>
  </div>
</template>

<script setup>
import GroupItem from './GroupItem.vue';
import {handleRequest} from '@/api/functions/groupMember.js';

const props = defineProps({
  awaited: Array, // 等待验证的群组
  needToBeProcessed: Array, // 需要处理的群组验证
  passed: Array, // 已通过的群组
  refused: Array, // 被拒绝的群组
});

const emit = defineEmits(['item-click', 'item-dblclick']);

const handleAccept = (group) => {
  console.log('接受群组请求:', group);
  // 调用 API 处理接受逻辑
  handleRequest({ groupId:group.group.groupId,userId:group.userId,role:"0"})
  
};

const handleReject = (group) => {
  console.log('拒绝群组请求:', group);
  handleRequest({ groupId:group.group.groupId,userId:group.userId,role:"4"})
  // 调用 API 处理拒绝逻辑
};

const handleItemClick = (group) => {
  emit('item-click', group);
};

const handleItemDblClick = (group) => {
  emit('item-dblclick', group);
};
</script>
