<template>
  <div class="book-word-list">
    <el-table :data="wordMsgList" style="width: 100%">
      <el-table-column prop="word.spell" label="单词拼写" width="200px" />
      <el-table-column label="单词简明信息" width="700px">
        <template #default="scope">
          <div>
            <p v-for="pron in scope.row.pronunciation" :key="pron.pronunciationType">
              {{ pron.pronunciationType }}: {{ pron.transcription }}
            </p>
            <p v-for="mean in scope.row.means" :key="mean.meaningType">
              {{ mean.meaningType }}: {{ mean.meaningDefinition }}
            </p>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200px">
        <template #default="scope">
          <el-button link type="primary" @click="handleDetail(scope.row)">查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="title" v-if="openWordMsg" v-model="openWordMsg" width="80%" append-to-body>
      <book-word-selector-component :word-id="openWordId" :bookId="openBookId"/>
      <word-detail-component :word-msg="wordMsg_"/>
    </el-dialog>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getWordMsgsByBookId } from '../../../api/functions/wordMsg.js';
import BookWordSelectorComponent from "./selector/BookWordSelectorComponent.vue";
import WordDetailComponent from "./WordDetailComponent.vue";
const props = defineProps({
  bookId: {
    type: String,
    required: false
  },
  wordMsgList: {
    type: Array,
    required: false
  }
});

const wordMsgList = ref([]);
const title = ref('');
const openBookId = ref('');
const openWordMsg = ref(false);
const wordMsg_ = ref("");
const openWordId = ref('');
watch([() => props.bookId, () => props.wordMsgList], ([bookId, wordMsgList]) => {
  if (bookId) {
    getWordMsgsByBookId({ bookId }).then(response => {
      wordMsgList.value = response.data;
    });
  } else if (wordMsgList) {
    wordMsgList.value = wordMsgList;
  }
});

onMounted(() => {
  console.log('BookWordListComponent mounted ;BookID', props.bookId);

  if (props.bookId) {
    getWordMsgsByBookId({ bookId: props.bookId }).then(response => {
      wordMsgList.value = response.data;
    });
  } else if (props.wordMsgList) {
    wordMsgList.value = props.wordMsgList;
  }
});


const handleDetail = (wordMsg) => {
  title.value = wordMsg.word.spell;
  openBookId.value = props.bookId;
  wordMsg_.value= wordMsg;
  openWordId.value = wordMsg.word.wordId;
  // 调用 WordDetailComponent 传入 wordMsg
  console.log('Show detail for word:', wordMsg);
  openWordMsg.value = true;

};
</script>

<style scoped>
.book-word-list {
  padding: 20px;
}
</style>
