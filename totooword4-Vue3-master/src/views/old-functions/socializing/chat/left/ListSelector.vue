<template>
  <div class="list-selector">
    <RadioGroupComponent
      :title="null"
      :data="options"
      :selectedValue="selectedType"
      :selectAble="true"
      @update:selectedValue="handleChange"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import RadioGroupComponent from "@/views/functions/components/selector/RadioGroupComponent.vue";
const selectedType = ref('message');

const emit = defineEmits(['select-list']);
let options = [{
  label: '消息列表',
  value: 'message'
}, {
  label: '好友列表',
  value: 'friend'
}, {
  label: '群列表',
  value: 'group'
}];

// 处理列表类型变化
const handleChange = (type) => {
  selectedType.value = type;
  emit('select-list', type);
};
</script>

<style scoped>
.list-selector {
  padding: 16px;
  border-bottom: 1px solid #ddd;
}
</style>