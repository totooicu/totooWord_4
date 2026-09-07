<template>
  <view class="selector-container">
    <text v-if="title" class="title">{{ title }}</text>
    <view class="picker-box" @click="togglePopup" :class="{ disabled: !selectAble }">
      <text class="placeholder">
        {{ displayText || '请选择' }}
      </text>
      <uni-icons type="arrowdown" size="14" color="#666"></uni-icons>
    </view>

    <!-- 弹出层 -->
    <uni-popup ref="popup" type="bottom">
      <view class="popup-content">
        <view class="popup-header">
          <text class="popup-title">{{ title || '请选择' }}</text>
          <text class="confirm-btn" @click="confirmSelection">完成</text>
        </view>
        
        <!-- 多选模式 -->
        <scroll-view v-if="multipleSelect" scroll-y class="option-list">
          <view 
            v-for="item in data" 
            :key="item.key"
            class="option-item"
            :class="{ selected: isSelected(item.value) }"
            @click="toggleSelect(item.value)"
          >
            <text>{{ item.label }}</text>
            <uni-icons 
              v-if="isSelected(item.value)" 
              type="checkmarkempty" 
              color="#007AFF" 
              size="16"
            ></uni-icons>
          </view>
        </scroll-view>

        <!-- 单选模式 -->
        <picker 
          v-else
          :range="data" 
          range-key="label"
          :value="singleIndex"
          @change="handleSingleChange"
        >
          <view class="picker-list">
            <view 
              v-for="(item, index) in data" 
              :key="item.key"
              class="picker-item"
              :class="{ active: index === singleIndex }"
            >
              {{ item.label }}
            </view>
          </view>
        </picker>
      </view>
    </uni-popup>
  </view>
</template>

<script>
export default {
  props: {
    title: String,
    data: {
      type: Array,
      default: () => []
    },
    selectedValue: {
      type: [Array, String, Number],
      default: () => []
    },
    multipleSelect: {
      type: Boolean,
      default: false
    },
    selectAble: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      tempSelection: [],
      singleIndex: -1
    }
  },
  computed: {
    displayText() {
      if (this.multipleSelect) {
        return this.value.map(v => 
          this.data.find(d => d.value === v)?.label
        ).join(', ')
      }
      return this.data.find(d => d.value === this.value)?.label
    }
  },
  watch: {
    value: {
      immediate: true,
      handler(newVal) {
        if (this.multipleSelect) {
          this.tempSelection = [...newVal]
        } else {
          this.singleIndex = this.data.findIndex(d => d.value === newVal)
        }
      }
    }
  },
  methods: {
    togglePopup() {
      if (!this.selectAble) return
      this.$refs.popup.open()
    },
    
    isSelected(value) {
      return this.tempSelection.includes(value)
    },
    
    toggleSelect(value) {
      const index = this.tempSelection.indexOf(value)
      if (index > -1) {
        this.tempSelection.splice(index, 1)
      } else {
        this.tempSelection.push(value)
      }
    },
    
    confirmSelection() {
      if (this.multipleSelect) {
        this.$emit('input', [...this.tempSelection])
        this.$emit('update:value', [...this.tempSelection])
      }
      this.$refs.popup.close()
    },
    
    handleSingleChange(e) {
      const index = e.detail.value
      const value = this.data[index]?.value
      if (value) {
        this.$emit('input', value)
        this.$emit('update:value', value)
      }
    }
  }
}
</script>

<style scoped>
.selector-container {
  margin: 20rpx 0;
}

.title {
  font-size: 28rpx;
  color: #666;
  margin-bottom: 10rpx;
  display: block;
}

.picker-box {
  height: 80rpx;
  border: 1rpx solid #e5e5e5;
  border-radius: 8rpx;
  padding: 0 20rpx;
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #fff;
}

.picker-box.disabled {
  background-color: #f5f5f5;
  opacity: 0.6;
}

.placeholder {
  color: #999;
  font-size: 28rpx;
}

.popup-content {
  background: #fff;
  border-radius: 24rpx 24rpx 0 0;
  padding: 40rpx;
  max-height: 70vh;
}

.popup-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 40rpx;
}

.popup-title {
  font-size: 32rpx;
  font-weight: bold;
}

.confirm-btn {
  color: #007AFF;
  font-size: 28rpx;
}

.option-list {
  padding: 20rpx 0;
}

.option-item {
  height: 100rpx;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 30rpx;
  border-bottom: 1rpx solid #eee;
}

.option-item.selected {
  background-color: #f5f8ff;
}

.picker-list {
  padding: 20rpx 0;
}

.picker-item {
  height: 100rpx;
  line-height: 100rpx;
  padding: 0 30rpx;
  font-size: 28rpx;
}

.picker-item.active {
  color: #007AFF;
  background-color: #f5f8ff;
}
</style>
