<!-- src/components/LanguageSelector.vue -->
<template>
  <div>
    <SelectComponent v-model:selectedValue="selectedLanguage"
                     :select-able="selectAble" :data="word_language"
                     :multipleSelect="false"  title="请选择语言"/>
  </div>
</template>

<script >
import {defineComponent, defineProps, ref, watch} from 'vue';
import SelectComponent from "./SelectorComponent.vue";

export default defineComponent({
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
});
</script>
