<template>
  <div class="word-card-container">
    <!-- 单词卡片主体 -->
    <transition name="card-flip" mode="out-in">
      <div
          class="word-card"
          :class="{ 'show-meaning': showMeaning }"
          @click.self="toggleMeaning"
      >
        <!-- 正面：单词拼写 -->
        <div class="card-front">
          <div class="word-header">
            <el-tag type="info" effect="dark" class="word-type">
              {{ wordTypeLabel }}
            </el-tag>
            <div class="word-meta">
              <span class="phonetic">
                <span
                  v-for="(pronunciation, index) in wordMsg.pronunciation"
                  :key="index"
                  class="pronunciation-item"
              >
                {{ pronunciation.pronunciationType }}: {{ pronunciation.transcription }}
              </span></span>
<!--              <el-icon class="sound-icon" @click="playPronunciation">-->
<!--                <VideoPlay />-->
<!--              </el-icon>-->
            </div>
          </div>

          <h1 class="word-spell">
            {{ wordMsg.word.spell }}
          </h1>

          <div class="progress-indicator">
            学习进度：<el-progress
              :percentage="learningProgress"
              :stroke-width="12"
              :color="progressColor"
          />
          </div>
        </div>

        <!-- 背面：单词释义 -->
        <div class="card-back">
          <h1 class="word-spell">
            {{ wordMsg.word.spell }}
          </h1>
          <div class="word-meta">
              <span class="phonetic">
                <span
                    v-for="(pronunciation, index) in wordMsg.pronunciation"
                    :key="index"
                    class="pronunciation-item"
                >
                {{ pronunciation.pronunciationType }}: {{ pronunciation.transcription }}
              </span></span>
            <!--              <el-icon class="sound-icon" @click="playPronunciation">-->
            <!--                <VideoPlay />-->
            <!--              </el-icon>-->
          </div>
          <h3 class="word-meaning">
            {{ formattedMeanings }}
          </h3>

          <div class="sentence-examples" v-if="hasExamples">
            <div
                v-for="(example, index) in exampleSentences"
                :key="index"
                class="sentence-item"
            >
              <el-icon class="quote-icon"><ChatLineRound /></el-icon>
              <span class="sentence-text">{{ example.sentence }}</span>
              <span class="sentence-translation">- {{ example.translation }}</span>
            </div>
          </div>
        </div>
      </div>
    </transition>

    <!-- 操作按钮组 -->
    <div class="action-buttons">
      <el-button
          type="danger"
          size="large"
          :icon="CloseBold"
          @click="handleDontKnow"
          :disabled="showNextButton"
          round
      >
        不认识 (A)
        <span class="key-hint">←</span>
      </el-button>

      <el-button
          v-if="showNextButton"
          type="primary"
          size="large"
          :icon="ArrowRightBold"
          @click="handleNext"
          autofocus
          round
      >
        继续 (Space)
      </el-button>

      <el-button
          type="success"
          size="large"
          :icon="Select"
          @click="handleKnow"
          :disabled="showNextButton"
          round
      >
        认识 (D)
        <span class="key-hint">→</span>
      </el-button>
    </div>

    <!-- 单词详情弹窗 -->
<!--    <el-dialog-->
<!--        v-model="openWordMsg"-->
<!--        width="90%"-->
<!--        top="5vh"-->
<!--        append-to-body-->
<!--        destroy-on-close-->
<!--    >-->
<!--      <word-detail-component-->
<!--          :word-msg="wordMsg"-->
<!--          @close="openWordMsg = false"-->
<!--      />-->
<!--    </el-dialog>-->
  </div>


</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import {
  VideoPlay,
  CloseBold,
  ArrowRightBold,
  Select,
  ChatLineRound
} from '@element-plus/icons-vue';

const props = defineProps({
  wordMsg: {
    type: Object,
    required: true,
    default: () => ({
      word: {  },
      means: [],
      examples: []
    })
  },
  learningProgress: {
    type: Number,
    default: 0
  },onNext:{
    type:Function
  }
});

const emit = defineEmits(['next', 'update-progress']);

// 响应式状态
const showMeaning = ref(false);
const showNextButton = ref(false);
const openWordMsg = ref(false);
const result = ref(null);

// 计算属性
const formattedMeanings = computed(() => {
  return props.wordMsg.means
      .map((m, index) => `${index + 1}. ${m.meaningDefinition}`)
      .join('\n');
});

const hasExamples = computed(() =>
    props.wordMsg.examples?.length > 0
);

const exampleSentences = computed(() =>
    props.wordMsg.examples?.slice(0, 2) || []
);

const wordTypeLabel = computed(() => {
  const types = {
    noun: '名词',
    verb: '动词',
    adj: '形容词',
    adv: '副词'
  };
  return types[props.wordMsg.word.type] || '通用';
});

const progressColor = computed(() =>
    props.learningProgress < 30 ? '#f56c6c' :
        props.learningProgress < 70 ? '#e6a23c' :
            '#67c23a'
);

// 交互方法
const toggleMeaning = () => {
  if (!showNextButton.value) {
    showMeaning.value = !showMeaning.value;
  }
};

const handleAction = (isKnown) => {
  // if (showNextButton.value) return;
  console.log(">>>isknow",isKnown)
  result.value = isKnown;
  showMeaning.value = true;
  showNextButton.value = true;

  if (!isKnown) {
    openWordMsg.value = true;
  }

  emit('update-progress', isKnown);
};

const handleDontKnow = () => handleAction(false);
const handleKnow = () => handleAction(true);

const handleNext = () => {
  console.log(">>>handleNext",result.value)
  showMeaning.value = false;
  showNextButton.value = false;
  // emit('next', result.value);
  props.onNext(result);
  // result.value = null;
};

const playPronunciation = () => {
  console.log(">>>点击发音")
  let url;
  let spell=props.wordMsg.word.spell
  switch (props.wordMsg.word.language) {
    case 'en':
      url = `https://dict.youdao.com/dictvoice?audio=${spell}&type=2`;
      break;
    case 'fr':
      url = `https://dict.youdao.com/dictvoice?audio=${spell}&type=2`;
      break;
    case 'ja':
      url = `https://dict.youdao.com/dictvoice?audio=${spell}&type=2`;
      break;
    case 'ko':
      url = `https://dict.youdao.com/dictvoice?audio=${spell}&type=2`;
      break;
  }
  const audio = new Audio( url);
  audio.play().catch(() => {
    ElMessage.warning('音频播放失败');
  });
};

// 键盘事件处理
const handleKeyDown = (e) => {
  if (e.repeat) return;

  switch(e.key) {
    case 'a':
    case 'ArrowLeft':
      handleDontKnow();
      break;
    case 'd':
    case 'ArrowRight':
      handleKnow();
      break;
    case ' ':
      if (showNextButton.value) {
        handleNext();
      } else {
        toggleMeaning();
      }
      break;
    case 'Enter':
      if (showNextButton.value) handleNext();
      break;
  }
};

onMounted(() => {
  window.addEventListener('keydown', handleKeyDown);
});
</script>

<style scoped>
.word-card-container {
  max-width: 800px;
  margin: 2rem auto;
  padding: 1rem;
}

.word-card {
  position: relative;
  min-height: 400px;
  background: var(--el-bg-color);
  border-radius: 16px;
  box-shadow: 0 8px 24px rgba(0,0,0,0.1);
  cursor: pointer;
  transform-style: preserve-3d;
  transition: all 0.6s cubic-bezier(0.4, 0, 0.2, 1);
  perspective: 1000px;
}

.word-card.show-meaning {
  transform: rotateY(180deg);
}

.card-front,
.card-back {
  position: absolute;
  width: 100%;
  height: 100%;
  backface-visibility: hidden;
  padding: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.card-back {
  transform: rotateY(180deg);
}

.word-header {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.word-spell {
  font-size: 3rem;
  margin: 1rem 0;
  color: var(--el-color-primary);
  text-align: center;
}

.phonetic {
  font-size: 1.2rem;
  color: var(--el-text-color-secondary);
}

.sound-icon {
  margin-left: 0.8rem;
  font-size: 1.5rem;
  cursor: pointer;
  transition: color 0.3s;
}

.sound-icon:hover {
  color: var(--el-color-primary);
}

.progress-indicator {
  width: 80%;
  margin-top: 2rem;
}

.word-meaning {
  white-space: pre-wrap;
  text-align: center;
  line-height: 1.8;
  font-size: 1.2rem;
}

.sentence-examples {
  margin-top: 2rem;
  width: 100%;
}

.sentence-item {
  padding: 1rem;
  margin: 1rem 0;
  background: var(--el-fill-color-light);
  border-radius: 8px;
  position: relative;
}

.quote-icon {
  position: absolute;
  left: 0.5rem;
  top: 50%;
  transform: translateY(-50%);
  color: var(--el-color-primary-light-5);
}

.sentence-text {
  display: block;
  margin-left: 2rem;
  font-style: italic;
}

.sentence-translation {
  display: block;
  margin-left: 2rem;
  color: var(--el-text-color-secondary);
}

.action-buttons {
  margin-top: 2rem;
  display: flex;
  justify-content: center;
  gap: 1.5rem;
}

.key-hint {
  margin-left: 0.5rem;
  font-size: 0.8em;
  opacity: 0.6;
}

.pronunciation-item {
  margin-right: 0.5rem;
}
/* 卡片翻转动画 */
.card-flip-enter-active,
.card-flip-leave-active {
  transition: all 0.6s;
}
.card-flip-enter-from {
  opacity: 0;
  transform: rotateY(-180deg);
}
.card-flip-leave-to {
  opacity: 0;
  transform: rotateY(180deg);
}

@media (max-width: 768px) {
  .word-card {
    min-height: 300px;
  }

  .word-spell {
    font-size: 2rem;
  }

  .action-buttons {
    flex-direction: column;
    align-items: center;
  }
}
</style>