<template>
  <div class="chat-layout">
    <!-- 左部分 -->
    <div class="left-panel">
      <!-- 列表选择 -->
      <ListSelector @select-list="handleListSelect" />
      <!-- 列表内容 -->
      <ListContent :list-type="selectedListType" @item-click="handleItemClick" @item-dblclick="handleItemDblClick" />
    </div>
    <!-- 右部分 -->
    <div class="right-panel">
      <ChatDetail v-if="selectedItem && !chatWindowVisible" :item="selectedItem" />
    </div>
    <!-- 聊天窗口 -->
    <ChatWindow v-if="chatWindowVisible" :item="chatWindowItem" @close="chatWindowVisible = false" />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import ListSelector from './ListSelector.vue';
import ListContent from './ListContent/ListContent.vue';
import ChatDetail from './ChatDetail.vue';
import ChatWindow from './ChatWindow.vue';
import {getBySelfAndOtherUserId} from "@/api/functions/friend.js"
import {getBySelfGroupId} from "@/api/functions/groupMember.js"
import {listByChatMessageBySelfUserIdOrGroupId} from "@/api/functions/chatMessage.js";

const selectedListType = ref('message'); // 当前选择的列表类型（message/friend/group）
const selectedItem = ref(null); // 当前选中的列表项
const chatWindowVisible = ref(false); // 聊天窗口是否可见
const chatWindowItem = ref(null); // 聊天窗口对应的列表项

// 处理列表选择
const handleListSelect = (type) => {
  selectedListType.value = type;
  selectedItem.value = null; // 清空选中项
  chatWindowVisible.value = false; // 关闭聊天窗口
};

// 处理列表项单击
const handleItemClick = async(item) =>  {
  let relationShip;
  switch(selectedListType.value) {
    case 'message':relationShip="";break
    case 'friend':relationShip=await getBySelfAndOtherUserId(item.userId);break;
    case 'group':relationShip=await getBySelfGroupId(item.groupId);break;
  }
  selectedItem.value = {item,relationShip};
  chatWindowVisible.value = false; // 关闭聊天窗口
  console.log('>>>单击了列表项：', item)
};

// 处理列表项双击
const handleItemDblClick = async (item) => {
  let messageType=selectedListType.value==='friend'?'0':'1';
  if(selectedListType.value==='friend'){
    item=await listByChatMessageBySelfUserIdOrGroupId({messageType,receiverId:item.userId});
  }else  item=await listByChatMessageBySelfUserIdOrGroupId({messageType,groupId:item.groupId});
  console.log('>>>双击了列表项：', item)
  chatWindowItem.value = item;

  chatWindowVisible.value = true;
};
</script>

<style scoped>
.chat-layout {
  display: flex;
  height: 100vh;
}
.left-panel {
  width: 300px;
  border-right: 1px solid #ddd;
  display: flex;
  flex-direction: column;
}
.right-panel {
  flex: 1;
  padding: 16px;
}
</style>
