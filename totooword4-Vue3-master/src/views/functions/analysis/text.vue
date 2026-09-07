<template>
  <div>
  <el-row>
    <el-col :span="12">
      <el-input
          v-model="textarea2"
          :autosize="{ minRows: 2, maxRows: 4 }"
          type="textarea"
          placeholder="Please input"
      />
<!--      <el-input-number v-model="bookid" :min="0"  @change="handleChange" />-->
<!--      <SelectComponent v-model:selectedValue="bookid" :data="bookOptions" :multipleSelect="false"  title="请选择书本"/>-->
      <book-selector-component
          v-if="isSearched"
          title="选择学习词库"
          v-model:selectedValue="bookid"
          :multiple-select="false"
          class="book-selector"
      />
      <el-button v-if="isSearched" v-loading="loading" type="primary" @click="onSubmit2Book" round>添加到书本</el-button>
      <el-table
          v-if="isSearched"
          :default-sort="{ prop: 'word', order: 'descending' }"
          ref="multipleTableRef"
          :data="tableData"
          style="width: 100%"
          @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection"  />
        <el-table-column type="index"  />
        <el-table-column property="word" sortable  width="80" label="word" />
        <el-table-column property="count" sortable  width="80" label="count"  />
      </el-table>
    </el-col>
    <el-col :span="12">
      <el-button type="primary" @click="onSubmit" round>提交</el-button>

      <template v-if="showChat">
        <Bar  :X="X" :Y="Y" />
        <Pie  :X="X" :Y="Y"/>
      </template>

    </el-col>
  </el-row>
  </div>
</template>

<script  setup>
import { ref } from 'vue'
import {ContentStatisticsByJson,getWordMsgsBySpellsLan} from "@/api/functions/wordMsg.js"
import {addsByBookWordsByBookIdWordId} from "@/api/functions/bookWord.js"
import {listBySelf} from "@/api/functions/book.js"
import Bar from "../components/EchartsGraph/Bar.vue"
import Pie from "../components/EchartsGraph/Pie.vue"
import SelectComponent from "../components/selector/SelectorComponent.vue";
import BookSelectorComponent from "../components/selector/BookSelectorComponent.vue";

const textarea2 = ref('')
const Statistics= ref('')
const bookOptions = ref([])
const showChat=ref(false)
const isSearched=ref(false)
var X
var Y
const bookid = ref([])
const loading = ref(false)
const handleChange = (value) => {
  console.log(value)
}

listBySelf().then(myBooks=>{
  bookOptions.value=[]
  console.log("myBooks",myBooks.rows)
  if (myBooks && myBooks.rows)
    myBooks.rows.forEach(book => {
      bookOptions.value.push({
        key: book.bookId,
        label: book.title,
        value: book.bookId
      })
    })
});
const onSubmit2Book = async () => {
  console.log("选择的书本为",bookid.value)
  if(bookid.value===null){
    this.$modal.msgError("请选择书本");
    return
  }
  //加载动画
  loading.value=true
  var XX={
    spells:X,
    language:"en",
  }
  var words=await getWordMsgsBySpellsLan(XX);

  words=words.data

  var YY=[]

  for(const i in words){
    // console.log(Y[i])
    YY.push({
      bookId:bookid.value,
      wordId:words[i].word.wordId,
      annotation:"频率:"+Y[i],
    })
  }

  await addsByBookWordsByBookIdWordId({bookWords:YY})



  // 隐藏加载动画
  loading.value=false

}

const tableData=ref([])
const onSubmit = async () => {
  showChat.value=false
  isSearched.value=false
  Statistics.value=await ContentStatisticsByJson({text: textarea2.value,language:"en"})
  console.log(typeof Statistics.value)
  console.log( Statistics.value["data"])
  X = Object.keys(Statistics.value["data"])
  Y = Object.values(Statistics.value["data"])

  console.log(X,Y)

  for (const i in X) {
    tableData.value.push({
      word:X[i],
      count:Y[i],
    })
  }
  isSearched.value=true
  setTimeout(()=>{
    showChat.value=true
  },1000)

}





</script>

<style scoped>

</style>