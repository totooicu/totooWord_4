<template>
  <div class="memorize-container">
    <!-- 第一行：打卡天数和连续打卡次数 -->
    <div class="streak">
      <h2>打卡天数: {{ streakDays }}</h2>
      <h2>连续打卡次数: {{ continuousDays }}</h2>
      <h2 @click="openWordList('totalLearnedWords')">共学{{ totalLearnedWords }}个单词</h2>
    </div>

    <!-- 第二行：词库选择、单词总数、每日学习量、剩余天数、修改按钮 -->
    <div class="second-row">
      <div class="book-selector">
        <book-selector-component
            title="请选择词库"
            v-model:selectedValue="selectedBookIds"
            :multiple-select="false"
            :select-able="isEditing"
        />
      </div>
      <div class="info-item" @click="openWordList('totalWords')">
        <span>单词总数:</span>
        <span>{{ totalWords }}</span>
      </div>
      <div class="info-item">
        <span>学习计划:</span>
        <el-input-number
            v-model="dailyGoal"
            :disabled="!isEditing"
            :min="1"
        />
        <span>个单词</span>
      </div>
      <div class="info-item" @click="setRemainingDays">
        <span>剩余天数:</span>
        <span>{{ remainingDays }}</span>
      </div>
      <el-button type="primary" @click="toggleEdit" round>
        {{ editButtonText }}
      </el-button>
    </div>

    <!-- 第三行：已背单词数、背诵按钮、复习按钮、还剩单词数 -->
    <div class="third-row">
      <div class="info-item" @click="openWordList('learnedWords')">
        <span>已背单词数:</span>
        <span>{{ learnedWords }}</span>
      </div>
      <div class="action-buttons">
        <el-button type="primary" @click="startMemorization('learnNew')" round>背诵</el-button>
        <el-button type="primary" @click="startMemorization('review')" round>复习</el-button>
      </div>
      <div class="info-item" @click="openWordList('remainingWords')">
        <span>还剩单词数:</span>
        <span>{{ remainingWords }}</span>
      </div>
    </div>

    <!-- 单词列表弹窗 -->
    <el-dialog
        :title="wordListTitle"
        v-model="wordListVisible"
        v-if="wordListVisible"
        width="80%"
        append-to-body
    >
      <book-word-list-component :word-msg-list="wordMsgList" />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import BookSelectorComponent from "../../components/selector/BookSelectorComponent.vue";
import BookWordListComponent from "../../components/bookWordListComponent.vue";
import { getBookByOwnWordIdOrLan } from "../../../../api/functions/book.js";
import { getBySelf, edit } from "../../../../api/functions/memorizedUserConfig.js";
import { listWordMsgsByBookWordByBookIdAndListWordMsgsByMemorizedByWordIdsByBookWordByBookId, getMemorizedDayBySelf } from "../../../../api/functions/memorized.js";
import { useRouter } from "vue-router";

const router = useRouter();

const streakDays = ref(0);
const continuousDays = ref(0);
const selectedBookIds = ref([]);
const totalWords = ref(0);
const dailyGoal = ref(0);
const remainingDays = ref(0);
const learnedWords = ref(0);
const totalLearnedWords = ref(0);
const remainingWords = ref(0);
const wordListVisible = ref(false);
const wordListTitle = ref("");
const wordMsgList = ref([]);
const selectedBookId = ref(null);
const allWordMsgs = ref();
const memorizedWordMsgs = ref();
const commonWordMsgs = ref();
const uncommonWordMsgs = ref();
const memorizedType = ref(); // 1->背新词 2->复习旧词

const isEditing = ref(false); // 编辑状态
const editButtonText = ref('修改'); // 修改按钮文本

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
    selectedBookId.value = selectedBookIds.value[0];
    wordListVisible.value = true;
  } else {
    console.error("请选择一个词库");
  }
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
    edit({ currentBookId: selectedBookIds.value[0], dailyLearningGoal: dailyGoal.value });
    editButtonText.value = '修改';
  } else {
    editButtonText.value = '确定修改？';
  }
  isEditing.value = !isEditing.value;
};

watch(selectedBookIds, (newIds) => {
  if (newIds.length > 0) {
    selectedBookId.value = newIds[0];
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
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.streak {
  text-align: center;
  margin-bottom: 20px;
}

.streak h2 {
  margin: 10px 0;
  font-size: 1.5rem;
  color: #333;
}

.second-row, .third-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  gap: 10px;
}

.book-selector {
  flex: 1;
}

.info-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  cursor: pointer;
  background-color: #f9f9f9;
}

.info-item span {
  font-size: 1rem;
  color: #666;
}

.action-buttons {
  display: flex;
  gap: 10px;
}

.el-button {
  margin-left: 10px;
}
</style>