<template>
  <div>
    <word-search-component @search="handleSearch"/>
    <!-- ... existing code ... -->
    <book-word-selector-component
        v-if="showSelector"
        :wordId="wordId"
    />
    <WordDetailComponent :wordMsg="wordMsg"
                         v-if="showSelector"
    />

  </div>
</template>

<script>
import WordSearchComponent from './components/wordSearchComponent.vue';
import {getWordMsgBySpellLan} from "../../api/functions/wordMsg.js";
import BookWordSelectorComponent from './components/selector/BookWordSelectorComponent.vue';
import WordDetailComponent from './components/WordDetailComponent.vue';

export default {
  components: {
    WordSearchComponent,
    BookWordSelectorComponent,
    WordDetailComponent,

  },
  data() {
    return {
      selectedLanguage: 'en',
      spell: '',
      wordId: null,
      userId: 1, // 假设用户ID为1，可以根据实际情况获取
      showSelector: false,
      wordMsg:null,
    };
  },
  methods: {
    handleSearch(query) {
      // 处理搜索逻辑
      console.log('Search query:', query);
      // 调用API函数
      getWordMsgBySpellLan(query).then(response => {
        this.wordMsg=response.data;
        this.wordId=this.wordMsg.word.wordId;
        console.log('>>>wordId:', this.wordId);
        console.log('>>>wordMsg:', this.wordMsg);
        this.showSelector=true;
        console.log('Search result:', response);
        // 处理响应数据
      }).catch(error => {
        console.error('Search error:', error);
      });
    }
  }
};
</script>

<style scoped>
/* ... existing code ... */
</style>
