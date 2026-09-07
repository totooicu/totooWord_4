<template>
  <el-card>

    <el-descriptions title="Word Details" :column="1" border>
      <el-descriptions-item label="Language" v-if="word.language">
        {{ word.language }}
      </el-descriptions-item>
      <el-descriptions-item label="Pronunciation" v-if="pronunciation.length">
        <ul>
          <li v-for="pron in pronunciation" :key="pron.pronunciationType">
            {{ pron.pronunciationType }}: {{ pron.transcription }}
          </li>
        </ul>
      </el-descriptions-item>
      <el-descriptions-item label="Means" v-if="means.length">
        <ul>
          <li v-for="mean in means" :key="mean.meaningType">
            {{ mean.meaningType }}: {{ mean.meaningDefinition }}
          </li>
        </ul>
      </el-descriptions-item>
      <el-descriptions-item label="Stages" v-if="stage.length">
        <ul>
          <li v-for="stg in stage" :key="stg.stageText">
            {{ stg.stageText }}
          </li>
        </ul>
      </el-descriptions-item>
      <el-descriptions-item label="Tenses" v-if="tense.length">
        <ul>
          <li v-for="ten in tense" :key="ten.inflectionType">
            {{ ten.inflectionType }}: {{ ten.inflectionText }}
          </li>
        </ul>
      </el-descriptions-item>
      <el-descriptions-item label="Phrases" v-if="phrase.length">
        <ul>
          <li v-for="phr in phrase" :key="phr.phraseText">
            {{ phr.phraseText }}: {{ phr.phraseDefinition }}
          </li>
        </ul>
      </el-descriptions-item>
      <el-descriptions-item label="Example Sentences" v-if="exampleSentence.length">
        <ul>
          <li v-for="sent in exampleSentence" :key="sent.sentenceText">
            {{ sent.sentenceText }}: {{ sent.sentenceDefinition }}
          </li>
        </ul>
      </el-descriptions-item>
    </el-descriptions>
  </el-card>
</template>

<script>
export default {
  name: 'WordDetailComponent',
  props: {
    wordMsg: {
      type: Object,
      required: true
    }
  },
  computed: {
    word() {
      return this.wordMsg.word;
    },
    pronunciation() {
      return this.wordMsg.pronunciation;
    },
    means() {
      return this.wordMsg.means;
    },
    stage() {
      return this.wordMsg.stage;
    },
    tense() {
      return this.wordMsg.tense;
    },
    phrase() {
      return this.wordMsg.phrase;
    },
    exampleSentence() {
      return this.wordMsg.exampleSentence;
    }
  }
};
</script>

<style scoped>
/* 添加一些样式 */
</style>
