<template>
  <view class="container">
    <!-- 搜索组件 -->
    <WordSearchComponent @search="handleSearch"/>

  </view>
</template>

<script>
	
	
import WordSearchComponent from '@/pages/functions/components/wordSearchComponent.vue'
// import BookWordSelectorComponent from '@/pages/functions/components/selector/BookWordSelectorComponent.vue'
// import WordDetailComponent from '@/pages/functions/components/WordDetailComponent.vue'


export default {
	components: {
    WordSearchComponent,
    // BookWordSelectorComponent,
    // WordDetailComponent
  },
  data() {
    return {
      selectedLanguage: 'en',
      spell: '',
      wordId: null,
      showSelector: false,
      wordMsg: null,
      loading: false
    }
  },
  methods: {
    async handleSearch(query) {
      if (this.loading) return
      
      try {
        this.loading = true
        uni.showLoading({ title: '搜索中...' })

        const res = await uni.request({
          url: '/api/wordMsg/getBySpellLan',
          method: 'POST',
          data: query
        })

        if (res.data.code === 200) {
          this.wordMsg = res.data.data
          this.wordId = this.wordMsg?.word?.wordId
          this.showSelector = true
          uni.pageScrollTo({ scrollTop: 0 })
        } else {
          uni.showToast({ title: '未找到相关单词', icon: 'none' })
        }
      } catch (error) {
        console.error('搜索失败:', error)
        uni.showToast({ title: '搜索失败', icon: 'none' })
      } finally {
        this.loading = false
        uni.hideLoading()
      }
    },

    handleUpdateSuccess() {
      uni.showToast({ title: '更新成功' })
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 30rpx;
  min-height: 100vh;
  background: #f5f7fa;
}

.detail-container {
  margin-top: 40rpx;
  box-shadow: 0 4rpx 24rpx rgba(0,0,0,0.06);
}
</style>
