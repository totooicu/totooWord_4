<template>
  <div class="chat-container">
    <!-- 标题区域 -->
    <div class="chat-title">
      <el-text type="primary" size="large" @dblclick="handleItemDblClick(obj)"
               @click="gotoButton" >{{ title }}</el-text>
    </div>

    <!-- 聊天窗口 -->
    <div class="chat-window">
      <el-scrollbar ref="scrollbarRef" class="scroll-container">
        <!-- 聊天内容区域 -->
        <div class="chat-content">
          <div
              v-for="message in messages"
              :key="message.messageId"
              :class="['message-item', isSelf(message.senderId) ? 'self' : 'other']"
          >
            <!-- 对方头像 -->
            <el-avatar
                v-if="!isSelf(message.senderId)"
                :src="getAvatar(message.senderId)"
                class="avatar"
            />
            <!-- 消息内容 -->
            <div class="message-content">
              <div class="message-text">{{ message.content }}</div>
              <div class="message-time">{{ formatTime(message.sendTime) }}</div>
            </div>
            <!-- 自己头像 -->
            <el-avatar
                v-if="isSelf(message.senderId)"
                :src="getAvatar(message.senderId)"
                class="avatar"
            />
          </div>
        </div>
      </el-scrollbar>

      <!-- 输入区域 -->
      <div class="input-area">
        <el-input
            v-model="inputMessage"
            placeholder="请输入消息"
            @keyup.enter="sendMessageBtn"
        />
        <el-button type="primary" @click="sendMessageBtn">发送</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { ElText } from 'element-plus';
import {sendMsg} from '@/api/functions/webSocket.js'
let obj
const props = defineProps({
  item: Object, // 传入的群或好友信息
});
const emit = defineEmits(['close','item-click', 'header-dblclick']);
const visible = ref(true); // 控制窗口显示
const inputMessage = ref(''); // 输入的消息内容
const scrollbarRef = ref(null);
let inButton= false;

//动态监听用户scrollbar位置，如果用户滑到最底部则inButton=true，若不则inButton=false
watch(scrollbarRef, (newValue, oldValue) => {
  if (newValue) {
    const scrollbar = newValue.$el;
    const scrollPane = scrollbar.querySelector('.el-scrollbar__wrap');
    if (scrollPane) {
      scrollPane.addEventListener('scroll', () => {
        if (scrollPane.scrollTop + scrollPane.clientHeight >= scrollPane.scrollHeight - 1) {
          inButton = true;
        } else {
          inButton = false;
        }
      })
    }
  }
})


// 获取标题（对方/群名称）
const title = computed(() => {
  gotoButton();
  console.log('item', props.item);
  if (props.item.data.groups.length > 0) {
    obj= props.item.data.groups[0];
    return props.item.data.groups[0].groupName;
  } else if (props.item.data.otherUsers.length > 0) {
    obj= props.item.data.otherUsers[0];
    return props.item.data.otherUsers[0].nickName;
  }
  return '未知';
});

// 获取对方昵称
const getOtherNickName = computed(() => {
  if (props.item.data.groups.length > 0) {
    return props.item.data.groups[0].groupName;
  } else if (props.item.data.otherUsers.length > 0) {
    return props.item.data.otherUsers[0].nickName;
  }
  return '未知';
});

// 获取消息列表
const messages = computed(() => {
  return  props.item.data.messages
});

// 判断消息是否是自己发送的
const isSelf = (senderId) => {
  return senderId === props.item.data.self[0].userId;
};

// 获取用户头像
const getAvatar = (userId) => {
  if (userId === props.item.data.self[0].userId) {
    return props.item.data.self[0].avatar;
  } else {
    const user = props.item.data.otherUsers.find((u) => u.userId === userId);
    return user ? user.avatar : '';
  }
};

// 格式化时间
const formatTime = (time) => {
  // console.log('time', time)
  return new Date(time).toLocaleString();
};
const handleItemDblClick = (obj) => {
  console.log('chatWindowD', obj)
  emit('header-dblclick', obj);
};
// 发送消息
const sendMessageBtn = async () => {
  if (inputMessage.value.trim()) {
    let msg;
    if (props.item.data.groups.length > 0) {
      // 群聊
      msg = {
        messageType: '1',
        groupId: props.item.data.groups[0].groupId,
        content: inputMessage.value,
      };
    } else if (props.item.data.otherUsers.length > 0) {
      // 单聊
      msg = {
        messageType: '0',
        receiverId: props.item.data.otherUsers[0].userId,
        content: inputMessage.value,
      };
    }
    let cache = inputMessage.value;
    inputMessage.value = '';
    sendMsg(msg);
    gotoButton();
  }
};

const handleItemClick = (friend) => {
  emit('item-click', friend);
};
// 滚动到底部
function gotoButton() {
  setTimeout(() => {
    if (scrollbarRef.value) {
      const scrollbar = scrollbarRef.value.$el;
      const scrollPane = scrollbar.querySelector('.el-scrollbar__wrap');
      if (scrollPane) {
        scrollPane.scrollTop = scrollPane.scrollHeight;
      }
    }
  }, 100); // 使用 setTimeout 确保 DOM 已加载
}

// 初始化时滚动到底部
gotoButton();

// 监听 item 变化以滚动到底部
watch(props.item, () => {
  gotoButton();
});

// 关闭窗口
const handleClose = () => {
  visible.value = false;
  emit('close');
};
</script>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 90%;
  width: 80%;
}

.chat-title {
  padding: 16px;
  border-bottom: 1px solid #eee;
  background-color: #f9f9f9;
  text-align: center;
}

.scroll-container {
  flex: 1;
  overflow: auto;
}

.chat-window {
  display: flex;
  flex-direction: column;
  height: 90%;
}

.chat-content {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
  border-bottom: 1px solid #eee;
}

.message-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 16px;
}

.message-item.self {
  justify-content: flex-end;
}

.message-item.other {
  justify-content: flex-start;
}

.avatar {
  width: 40px;
  height: 40px;
  margin: 0 8px;
}

.message-content {
  max-width: 60%;
  padding: 8px 12px;
  border-radius: 8px;
  background-color: #f0f0f0;
}

.message-item.self .message-content {
  background-color: #409eff;
  color: white;
}

.message-text {
  word-wrap: break-word;
}

.message-time {
  font-size: 12px;
  color: #999;
  margin-top: 4px;
}

.input-area {
  display: flex;
  align-items: center;
  padding: 16px;
  border-top: 1px solid #eee;
  background-color: #f9f9f9;
}

.el-input {
  flex: 1;
  margin-right: 8px;
}
</style>