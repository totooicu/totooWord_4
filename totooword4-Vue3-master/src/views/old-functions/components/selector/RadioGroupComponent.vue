<template>
  <div >
    <el-text v-if="title">{{ title }}</el-text>
    <el-radio-group
        v-model="selectedValue"
        :disabled="!selectAble"
    >
      <el-radio-button
          v-for="item in data"
          :key="item.key"
          :label="item.value"
      >
        {{ item.label }}
      </el-radio-button>
    </el-radio-group>
  </div>
</template>

<script>
import { ref, watch } from 'vue';
import { defineComponent } from 'vue'; // 导入 defineComponent

export default defineComponent({
  name: 'RadioGroupComponent',
  props: {
    title: {
      type: String,
      required: false
    },
    data: {//{key->key value->label}
      type: Array,
      required: true
    },
    selectedValue: {
      type: [String, Number, Boolean],
      required: true
    },
    selectAble: {
      type: Boolean,
      required: false,
      default: true
    }
  },
  setup(props, { emit }) { // 修改 setup 函数，接收 emit 参数
    const selectedValue = ref(props.selectedValue);

    // 监听 props.selectedValue 的变化
    watch(() => props.selectedValue, (newValue) => {
      selectedValue.value = newValue;
      console.log('selectedValue changed:', newValue);
      console.log("selectAble", props.selectAble);
    });

    // 监听 selectedValue 的变化并触发事件
    watch(selectedValue, (newValue) => {
      emit('update:selectedValue', newValue); // 使用 emit 发射事件
    });

    return {
      selectedValue
    };
  }
});
</script>

<style scoped>
/* 可以根据需要添加样式 */

</style>