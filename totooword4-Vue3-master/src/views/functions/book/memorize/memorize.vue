<template>
  <div class="memorize-container">
    <!-- 顶部打卡信息 -->
    <header class="streak-header">
      <div class="streak-item">
        <h3>累计打卡</h3>
        <div class="highlight-number">{{ streakDays }}天</div>
      </div>
      <div class="streak-item">
        <h3>连续打卡</h3>
        <div class="highlight-number">{{ continuousDays }}天</div>
      </div>
      <div class="streak-item clickable" @click="openWordList('totalLearnedWords')">
        <h3>已学单词</h3>
        <div class="highlight-number">{{ totalLearnedWords }}个</div>
      </div>
    </header>

    <!-- 主内容区 -->
    <main class="main-content">
      <!-- 词库选择区域 -->
      <section class="book-selector-section">
        <book-selector-component
            title="选择学习词库"
            v-model:selectedValue="selectedBookIds"
            :multiple-select="false"
            :select-able="isEditing"
            class="book-selector"
        />
      </section>

      <!-- 学习数据仪表盘 -->
      <section class="dashboard">
        <div class="dashboard-item clickable" @click="openWordList('totalWords')">
          <div class="metric-label">词库总量</div>
          <div class="metric-value">{{ totalWords }}</div>
        </div>
        <div class="dashboard-item">
          <div class="metric-label">每日目标</div>
          <el-input-number
              v-model="dailyGoal"
              :disabled="!isEditing"
              :min="1"
              :max="100"
              size="large"
              controls-position="right"
          />
        </div>
        <div class="dashboard-item clickable" @click="setRemainingDays">
          <div class="metric-label">预计完成</div>
          <div class="metric-value">{{ remainingDays }}天</div>
        </div>
      </section>

      <!-- 学习进度 -->
      <section class="progress-section">
        <div class="progress-item clickable" @click="openWordList('learnedWords')">
          <div class="progress-label">已完成</div>
          <div class="progress-value">{{ learnedWords }}个</div>
          <el-progress
              :percentage="(learnedWords / totalWords * 100 || 0)"
              :color="customColors"
              :show-text="false"
          />
        </div>
        <div class="progress-item clickable" @click="openWordList('remainingWords')">
          <div class="progress-label">待学习</div>
          <div class="progress-value">{{ remainingWords }}个</div>
          <el-progress
              :percentage="(remainingWords / totalWords * 100 || 0)"
              status="exception"
              :show-text="false"
          />
        </div>
      </section>

      <!-- 操作按钮 -->
      <section class="action-buttons">
        <el-button
            type="primary"
            size="large"
            @click="startMemorization('review')"
            :disabled="learnedWords === 0"
            round
        >
          <span class="button-content">
            <el-icon><Refresh /></el-icon>
            复习 ({{ learnedWords }})
          </span>
        </el-button>
        <el-button
            type="success"
            size="large"
            @click="startMemorization('learnNew')"
            :disabled="remainingWords === 0"
            round
        >
          <span class="button-content">
            <el-icon><Reading /></el-icon>
            学习新词 ({{ remainingWords }})
          </span>
        </el-button>
        <el-button
            :type="isEditing ? 'success' : 'primary'"
            @click="toggleEdit"
            circle
            class="edit-button"
        >
          <el-icon v-if="!isEditing"><Edit /></el-icon>
          <el-icon v-else><Check /></el-icon>
        </el-button>
      </section>
    </main>

    <!-- 单词列表弹窗 -->
    <el-dialog
        :title="wordListTitle"
        v-model="wordListVisible"
        width="90%"
        top="5vh"
        append-to-body
    >
      <book-word-list-component v-if="wordListVisible" :word-msg-list="wordMsgList"/>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, watch, computed } from 'vue';
import { useRouter } from 'vue-router';
import {
  ElButton,
  ElDialog,
  ElProgress,
  ElIcon,
  ElInputNumber
} from 'element-plus';
import {
  Refresh,
  Reading,
  Edit,
  Check
} from '@element-plus/icons-vue';
import BookSelectorComponent from "../../components/selector/BookSelectorComponent.vue";
import BookWordListComponent from "../../components/bookWordListComponent.vue";
import { getBookByOwnWordIdOrLan } from "@/api/functions/book.js";
import { getBySelf, edit } from "@/api/functions/memorizedUserConfig.js";
import { listWordMsgsByBookWordByBookIdAndListWordMsgsByMemorizedByWordIdsByBookWordByBookId, getMemorizedDayBySelf } from "@/api/functions/memorized.js";

const router = useRouter();

// 响应式数据
const streakDays = ref(0);
const continuousDays = ref(0);
const selectedBookIds = ref([]);
const totalWords = ref(0);
const dailyGoal = ref(20);
const learnedWords = ref(0);
const totalLearnedWords = ref(0);
const wordListVisible = ref(false);
const wordListTitle = ref("");
const wordMsgList = ref([]);
const isEditing = ref(false);


const selectedBookId = ref(null);
const allWordMsgs = ref();
const memorizedWordMsgs = ref();
const commonWordMsgs = ref();
const uncommonWordMsgs = ref();
const memorizedType = ref(); // 1->背新词 2->复习旧词

const editButtonText = ref('修改'); // 修改按钮文本


// 计算属性
const remainingWords = computed(() => totalWords.value - learnedWords.value);
const remainingDays = computed(() =>
    Math.ceil(remainingWords.value / dailyGoal.value) || 0
);
const customColors = ref([
  { color: '#6dd400', percentage: 20 },
  { color: '#a0d911', percentage: 40 },
  { color: '#ffd666', percentage: 60 },
  { color: '#ffa940', percentage: 80 },
  { color: '#ff7a45', percentage: 100 }
]);

// 方法保持不变...
// 监听器保持不变...
// 初始化逻辑保持不变...

const fetchInitMsg = async () => {
  try {
    const response = await getBySelf();
    dailyGoal.value = response.data.dailyLearningGoal;
    selectedBookIds.value = String(response.data.currentBookId);
  } catch (error) {
    console.error('获取用户配置失败:', error);
  }

  try {
    const response = await getMemorizedDayBySelf();
    streakDays.value = response.data.AllDays;
    continuousDays.value = response.data.continuousDays;
  } catch (error) {
    console.error('获取打卡数据失败:', error);
  }
};

const openWordList = (type) => {
  if (selectedBookIds.value.length > 0) {
    switch (type) {
      case 'totalWords':
        wordListTitle.value = "单词总数";
        wordMsgList.value = allWordMsgs.value;
        break;
      case 'totalLearnedWords':
        wordListTitle.value = "已学单词总数";
        wordMsgList.value = memorizedWordMsgs.value;
        break;
      case 'learnedWords':
        wordListTitle.value = "已学单词";
        wordMsgList.value = commonWordMsgs.value;
        break;
      case 'remainingWords':
        wordListTitle.value = "剩余单词";
        wordMsgList.value = uncommonWordMsgs.value;
        break;
      default:
        console.error("请选择一个词库");
    }
    selectedBookId.value = selectedBookIds.value;
    wordListVisible.value = true;
  } else {
    console.error("请选择一个词库");
  }
  console.log("打开单词列表", wordMsgList.value)
};

const setRemainingDays = () => {
  console.log("设置剩余天数");
};

const startMemorization = (type) => {
  memorizedType.value = type;
  if (selectedBookIds.value.length > 0) {
    router.push({
      path: '/functions/book/WordRecommendation',
      query: { pattern: type }
    });
  } else {
    console.error("请选择一个词库");
  }
};

const toggleEdit = () => {

  if (isEditing.value) {
    edit({ currentBookId: selectedBookIds.value, dailyLearningGoal: dailyGoal.value });
    editButtonText.value = '修改';
  } else {
    editButtonText.value = '确定修改？';
  }
  isEditing.value = !isEditing.value;
  console.log("选择的书本为",selectedBookIds.value)
};

watch(selectedBookIds, (newIds) => {
  console.log("newIds",newIds)
  if (newIds > 0) {

    selectedBookId.value = newIds;
    listWordMsgsByBookWordByBookIdAndListWordMsgsByMemorizedByWordIdsByBookWordByBookId({ bookId: selectedBookId.value }).then((response) => {
      totalWords.value = response.data.bookWordsCount;
      learnedWords.value = response.data.commonCount;
      totalLearnedWords.value = response.data.memorizedCount;
      remainingWords.value = totalWords.value - learnedWords.value;
      remainingDays.value = Math.ceil(remainingWords.value / dailyGoal.value);
      allWordMsgs.value = response.data.allWordMsgs;
      memorizedWordMsgs.value = response.data.memorizedWordMsgs;
      commonWordMsgs.value = response.data.commonWordMsgs;
      uncommonWordMsgs.value = response.data.uncommonWordMsgs;
    });
  }
});

watch(dailyGoal, (newGoal) => {
  remainingDays.value = Math.ceil(totalWords.value / newGoal);
});

fetchInitMsg();
</script>

<style scoped>
.memorize-container {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
  min-height: 100vh;
}

.streak-header {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.streak-item {
  padding: 1.5rem;
  background: var(--el-bg-color);
  border-radius: 12px;
  box-shadow: var(--el-box-shadow-light);
  text-align: center;
  transition: transform 0.2s;
}

.streak-item.clickable:hover {
  transform: translateY(-3px);
  cursor: pointer;
}

.highlight-number {
  font-size: 2.2rem;
  font-weight: 700;
  color: var(--el-color-primary);
  margin-top: 0.5rem;
}

.main-content {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.book-selector-section {
  margin: 0 auto;
  width: 100%;
  max-width: 600px;
}

.dashboard {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1.5rem;
}

.dashboard-item {
  padding: 1.5rem;
  background: var(--el-bg-color);
  border-radius: 12px;
  box-shadow: var(--el-box-shadow-light);
  text-align: center;
}

.metric-label {
  color: var(--el-text-color-secondary);
  margin-bottom: 0.5rem;
}

.metric-value {
  font-size: 1.8rem;
  font-weight: 600;
}

.progress-section {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1.5rem;
}

.progress-item {
  padding: 1.5rem;
  background: var(--el-bg-color);
  border-radius: 12px;
  box-shadow: var(--el-box-shadow-light);
}

.progress-label {
  margin-bottom: 0.5rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.progress-value {
  font-size: 1.4rem;
  margin-bottom: 1rem;
}

.action-buttons {
  display: flex;
  justify-content: center; /* 使按钮水平居中 */
  gap: 2rem;
  position: relative;
  margin-top: 2rem;
}

.edit-button {
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 48px;
  height: 48px;
}

.button-content {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.el-progress {
  margin-top: 1rem;
}

@media (max-width: 768px) {
  .streak-header,
  .dashboard,
  .progress-section {
    grid-template-columns: 1fr;
  }

  .action-buttons {
    flex-direction: column;
    align-items: stretch;
  }

  .edit-button {
    position: static;
    transform: none;
    margin-top: 1rem;
  }
}
</style>