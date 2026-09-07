<template>
  <div class="chat-layout">
    <!-- 左部分 -->
    <div class="left-panel">
      <!-- 列表选择 -->
      <ListSelector @select-list="handleListSelect" />
      <!-- 列表内容 -->
      <ListContent
        :list-type="selectedListType"
        :message-data="messageData"
        :friend-data="friendData"
        :group-data="groupData"
        @item-click="handleItemClick" 
        @item-dblclick="handleItemDblClick" 
      />
      <!-- 添加好友/群的按钮 -->
      <div class="add-button-container">
        <el-button type="primary" @click="chatWindowVisible = 'AddFriendGroup'">添加好友/群</el-button>
      </div>
    </div>
    <!-- 右部分 -->
<!--    <div class="right-panel">-->
      <ChatDetail v-if="selectedItem && chatWindowVisible==='ChatDetail'" :item="selectedItem" />
<!--    </div>-->
    <!-- 聊天窗口 -->
    <ChatWindow v-if="chatWindowVisible==='ChatWindow'" :item="chatWindowItem"
                @header-dblclick="handleItemClick" @close="chatWindowVisible = ''" />
    <AddFriendGroup v-if="chatWindowVisible==='AddFriendGroup'" />
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import ListSelector from './left/ListSelector.vue';
import ListContent from './left/ListContent/ListContent.vue';
import ChatDetail from './right/ChatDetail.vue';
import ChatWindow from './right/ChatWindow.vue';
import { getBySelfAndOtherUserId } from "@/api/functions/friend.js"
import { getBySelfGroupId } from "@/api/functions/groupMember.js"
import { listByChatMessageBySelfUserIdOrGroupId,watched } from "@/api/functions/chatMessage.js";
import { receiveMsg } from '@/api/functions/webSocket.js'
import { getFriendList } from '@/api/functions/friend.js';
import { getGroupList } from '@/api/functions/group.js';
import { getMessageList } from '@/api/functions/messageList.js';
import AddFriendGroup from "@/views/functions/socializing/chat/right/AddFriendGroup.vue";

const selectedListType = ref('message'); // 当前选择的列表类型（message/friend/group）
const refreshListContent=ref(false)
const selectedItem = ref(null); // 当前选中的列表项
const chatWindowVisible = ref(''); // 聊天窗口类型
const chatWindowItem = ref(null); // 聊天窗口对应的列表项
const messages = ref([]); // 消息列表
const currentClickedItem = ref(null); // 当前客户端

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

const messageData = ref([]);



// 根据列表类型加载数据
watch(
  () => selectedListType.value,
  async (type) => {
    switch (type) {
      case 'message':messageData.value =( await getMessageList()).data;break;
      case 'friend':friendData.value = (await getFriendList()).data;break;
      case 'group':groupData.value =( await getGroupList()).data;break;
    }
  },
  { immediate: true }
);

// 处理列表选择
const handleListSelect = (type) => {
  selectedListType.value = type;
  selectedItem.value = null; // 清空选中项
  chatWindowVisible.value = ''; // 关闭聊天窗口
};

let count = 0; // 用于等待双击
// 处理列表项单击
const handleItemClick = async (item) => {
  currentClickedItem.value = item;
  count++;
  setTimeout(async () => {
    if (count !== 1) {
      count = 0;
      return;
    }
    count = 0;
    let relationShip;
    switch (selectedListType.value) {
      case 'message': relationShip = ""; break;
      case 'friend': relationShip = await getBySelfAndOtherUserId(item.userId); break;
      case 'group': relationShip = await getBySelfGroupId(item.groupId); break;
    }
    selectedItem.value = { item, relationShip };
    chatWindowVisible.value = 'ChatDetail'; //
    console.log('>>>单击了列表项：', item)
  }, 300)
};


// 处理列表项双击
const handleItemDblClick = async (item) => {
  currentClickedItem.value = item;
  count = 0;
  let messageType = item.groupId == null ? '0' : '1';
  let fitter;
  if (messageType === '0') fitter={ messageType, receiverId: item.userId };
   else fitter={ messageType, groupId: item.groupId };

  chatWindowItem.value =await listByChatMessageBySelfUserIdOrGroupId(fitter);

  fitter.messageId=(await watched(fitter)).data
  console.log('>>>双击了列表项：', chatWindowItem.value)
  fitter.senderId= chatWindowItem.value.data.self[0].userId;//自己id
  editMsgDataLastMessageId(fitter,false)

  // chatWindowItem.value = item;

  chatWindowVisible.value = 'ChatWindow';
};

receiveMsg((newMsg) => {
  console.log(">>>收到新消息：", newMsg)
  console.log("当前点击的item：", selectedListType.value)
  console.log(">>>当前点击的列表item：", currentClickedItem.value)

  if (selectedListType.value !== "message") return;
  if(currentClickedItem.value==null){
    editMsgDataLastMessageId(newMsg,true);return;
  }
  if ((newMsg.messageType === '0'&&newMsg.senderId === currentClickedItem.value.userId)
      || (newMsg.messageType === '1'&&newMsg.groupId === currentClickedItem.value.groupId)) {
    chatWindowItem.value.data.messages.push(newMsg);
    console.log(">>>chatWindowItem.value:",chatWindowItem.value)
    watched({
      "receiverId":newMsg.receiverId,
      "groupId":newMsg.groupId,
      "messageType":newMsg.messageType
    })
  } else {
    editMsgDataLastMessageId(newMsg,true)
  }
})




function editMsgDataLastMessageId(chatMsg,isNewMsg){
  console.log(">>>editMsgDataLastMessageId>>>",chatMsg)
  refreshListContent.value=false
  for(let i=0;i<messageData.value.length;i++){
    let msglist=messageData.value[i];
    let editflag=false
    console.log(">>>editMsgDataLastMessageId>>>",msglist)
  if(msglist.messageType==='0'
      &&(msglist.senderId===chatMsg.senderId&&msglist.receiverId===chatMsg.receiverId
          ||msglist.senderId===chatMsg.receiverId&&msglist.receiverId===chatMsg.senderId)){//是否是好友
    console.log(">>>editMsgDataLastMessageId>>>找到好友:",refreshListContent)
    editflag=true}
  if(msglist.messageType==='1'&&msglist.groupId===chatMsg.groupId){//是否是群组
    console.log(">>>editMsgDataLastMessageId>>>找到群:",refreshListContent)
    editflag=true}
  if(editflag){
    console.log(">>>editMsgDataLastMessageId>>>找到:",refreshListContent)
    if(isNewMsg){
    chatMsg.friend= msglist.friend; chatMsg.group= msglist.group; chatMsg.groupMember= msglist.groupMember;
    // chatMsg.sender= msglist.sender; chatMsg.receiver= msglist.receiver;
    messageData.value[i]=chatMsg;
    break;}else{
      messageData.value[i].messageId=chatMsg.messageId;
      if(chatMsg.messageType==='0')messageData.value[i].friend.latestMessageId=chatMsg.messageId;
      else messageData.value[i].groupMember.latestMessageId=chatMsg.messageId;
    }
  }
}
  setTimeout(() => {
    refreshListContent.value = true
  },200)
}
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
}
.add-button-container {
  width: 300px;
  border-right: 1px solid #ddd;
  display: flex;
  flex-direction: column;
  bottom: 80px;
  left: 10%;
  z-index: 1000;
}
</style>
