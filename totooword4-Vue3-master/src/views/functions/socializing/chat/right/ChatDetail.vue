<template>
  <div class="chat-detail">
    <!-- 用户信息 -->
    <div v-if="isUser" class="user-info">
      <el-avatar :src="item.item.avatar" class="avatar" />
      <div class="info">
        <h3>{{ item.item.nickName }}</h3>
        <p>用户名: {{ item.item.userName }}</p>
        <p>性别: {{ genderMap[item.item.sex] }}</p>
        <p>邮箱: {{ item.item.email }}</p>
        <p>手机号: {{ item.item.phonenumber }}</p>
        <p>状态: {{ statusMap[item.item.status] }}</p>
        <p>好友关系状态: {{ friendStatusMap[item.relationShip.data.status] }}</p>
      </div>
    </div>

    <!-- 群聊信息 -->
    <div v-else class="group-info">
      <el-avatar :src="item.item.avatarUrl" class="avatar" />
      <div class="info">
        <h3>{{ item.item.groupName }}</h3>
<!--        <p>群主: {{ item.item.createUserId }}</p>-->
        <p>简介: {{ item.item.introduction || '暂无简介' }}</p>
        <p>我的角色: {{ roleMap[item.relationShip.data.role] }}</p>
      </div>
    </div>
    <el-button  v-if="!isUser &&( item.relationShip.data.role === '2'||item.relationShip.data.role === '1')"
    @click="showMember=true">查看群成员</el-button>

<el-dialog v-if="showMember"  v-model="showMember" width="70%">
<GroupMemberList v-if="!isUser &&( item.relationShip.data.role === '2'||item.relationShip.data.role === '1')"
  :groupMembers="groupMembers" :self="item"/></el-dialog>
  </div>
  <!-- 根据isUser分类别展示删除用户/删除群组 -->
  <!-- 根据item.relationShip.data.status/item.relationShip.data.role决定是否显示按钮 -->
  <el-button v-if="isUser && item.relationShip.data.status === '1'" @click="delateRelationShip(item,1)">删除好友</el-button>
  <el-button v-else-if="!isUser &&( item.relationShip.data.role == '0'|| item.relationShip.data.role == '1')" @click="delateRelationShip(item,2)">退出群组</el-button>
  <el-button v-else-if="!isUser &&( item.relationShip.data.role == '2')" @click="delateRelationShip(item,3)">解散群组</el-button>

</template>

<script setup>
import { computed } from 'vue';
import {deleteFriend}from '@/api/functions/friend.js'
import {deleteMember,listByGroupId}from '@/api/functions/groupMember.js'
import GroupMemberList from './GroupMemberList.vue'
const props = defineProps({
  item: Object, // 传入的用户或群聊信息
});
var groupMembers
const showMember=ref(false)

// 判断是否为用户信息
const isUser = computed(() => !!props.item.item.userId);
console.log(">>>isUser",isUser.value)
const getGroupMember = async () => {
  groupMembers = await listByGroupId(props.item.item.groupId)
  groupMembers=groupMembers.data
  return groupMembers
}
// if(!(isUser.value)) getGroupMember()


// 性别映射
const genderMap = {
  '0': '男',
  '1': '女',
  '2': '未知',
};
function delateRelationShip(item,type){
	switch(type){
		case 1:
			// 删除好友
			deleteFriend(item.relationShip.data.friendId)
			break;
		case 2:case 3:
			// 退出群组
			deleteMember(item.relationShip.data.memberId)
			break;
	}

}
// 用户状态映射
const statusMap = {
  '0': '正常',
  '1': '停用',
};

// 好友关系状态映射
const friendStatusMap = {
  '0': '等待对方同意',
  '1': '正常',
};

// 群成员角色映射
const roleMap = {
  '0': '普通成员',
  '1': '管理员',
  '2': '群主',
};
onMounted(async () => {
  console.log(">>>item",props.item)
  //如果是群聊，且角色为管理员，获取群成员
  if(!(isUser.value)) await getGroupMember()

})
</script>

<style scoped>
.chat-detail {
  padding: 16px;
}

.user-info,
.group-info {
  display: flex;
  align-items: center;
}

.avatar {
  width: 80px;
  height: 80px;
  margin-right: 16px;
}

.info h3 {
  margin: 0 0 8px;
  font-size: 18px;
}

.info p {
  margin: 4px 0;
  color: #666;
}
</style>