<template>

  <!-- 添加好友/群的悬浮窗 -->
  <el-form :model="addForm"   >
    <el-form-item label="类型">
      <RadioGroupComponent :title="null" :data="addedOptions" v-model:selectedValue="addForm.type" :selectAble="true"  />
    </el-form-item>
    <el-form-item label="ID" :v-if="addForm.type==='group'||addForm.type==='friend'" >
      <el-input v-model="addForm.id" placeholder="请输入ID"></el-input>
    </el-form-item>
    <div v-if="addForm.type==='creatGroup'">
<!--      groupName introduction avatar_url-->
      <el-form-item label="群组名称">
        <el-input v-model="addForm.groupName" placeholder="请输入群组名称"></el-input>
      </el-form-item>
      <el-form-item label="简介">
        <el-input v-model="addForm.introduction" placeholder="请输入简介"></el-input>
      </el-form-item>

    </div>
  </el-form>

    <el-button type="primary" @click="handleAdd">确定</el-button>

</template>

<script setup>
import {ref} from "vue";
import RadioGroupComponent from "@/views/functions/components/selector/RadioGroupComponent.vue";
import {addFriend} from "@/api/functions/friend.js"
import {addMember} from "@/api/functions/groupMember.js"

const addedOptions = ref([{value: 'friend', label: '好友'},{value: 'group', label: '群组'},{value:'creatGroup', label: '创建群组'}]); // 添加好友/群组操作记录
const addedOptionsSelected = ref("friend"); // 添加好友/群组操作记录映射
const addForm = ref({
  type: addedOptionsSelected.value,
  id: ''
});



const handleAdd = () => {
  switch (addForm.value.type){
    case 'creatGroup':addMember(addForm.value.id);break;
    case 'friend':addFriend(addForm.value.id);break;
    case 'group':addMember(addForm.value.id)
  }

  // 这里处理添加好友/群的逻辑
  console.log('添加类型:', addForm.value.type, 'ID:', addForm.value.id);
};
</script>

<style scoped>

</style>