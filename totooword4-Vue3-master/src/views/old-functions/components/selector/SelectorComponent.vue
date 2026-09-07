<template>
  <div>
    <el-text v-if="title">{{ title }}</el-text>
    <el-select
        v-model="selectedValue"
        :multiple="multipleSelect"
        placeholder="请选择"
        :disabled="!selectAble"
        :remote-method="remoteMethod"
        :loading="loading"
        style="width: 100%; margin-top: 10px;"
    >
      <el-option
          v-for="item in data"
          :key="item.key"
          :label="item.label"
          :value="item.value"
      />
    </el-select>
  </div>
</template>
<script>
import { ref, watch } from 'vue';
import { defineComponent } from 'vue'; // 导入 defineComponent

export default defineComponent({
  name: 'SelectorComponent',
  props: {
    title: {
      type: String,
      required: false
    },
    data: {
      type: Array,
      required: true
    },
    selectedValue: {
      type: Array,
      required: true
    },
    multipleSelect:{
      type: Boolean,
      required: false,
      default: true
    },
    selectAble:{
      type: Boolean,
      required: false,
      default: true
    }
  },
  setup(props, { emit }) { // 修改 setup 函数，接收 emit 参数
    const selectedValue = ref(props.selectedValue || []);
    const loading = ref(false);

    const remoteMethod = (query) => {
      if (query !== '') {
        loading.value = true;
        setTimeout(() => {
          loading.value = false;
        }, 200);
      } else {
      }
    };

    watch(() => props.selectedValue, (newselectedValue) => {
      selectedValue.value = newselectedValue || [];
      console.log('selectedValue changed:', newselectedValue)
      console.log("selectAble", props.selectAble)
    });

    watch(selectedValue, (newselectedValue) => {
      emit('update:selectedValue', newselectedValue); // 使用 emit 发射事件
    });

    return {
      selectedValue,
      loading,
      remoteMethod
    };
  }
});
</script>
