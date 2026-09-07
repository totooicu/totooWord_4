<template>
  <div class="message-list">
    <el-scrollbar>
      <div v-for="item in props.messages" :key="item.messageId" class="message-item"
      @click="handleItemDblClick(item)">

        <!-- 好友/群聊头像 -->
        <el-avatar :src="getAvatar(item)" class="avatar" />

        <!-- 好友/群聊信息 -->
        <div class="message-info">
          <div class="name">{{ getName(item) }}</div>
          <div class="last-message">{{ item.content }}</div>
        </div>

        <!-- 新消息标志 -->
        <div v-if="isNewMessage(item)" class="new-message">新消息</div>
      </div>
    </el-scrollbar>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getMessageList } from '@/api/functions/messageList.js';
import useUserStore from '@/store/modules/user.js';
const userStore = useUserStore();
const props = defineProps({messages:Array})
const emit = defineEmits(['item-click', 'item-dblclick']);

watch(props.messages, (newVal) => {
  console.log(">>>MessageList>>>newVal",newVal)
})

// 加载消息列表
const loadMessageList = async () => {
  const res = await getMessageList();
  if (res.code === 200) {
    props.messages.value = res.data;
  }
};


const getUserOrGroup=(item)=>{
  if(item.messageType==="1")return item.group
  if(item.senderId!==userStore.$state.id)return item.sender
  else return item.receiver
}

// 获取头像
const getAvatar = (item) => {
  if (item.messageType==="1") {
    return item.group.avatarUrl || ''; // 群聊头像
  } else if (item.messageType==="0") {
    return item.sender.avatar || ''; // 好友头像
  }
  return '';
};

// 获取名称
const getName = (item) => {
  if (item.messageType==="1") {
    return item.group.groupName; // 群聊名称
  } else if (item.messageType==="0") {
    return item.sender.nickName; // 好友昵称
  }
  return '未知';
};

// 判断是否有新消息
const isNewMessage = (item) => {
  if (item.messageType==="1") {
    return item.messageId > item.groupMember.latestMessageId; // 群聊新消息
  } else if (item.messageType==="0") {
    console.log("item.messageId",item.messageId,"item.friend.latestMessageId",item.friend.latestMessageId)
    return item.messageId > item.friend.latestMessageId; // 好友新消息
  }
  return false;
};



const handleItemDblClick = (item) => {
  console.log(getUserOrGroup(item))
  emit('item-dblclick', getUserOrGroup(item));
};

// 初始化加载数据
onMounted(() => {
  // loadMessageList();
});
</script>

<style scoped>
.message-list {
  padding: 16px;
}

.message-item {
  display: flex;
  align-items: center;
  padding: 12px;
  border-bottom: 1px solid #eee;
  cursor: pointer;
}

.message-item:hover {
  background-color: #f9f9f9;
}

.avatar {
  width: 40px;
  height: 40px;
  margin-right: 12px;
}

.message-info {
  flex: 1;
}

.name {
  font-weight: bold;
}

.last-message {
  color: #666;
  font-size: 14px;
}

.new-message {
  color: #fff;
  background-color: #f56c6c;
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 12px;
}
</style>