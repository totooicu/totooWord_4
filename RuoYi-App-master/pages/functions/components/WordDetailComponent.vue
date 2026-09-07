<template>
  <view class="word-card">
    <view class="detail-section" v-if="word.language">
      <text class="section-label">语言</text>
      <text class="section-content">{{ word.language }}</text>
    </view>

    <detail-list-section 
      v-if="pronunciation.length"
      title="发音"
      :items="pronunciation.map(p => `${p.pronunciationType}: ${p.transcription}`)"
    />

    <detail-list-section 
      v-if="means.length"
      title="释义"
      :items="means.map(m => `${m.meaningType}: ${m.meaningDefinition}`)"
    />

    <detail-list-section 
      v-if="stage.length"
      title="学习阶段"
      :items="stage.map(s => s.stageText)"
    />

    <detail-list-section 
      v-if="tense.length"
      title="时态变化"
      :items="tense.map(t => `${t.inflectionType}: ${t.inflectionText}`)"
    />

    <detail-list-section 
      v-if="phrase.length"
      title="常用短语"
      :items="phrase.map(p => `${p.phraseText}: ${p.phraseDefinition}`)"
    />

    <detail-list-section 
      v-if="exampleSentence.length"
      title="例句"
      :items="exampleSentence.map(e => `${e.sentenceText}: ${e.sentenceDefinition}`)"
    />
  </view>
</template>

<script>
export default {
  props: {
    wordMsg: {
      type: Object,
      required: true
    }
  },
  computed: {
    word() { return this.wordMsg.word || {} },
    pronunciation() { return this.wordMsg.pronunciation || [] },
    means() { return this.wordMsg.means || [] },
    stage() { return this.wordMsg.stage || [] },
    tense() { return this.wordMsg.tense || [] },
    phrase() { return this.wordMsg.phrase || [] },
    exampleSentence() { return this.wordMsg.exampleSentence || [] }
  }
}
</script>

<style lang="scss" scoped>
.word-card {
  background: #fff;
  border-radius: 16rpx;
  padding: 32rpx;
  margin: 24rpx;
  box-shadow: 0 4rpx 24rpx rgba(0,0,0,0.06);
}

.detail-section {
  margin-bottom: 40rpx;
  .section-label {
    display: block;
    font-size: 28rpx;
    color: #666;
    margin-bottom: 16rpx;
  }
  .section-content {
    font-size: 32rpx;
    color: #333;
    line-height: 1.6;
  }
}
</style>

<!-- 公用子组件 DetailListSection.vue -->
<template>
  <view class="list-section">
    <text class="section-title">{{ title }}</text>
    <view class="list-container">
      <view 
        v-for="(item, index) in items" 
        :key="index"
        class="list-item"
      >
        <view class="bullet"></view>
        <text class="item-text">{{ item }}</text>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  props: {
    title: String,
    items: Array
  }
}
</script>

<style scoped>
.list-section {
  margin-bottom: 40rpx;
}

.section-title {
  font-size: 28rpx;
  color: #666;
  margin-bottom: 24rpx;
  display: block;
}

.list-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 20rpx;
}

.bullet {
  width: 12rpx;
  height: 12rpx;
  background: #007AFF;
  border-radius: 50%;
  margin: 16rpx 16rpx 0 0;
}

.item-text {
  flex: 1;
  font-size: 28rpx;
  color: #333;
  line-height: 1.6;
}
</style>
