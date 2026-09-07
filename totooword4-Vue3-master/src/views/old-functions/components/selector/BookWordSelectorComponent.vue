<template>
  <div>
    <el-row :gutter="10" style="display: flex; align-items: center;">
      <el-col :span="2">
        <el-text>选择将单词添加到哪本书？</el-text>
      </el-col>
      <el-col :span="20">
        <MultipleSelectComponent :title="title"
        :data="bookOptions"
        v-model:selectedValue="selectedBookIds"
        />
      </el-col>
      <el-col :span="2">
        <el-button @click="handleSubmit" style="margin-top: 10px;">提交</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue';
import { getBookByOwnWordIdOrLan } from "../../../../api/functions/book.js";
import { editByBookIdsWordIdSelf, searchBookIdsByWordIdSelf } from "../../../../api/functions/bookWord.js";
import MultipleSelectComponent from "./SelectorComponent.vue";

export default {
  name: 'BookWordSelectorComponent',
  props: {
    wordId: {
      type: Number,
      required: true
    },
  },
  components: {
    MultipleSelectComponent
  },
  setup(props) {
    const bookOptions = ref([]);
    const selectedBookIds = ref([]);
    const loading = ref(false);

    const fetchBooks = async () => {
      loading.value = true;
      try {
        // 获取所有书籍
        const bookResponse = await getBookByOwnWordIdOrLan({ wordId: props.wordId });
        console.log('>>>bookResponse:', bookResponse);
        bookOptions.value = bookResponse.data.map(book => ({
          key: book.bookId,
          label: book.title,
          value: book.bookId
        }));

        // 获取已选中的书籍 ID
        const selectedResponse = await searchBookIdsByWordIdSelf({ wordId: props.wordId });
        //获取id数组
        selectedBookIds.value=selectedResponse.data;

      } catch (error) {
        console.error('搜索书籍失败:', error);
      } finally {
        loading.value = false;
      }
    };
    fetchBooks();

    const handleSubmit = async () => {
      console.log('提交的书籍 ID:', selectedBookIds.value);
      await editByBookIdsWordIdSelf({ wordId: props.wordId, bookIds: selectedBookIds.value });      await fetchBooks();
      // 处理提交逻辑
    };

    // 监听 wordId 和 userId 的变化，重新获取数据
    watch([() => props.wordId], ([newWordId]) => {
      if (newWordId !== null) {
        fetchBooks();
      }
    });

    // 初始化时获取数据
    onMounted(() => {
      if (props.wordId !== null) {
        fetchBooks();
      }
    });

    return {
      bookOptions,
      selectedBookIds,
      loading,
      handleSubmit,
    };
  }
};
</script>

<style scoped>
</style>
