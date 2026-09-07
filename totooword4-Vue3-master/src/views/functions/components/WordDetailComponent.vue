<template>
  <el-card class="word-card">
    <el-descriptions title="Word Details" :column="1" border>
      <el-descriptions-item label="Language" v-if="word.language">
        {{ word.language }}
      </el-descriptions-item>
      <el-descriptions-item label="Pronunciation" v-if="pronunciation.length">
        <ul class="compact-list">
          <li v-for="pron in pronunciation" :key="pron.pronunciationType">
            <strong>{{ pron.pronunciationType }}:</strong> {{ pron.transcription }}
          </li>
        </ul>
      </el-descriptions-item>
      <el-descriptions-item label="Means" v-if="means.length">
        <ul class="compact-list">
          <li v-for="mean in means" :key="mean.meaningType">
            <strong>{{ mean.meaningType }}:</strong> {{ mean.meaningDefinition }}
          </li>
        </ul>
      </el-descriptions-item>
      <el-descriptions-item label="Stages" v-if="stage.length">
        <ul class="compact-list">
          <li v-for="stg in stage" :key="stg.stageText">
            {{ stg.stageText }}
          </li>
        </ul>
      </el-descriptions-item>
      <el-descriptions-item label="Tenses" v-if="tense.length">
        <ul class="compact-list">
          <li v-for="ten in tense" :key="ten.inflectionType">
            <strong>{{ ten.inflectionType }}:</strong> {{ ten.inflectionText }}
          </li>
        </ul>
      </el-descriptions-item>
      <el-descriptions-item label="Phrases" v-if="phrase.length">
        <ul class="compact-list">
          <li v-for="phr in phrase" :key="phr.phraseText">
            <strong>{{ phr.phraseText }}:</strong> {{ phr.phraseDefinition }}
          </li>
        </ul>
      </el-descriptions-item>
      <el-descriptions-item label="Example Sentences" v-if="exampleSentence.length">
        <ul class="compact-list">
          <li v-for="sent in exampleSentence" :key="sent.sentenceText">
            <strong>{{ sent.sentenceText }}:</strong> {{ sent.sentenceDefinition }}
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
      return this.wordMsg.word || {};
    },
    pronunciation() {
      return this.wordMsg.pronunciation || [];
    },
    means() {
      return this.wordMsg.means || [];
    },
    stage() {
      return this.wordMsg.stage || [];
    },
    tense() {
      return this.wordMsg.tense || [];
    },
    phrase() {
      return this.wordMsg.phrase || [];
    },
    exampleSentence() {
      return this.wordMsg.exampleSentence || [];
    }
  }
};
</script>

<style scoped>
.word-card {
  margin: 10px;
  padding: 10px;
}

.compact-list {
  margin: 0;
  padding-left: 20px;
}

.compact-list li {
  margin-bottom: 5px;
  line-height: 1.4;
}
</style>