<template>

    <h2>
      {{wordMsg.word.spell}}
    </h2>
    <h3 v-if="showMeaning" @click="handleOpenWordMsg">
      {{wordMsg.means.map(obj => obj.meaningDefinition).join('；')}}
    </h3>

<el-row>
<!--  依次是不认识按钮、下一个按钮、认识按钮-->
  <el-button type="danger" @click="handleDontKnow" v-if="!showNextButton">不认识</el-button>
  <el-button type="primary" @click="handleNext" v-if="showNextButton">下一个</el-button>
  <el-button type="success" @click="handleKnow" v-if="!showNextButton">认识</el-button>
</el-row>

  <el-dialog  v-if="openWordMsg" v-model="openWordMsg" width="80%" append-to-body>
    <word-detail-component :word-msg="wordMsg_"/>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import WordDetailComponent from "../../components/WordDetailComponent.vue";

const props = defineProps({
  wordMsg: {
    type: Object,
    required: true,
  },
  pattern: {
    type: String,
    required: true,
  },
  onNext: {
    type: Function,
    required: true,
  },
});

const userInput = ref("");
const showMeaning = ref(false);
const showNextButton = ref(false);
const showDetail = ref(false);
const result= ref(false);
const openWordMsg = ref(false);
const wordMsg_ = ref({});
// 处理键盘事件
const handleKeyDown = (event) => {
  if (event.key === "a" || event.key === "ArrowLeft") {
    handleDontKnow();
  } else if (event.key === "d" || event.key === "ArrowRight") {
    handleKnow();
  } else if (event.key === " ") {
    toggleMeaning();
  }
};

// 不认识按钮逻辑
const handleDontKnow = () => {
  showMeaning.value = true;
  showNextButton.value = true;
  showDetail.value = true;
  result.value=false;
  handleOpenWordMsg()
  // props.onNext(false); // 传入是否认识的结果
};
const handleOpenWordMsg=()=>{
  openWordMsg.value=true;
  wordMsg_.value= props.wordMsg;
}
// 认识按钮逻辑
const handleKnow = () => {
  showMeaning.value = true;
  showNextButton.value = true;
  result.value=true;
  // props.onNext(true); // 传入是否认识的结果
};

// 下一个按钮逻辑
const handleNext = () => {
  showMeaning.value = false;
  showNextButton.value = false;
  userInput.value = "";
  props.onNext(result); // 调用父组件的下一个单词逻辑
};

// 切换意思显示
const toggleMeaning = () => {
  showMeaning.value = !showMeaning.value;
};

// 显示单词详细信息
const showWordDetail = () => {
  showDetail.value = true;
};

// 监听键盘事件
onMounted(() => {
  window.addEventListener("keydown", handleKeyDown);
});

onBeforeUnmount(() => {
  window.removeEventListener("keydown", handleKeyDown);
});
</script>