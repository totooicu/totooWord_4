<template>
 <multiple-select-component title="请选择书本"  :data="bookOptions" v-model:selectedValue="selectedBookIds" />

  <el-button type="primary" @click="analyze" round>分析</el-button>
  <template v-if="showChat">
    <Line :X="bname" :Y="bookWordNums"/>
    <Scatter :words="words" :bname="bname"/>


  </template>
</template>

<script setup>
import { ref } from 'vue'
import {BooksStatisticsByBookIds,listBySelf} from "../../../api/functions/book.js"
import Line from "../components/EchartsGraph/Line.vue"
import Scatter from "../components/EchartsGraph/Scatter.vue"
import MultipleSelectComponent from "../components/selector/SelectorComponent.vue";
const showChat = ref(false)

var bids=[]
var bookWordNums,words,bname
const bookOptions=ref()
const selectedBookIds=ref()

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


const analyze = async () => {
  showChat.value=false
  bookWordNums=words=bname=bids=null
  bids = selectedBookIds.value
  console.log(bids)
  var msg=await BooksStatisticsByBookIds({bookIds:bids})
  msg=msg.data
  console.log(msg)
  bookWordNums=msg.bookWordNums
  words=msg.words
  bname=msg.bname
  console.log(words)

  setTimeout(() => {
    showChat.value = true
  }, 100)
  // showChat.value = true;
}
</script>

