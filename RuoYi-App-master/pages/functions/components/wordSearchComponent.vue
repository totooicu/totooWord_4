<template>
  <view>
	  <LanguageSelector :selectedLanguage="selectedLanguage"  />
	  
	  	<input class="uni-input" @input="getspell" placeholder="输入单词" />

	        <!-- <el-input v-model="spell" clearable placeholder="Enter word"></el-input> -->

	        <!-- <el-button type="primary"  @click="searchWord">Search</el-button> -->

  </view>
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
    },getspell(event){
		 this.spell = event.target.value
	}
  }
};
</script>

<style lang="scss" scoped>
.word-card {
  background: #ffffff;
  border-radius: 16rpx;
  padding: 32rpx;
  margin: 24rpx;
  box-shadow: 0 4rpx 24rpx rgba(0, 0, 0, 0.08);
}

.title {
  font-size: 36rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 40rpx;
  display: block;
}

.detail-item {
  margin-bottom: 32rpx;
  .label {
    font-size: 30rpx;
    color: #666;
  }
  .content {
    font-size: 30rpx;
    color: #333;
  }
}

.detail-section {
  margin-bottom: 48rpx;
  .section-title {
    font-size: 32rpx;
    color: #007AFF;
    font-weight: bold;
    margin-bottom: 24rpx;
    display: block;
  }
}

.list-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 24rpx;
  .dot {
    width: 12rpx;
    height: 12rpx;
    background: #007AFF;
    border-radius: 50%;
    margin: 16rpx 16rpx 0 0;
    flex-shrink: 0;
  }
  .text {
    font-size: 28rpx;
    color: #333;
    line-height: 1.6;
    flex: 1;
  }
}
</style>
