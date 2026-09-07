<template>
  <view class="selector-container">
    <SelectComponent v-model:selectedValue="selectedLanguage"
                     :select-able="selectAble" :data="word_language"
                     :multipleSelect="false"  />
  </view>
</template>

<script>
	import {defineComponent, ref, watch} from 'vue';
	import SelectComponent from "./SelectorComponent.vue";

export default {
  name: '',
  components: { // 添加组件注册
    SelectComponent
  },
  props: {
    selectedLanguage: {
      type: [String, Array],
      required: false,
      default: 'en'
    },    selectAble: {
      type: Boolean,
      required: false,
      default: true
    }
  },
  setup(props, { emit }) { // 修改 setup 函数，接收 emit 参数
    const selectedLanguage = ref(props.selectedLanguage || "");
    const loading = ref(false);
    const { proxy } = getCurrentInstance();
    const { word_language } =  proxy.useDict('word_language');
    const remoteMethod = (query) => {
      if (query !== '') {
        loading.value = true;
        setTimeout(() => {
          loading.value = false;
        }, 200);
      } else {
      }
    };

    watch(() => props.selectedLanguage, (newselectedValue) => {
      selectedLanguage.value = newselectedValue || [];
      console.log('selectedLanguage changed:', newselectedValue)
    });

    watch(selectedLanguage, (newselectedValue) => {
      emit('update:selectedLanguage', newselectedValue); // 使用 emit 发射事件
    });

    return {
      selectedLanguage,
      loading,
      remoteMethod,
      word_language,
      SelectComponent
    };
  }
};
</script>

<style scoped>
.selector-container {
  padding: 20rpx 0;
}

/* 选择器统一样式 */
.selector-component {
  background-color: #fff;
  border-radius: 12rpx;
  padding: 20rpx;
  box-shadow: 0 2rpx 12rpx rgba(0,0,0,0.1);
}
</style>
