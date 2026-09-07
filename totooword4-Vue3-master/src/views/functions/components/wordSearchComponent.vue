<template>
  <el-row :gutter="10" style="display: flex; align-items: center;">
    <el-col :span="2">
      <LanguageSelector  v-model:selectedLanguage="selectedLanguage"  />
    </el-col>
    <el-col :span="20">
      <el-input v-model="spell" clearable placeholder="Enter word"></el-input>
    </el-col>
    <el-col :span="1">
      <el-button type="primary"  @click="searchWord">Search</el-button>
    </el-col>
  </el-row>
</template>
<script>
import LanguageSelector from './selector/LanguageSelector.vue';
export default {
  components: {
    LanguageSelector
  },
  data() {
    return {
      selectedLanguage: 'en',
      spell: ''
    };
  },
  methods: {
    searchWord() {
      this.$emit('search', { language: this.selectedLanguage, spell: this.spell });
    }
  },
  //watch selectedLanguage
  watch: {
    selectedLanguage(newValue) {
      console.log('wordSearchComponent selectedLanguage changed:', newValue);
    }
  }
};
</script>

<style scoped>
/* Add your styles here */
</style>
