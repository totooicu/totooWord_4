<template>
  <SelectorComponent :title="title" :data="bookOptions" v-model:selectedValue="selectedValue"
                     :multiple-select="multipleSelect" :selectAble="selectAble"/>
</template>

<script>
import SelectorComponent from "./SelectorComponent.vue";
import { listByBookSelf } from "@/api/functions/book.js";
import {ref, watch} from "vue";

export default {
  name: 'BookSelectorComponent',
  components: {
    SelectorComponent
  },
  props: {
    filter: {
      required: false,
      default:{}
    },
    title: {
      type: String,
      required: false
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
  setup(props, { emit })  {
    const selectedValue = ref(props.selectedValue || []);

    watch(() => props.selectedValue, (newselectedValue) => {
      selectedValue.value = newselectedValue || [];
      console.log('selectedValue changed:', newselectedValue)
      console.log("selectAble", props.selectAble)
    });

    watch(selectedValue, (newselectedValue) => {
      emit('update:selectedValue', newselectedValue); // 使用 emit 发射事件
    });

    return{
      selectedValue
    }
  },

  data() {
    return {
      bookOptions: []
    };
  },
  computed: {
    filteredData() {
      return this.bookOptions.filter(this.filter);
    }
  },
  methods: {
    async fetchBooks() {
      try {
        const response = await listByBookSelf( this.filter );
        this.bookOptions = response.data.map(book => ({
          key: book.bookId,
          label: book.title,
          value: book.bookId
        }));
      } catch (error) {
        console.error('获取书籍失败:', error);
      }
    }
  },
  watch: {
    filter: {
      immediate: true,
      handler(newFilter) {
        this.fetchBooks();
      }
    }
  },

}
</script>
