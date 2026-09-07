<template>
  <div>
<!--单词搜索组件包含语言选择框、单词输入框、搜索按钮-->
<!--点击搜索按钮可获取到wordId和wordMsg（单词数据），且将showSelector设为true-->
    <word-search-component @search="handleSearch"/>
<!--书本单词选择框-->
    <book-word-selector-component v-if="showSelector" :wordId="wordId"/>
<!--单词详情组件-->
    <WordDetailComponent :wordMsg="wordMsg" v-if="showSelector"/>
  </div>
</template>

<script>
import WordSearchComponent from './components/wordSearchComponent.vue';
import {getWordMsgBySpellLan} from "@/api/functions/wordMsg.js";
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
