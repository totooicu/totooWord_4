<template>
  <!-- 好友/群聊头像 -->
  <el-avatar :src="getImage(getAvatar())" class="avatar" />

  <!-- 好友/群聊信息 -->
  <div class="message-info">
    <div class="name">{{ getName(item) }}</div>
    <div class="last-message">{{ item.content }}</div>
  </div>

  <!-- 新消息标志 -->
  <div v-if="isNewMessage(item)" class="new-message">新消息</div>
</template>

<script setup>
import {getLastMessage} from "@/api/functions/chatMessage.js";
import {getUserById}from "@/api/functions/userApi.js"
import {getGroupById}from"@/api/functions/group.js"
import {getImage}from "@/api/functions/image.js"
import {} from "vue";
export default {
  name: "MessageItem"
}
const lastMessage=ref({})
const userGroupMsg=ref({})
const props = defineProps({messagesType:String,//"0":"user","1":"group"
                          id:Number//用户id或者群组id
})
const getData=async ()=>{
  lastMessage.value=(await getLastMessage({
    messagesType:props.messagesType,
    id:props.id
  })).data
if(this.props.messagesType=="0"){
  userGroupMsg.value=(await getUserById(props.id)).data
}else if(this.props.messagesType=="1"){
  userGroupMsg.value=(await getGroupById(props.id)).data
}
}

function getAvatar(){
  if(props.messagesType==="0")return userGroupMsg.value.avatar
  else return userGroupMsg.value.avatarUrl
}
function getName(){
  if(props.messagesType==="0")return userGroupMsg.value.userName
  else return userGroupMsg.value.groupName
}

</script>

<style scoped>

</style>