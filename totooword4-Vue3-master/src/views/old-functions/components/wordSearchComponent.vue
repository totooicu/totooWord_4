<template>
  <el-row :gutter="10" style="display: flex; align-items: center;">
    <el-col :span="2">
      <el-select v-model="selectedLanguage" placeholder="Select language">
        <el-option label="English" value="en"></el-option>
        <el-option label="Japanese" value="ja"></el-option>
        <el-option label="French" value="fr"></el-option>
        <el-option label="Korean" value="ko"></el-option>
      </el-select>
    </el-col>
    <el-col :span="12">
      <el-input v-model="spell" placeholder="Enter word"></el-input>
    </el-col>
    <el-col :span="6">
      <el-button @click="searchWord">Search</el-button>
    </el-col>
  </el-row>
</template>
<script>
export default {
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
  }
};
</script>

<style scoped>
/* Add your styles here */
</style>
