<template>
  <div>
    <transition name="fade" mode="out-in">
    <WordReviewPage
        v-if="currentWord"
        :wordMsg="currentWord"
        :pattern="pattern2"
        :onNext="handleNextWord"
        :learningProgress="learningProgress"
    />
    <div v-else class="start-section">
    <el-button  @click="fetchWords" type="primary">
      开始{{ pattern1 === "review" ? "复习" : "背诵" }}
    </el-button>
    </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import WordReviewPage from "./WordReviewPage.vue";
import { useRoute } from 'vue-router';
import {
  getTodayNewWordBySelf,
  getTodayReviewWordBySelf,
  updataStudayDataBySelfWordId
} from "@/api/functions/memorized.js";
const route = useRoute();

const pattern1=route.query.pattern;
const pattern2=ref("spell")
const wordList = ref([]);
const currentWord = ref(null);
const currentIndex = ref(0);
let learningProgress=0
let AllWordsCount;
// 获取单词数据
const fetchWords = async () => {
  console.log("获取单词数据 pattern", pattern1);
    if(pattern1 === "review"){
      //检查本地是否有单词，并检查时间是否过去一天
      if (localStorage.getItem("reviewWords")) {//有
        wordList.value = JSON.parse(localStorage.getItem("reviewWords"));
        if(wordList.value.MemorizedWordMsgList !=null||wordList.value.timestamp < Date.now() - 24 * 60 * 60 * 1000){//过期
         wordList.value=(await getTodayReviewWordBySelf()).data;localStorage.setItem("reviewWords", JSON.stringify(wordList.value));
      }
      }else{//没有，则向后端请求数据
         wordList.value=(await getTodayReviewWordBySelf()).data;localStorage.setItem("reviewWords", JSON.stringify(wordList.value));
      }
    }else{//learnNew
      if (localStorage.getItem("newWords")) {//有
        wordList.value = JSON.parse(localStorage.getItem("newWords"));
        console.log("本地有数据", wordList.value);
        if(wordList.value.MemorizedWordMsgList!=null||wordList.value.timestamp < Date.now() - 24 * 60 * 60 * 1000){//过期
         wordList.value=(await getTodayNewWordBySelf()).data;localStorage.setItem("newWords", JSON.stringify(wordList.value));
      }
      }else{//没有
         wordList.value=(await getTodayNewWordBySelf()).data;localStorage.setItem("newWords", JSON.stringify(wordList.value));
      }
    }
  currentWord.value=wordList.value.MemorizedWordMsgList[0].wordMsg;
  AllWordsCount=wordList.value.MemorizedWordMsgList.length;
  learningProgress=0
  console.log("当前单词", currentWord.value);
}

// 处理下一个单词
const handleNextWord = async (isCorrect) => {
  let scores = JSON.parse(localStorage.getItem("wordScores")) || {};
  console.log("scores", scores);
  // 更新剩余单词列表
  if (!isCorrect.value) {

    wordList.value.MemorizedWordMsgList.push(wordList.value.MemorizedWordMsgList[0]); // 将首元素加入到末尾
  }
  // 删除首元素
  wordList.value.MemorizedWordMsgList.splice(0, 1);

  console.log("isCorrect", isCorrect.value);

  // 获取当前单词的 wordId
  const wordId = currentWord.value.word.wordId;
  console.log("wordId", wordId);
  // 计算分数，假设分数越低代表掌握越牢
  const timestamp = Date.now();
  let score = isCorrect.value ? 100 : 50; // 假设正确得100分，错误得50分

  // 读取本地打分记录

  // 如果之前有打分记录，结合上次打分情况调整当前分数
  if (scores[wordId]) {
    const lastScore = scores[wordId].score;
    const lastTimestamp = scores[wordId].timestamp;
    const timeDiff = timestamp - lastTimestamp;

    // 根据时间差调整分数，假设时间差越大，分数越高
    score = Math.min(100, lastScore + (timeDiff / ( 1000))); // 每过一天分数增加1分
  }

  // 更新打分记录
  scores[wordId] = { wordId: wordId, isCorrect: isCorrect.value, score:score, timestamp: timestamp };
  localStorage.setItem("wordScores", JSON.stringify(scores));

  // 更新当前单词
  console.log("wordList.value.MemorizedWordMsgList.length",wordList.value.MemorizedWordMsgList.length)
  // 如果所有单词都背完，上传打分记录到服务器
  if (wordList.value.MemorizedWordMsgList.length === 0) {
    currentWord.value = false;
    const newData = Object.values(scores).map(score => ({
      wordId: score.wordId,
      isCorrect: score.isCorrect,
      score: parseInt(score.score)
    }));

    console.log("newData",newData)
    await updataStudayDataBySelfWordId({ newData,pattern:pattern1 });
    localStorage.removeItem("wordScores"); // 清空本地打分记录
    return
  }
    currentWord.value = wordList.value.MemorizedWordMsgList[0].wordMsg;
  learningProgress=100-wordList.value.MemorizedWordMsgList.length/AllWordsCount*100;
};

// 初始化时根据模式获取单词
onMounted(() => {
  fetchWords();
});
</script>

<style scoped>
.word-review-container {
  max-width: 800px;
  margin: 2rem auto;
  padding: 1rem;
}

.start-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.5rem;
  margin-top: 2rem;
}

.progress-info {
  font-size: 1.1rem;
  color: var(--el-text-color-secondary);
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>