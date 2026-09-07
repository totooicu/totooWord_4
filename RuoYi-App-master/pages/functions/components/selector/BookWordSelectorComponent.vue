<template>
  <view class="container">
    <view class="row">
      <text class="tip-text">选择将单词添加到哪本书？</text>
      <view class="selector-box">
        <multiple-select-component 
          :title="title"
          :data="bookOptions"
          :value="selectedBookIds"
          @input="handleSelectChange"
        />
      </view>
      <button 
        class="submit-btn" 
        @tap="handleSubmit"
        :disabled="loading"
      >提交</button>
    </view>
  </view>
</template>

<script>
export default {
  props: {
    wordId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      bookOptions: [],
      selectedBookIds: [],
      loading: false
    }
  },
  mounted() {
    if (this.wordId) {
      this.fetchBooks()
    }
  },
  watch: {
    wordId(newVal) {
      if (newVal) {
        this.fetchBooks()
      }
    }
  },
  methods: {
    async fetchBooks() {
      this.loading = true
      try {
        // 获取书籍列表
        const [bookRes, selectedRes] = await Promise.all([
          this.$request({
            url: '/api/book/getByOwnWordIdOrLan',
            method: 'POST',
            data: { wordId: this.wordId }
          }),
          this.$request({
            url: '/api/bookWord/searchBookIdsByWordId',
            method: 'POST',
            data: { wordId: this.wordId }
          })
        ])

        this.bookOptions = bookRes.data.map(book => ({
          key: book.bookId,
          label: book.title,
          value: book.bookId
        }))

        this.selectedBookIds = selectedRes.data
      } catch (error) {
        console.error('请求失败:', error)
        uni.showToast({
          title: '加载失败',
          icon: 'none'
        })
      } finally {
        this.loading = false
      }
    },

    handleSelectChange(ids) {
      this.selectedBookIds = ids
    },

    async handleSubmit() {
      if (this.loading) return
      
      try {
        this.loading = true
        await this.$request({
          url: '/api/bookWord/editByBookIdsWordId',
          method: 'POST',
          data: {
            wordId: this.wordId,
            bookIds: this.selectedBookIds
          }
        })
        
        uni.showToast({
          title: '保存成功',
          icon: 'success'
        })
        this.$emit('update-success')
      } catch (error) {
        console.error('提交失败:', error)
        uni.showToast({
          title: '保存失败',
          icon: 'none'
        })
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.container {
  padding: 20rpx;
}

.row {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 20rpx;
}

.tip-text {
  font-size: 28rpx;
  color: #666;
  flex-shrink: 0;
}

.selector-box {
  flex: 1;
  min-width: 400rpx;
}

.submit-btn {
  background-color: #007AFF;
  color: white;
  padding: 0 30rpx;
  height: 60rpx;
  line-height: 60rpx;
  border-radius: 8rpx;
  font-size: 28rpx;
}

.submit-btn[disabled] {
  background-color: #cccccc;
}
</style>
