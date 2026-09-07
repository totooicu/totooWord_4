<!-- BookWordOperationPage.vue -->
<template xmlns="http://www.w3.org/1999/html">
  <div>
    <el-row style="margin-bottom: 20px; margin-left: 10px;":gutter="20">
      使用说明：对书本中的单词进行如下公式运算，bij为所选定的书本，opk为运算符，其中k为运算符的序号
    </el-row>
    <el-row :gutter="20" style="margin-bottom: 20px;margin-left: 30%; ">
      (b11 op1 b12 op1 b13 ...) op3 (b21 op2 b22 op2 b23 ...)
    </el-row>

    <!-- 第一行：选择书本以及表达式 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <!-- 语言选择器 -->
      <el-col :span="4">
      <language-selector v-model:selected-language="lan"/>
      </el-col>
      <!-- 第一个选择组件 chosenBookIds1 -->
      <el-col :span="8">
        <div style="display: flex; flex-direction: column;">
          <el-select v-model="op1" placeholder="选择操作op1">
            <el-option label="交集" value="2" />
            <el-option label="并集" value="1" />
          </el-select>
          <BookSelectorComponent
              v-if="lan!==''&&op1!==''"
              title="选择书本b1i"
              :data="bookOptions"
              v-model:selectedValue="chosenBookIds1"
          />
          <el-button  @click="handlePreview(1)" style="margin-top: 10px;" type="primary" round> 预览</el-button>
        </div>
      </el-col>
      <!-- 运算符号选择 op2 -->
      <el-col :span="4">
        <div style="display: flex; flex-direction: column;">
        <el-select v-model="op3" placeholder="选择操作op3">
          <el-option label="交集" value="2" />
          <el-option label="并集" value="1" />
          <el-option label="补集" value="3" />
        </el-select>
        <br><br>
        <el-button   @click="handlePreview(3)" style="margin-top: 10px;" type="primary" round> 预览</el-button>
          </div>
      </el-col>
      <!-- 第二个选择组件 chosenBookIds2 -->
      <el-col :span="8">
        <div style="display: flex; flex-direction: column;">
          <el-select v-model="op2" placeholder="选择操作op2">
            <el-option label="交集" value="2" />
            <el-option label="并集" value="1" />
          </el-select>
          <BookSelectorComponent
              v-if="lan!==''&&op2!==''"
              title="选择书本b2j"
              :data="bookOptions"
              v-model:selectedValue="chosenBookIds2"
          />
          <el-button  @click="handlePreview(2)" style="margin-top: 10px;" type="primary" round> 预览</el-button>

        </div>
      </el-col>
    </el-row>


    <!-- 第二行：选择存储到书本的方式 -->
    <el-row :gutter="20">
      <!-- 选择存储方式 -->
      <el-col :span="4">
        <el-select v-model="saveOption" placeholder="选择保存方式">
          <el-option label="新建书本" value="new" />
          <el-option label="追加到书本" value="append" />
        </el-select>
      </el-col>

      <!-- 保存到书本的选择 -->
      <el-col :span="8">
        <template v-if="saveOption === 'new'">
          <el-form :model="newBookForm" label-width="100px">
            <el-form-item label="书名">
              <el-input v-model="newBookForm.title" />
            </el-form-item>
            <el-form-item label="语言">
            <LanguageSelector v-model:selected-language="lan" :select-able="false"/>
            </el-form-item>
            <el-form-item label="描述">
              <el-input v-model="newBookForm.description" />
            </el-form-item>
            <el-form-item label="简介">
              <el-input v-model="newBookForm.intro" />
            </el-form-item>
          </el-form>
        </template>
        <template v-else>
          <BookSelectorComponent
              title="选择要追加的书本"
              :multiple-select="false"
              :data="bookOptions"
              v-model:selectedValue="savedBookId"
          />
        </template>
      </el-col>
      <!-- 保存按钮 -->
      <el-col :span="4">
        <el-button type="primary" @click="handleSave" round>保存</el-button>
      </el-col>
    </el-row>

  <el-dialog title="" v-if="openWordMsgList" v-model="openWordMsgList" width="80%" append-to-body>
    <book-word-list-component :wordMsgList="previewData" />
  </el-dialog>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { getBookByOwnWordIdOrLan, listByUserBookBySelf_ByBook,addBook } from "@/api/functions/book.js";
import { addsByBookWordsByBookIds_ByLogical ,getWordMapByBookWordsByBookIds_ByLogical} from "@/api/functions/bookWord.js";
import BookSelectorComponent from "../components/selector/BookSelectorComponent.vue";
import LanguageSelector from "../components/selector/LanguageSelector.vue"
import {ElMessage} from "element-plus";
import BookWordListComponent from "../components/bookWordListComponent.vue";

const lan = ref('');
const bookOptions = ref([]);
const chosenBookIds1 = ref([]);
const chosenBookIds2 = ref([]);
const savedBookId = ref([]);
const op1 = ref('');
const op2 = ref('');
const op3 = ref('');
const saveOption = ref('');
const newBookForm = ref({
  title: '',
  language: '',
  description: '',
  intro: ''
});
function getNewBookForm(){}
const previewData = ref([]);
const openWordMsgList=ref(false)
const fetchBooks = async () => {
  try {
    const response = await getBookByOwnWordIdOrLan({ lan: lan.value });
    bookOptions.value = response.data.map(book => ({
      key: book.bookId,
      label: book.title,
      value: book.bookId
    }));
  } catch (error) {
    console.error('获取我的书籍失败:', error);
  }
  try{
    const response=await listByUserBookBySelf_ByBook({language:lan.value});
    bookOptions.value= bookOptions.value.concat( response.data.map(book => ({
      key: book.bookId,
      label: book.title,
      value: book.bookId
    })));
  } catch (error) {
    console.error('获取收藏的书籍失败:', error);
  }
  //去重
  bookOptions.value = [...new Map(bookOptions.value.map(item => [item['key'], item])).values()];

  console.log("获取到的书本信息为：",bookOptions)
};

const handleSave = async () => {
  const payload = {
    chosenBookIds1: chosenBookIds1.value,
    chosenBookIds2: chosenBookIds2.value,
    savedBookId: savedBookId.value || '',
    op1: op1.value,
    op2: op2.value,
    op3: op3.value
  };
  console.log("待发送数据为",payload)
  if (saveOption.value === 'new') {
    try {
      newBookForm.value.language = lan.value;
      const newBookResponse = await addBook(newBookForm.value);
      payload.savedBookId = newBookResponse.data.bookId;
      await addsByBookWordsByBookIds_ByLogical(payload).then(() => {
            console.log('新书本创建并保存成功');
          },
          (error) => {
            console.error('创建新书本失败:', error);
          }
      );
      console.log('新书本创建并保存成功');
    } catch (error) {
      console.error('创建新书本失败:', error);
    }
  } else if (saveOption.value === 'append') {
    try {
      await addsByBookWordsByBookIds_ByLogical(payload).then(() => {
            console.log('追加保存成功');
          },
          (error) => {
            console.error('追加失败:', error);
      });
      console.log('追加到现有书本成功');
    } catch (error) {
      console.error('追加到现有书本失败:', error);
    }
  }
};
async function  handlePreview(n){

  switch (n){
    //检查数据是否装入
    case 1:
      if(op1.value===''||chosenBookIds1.value.length===0){//弹出提示
        ElMessage({ message: '请选择书本以及操作', type: 'warning' });
        return;
      }
      break;
    case 2:
      if(op2.value===''||chosenBookIds2.value.length===0){//弹出提示
        ElMessage({ message: '请选择书本以及操作', type: 'warning' });
        return;
      }
      break;
    case 3:
      if(op3.value===''){//弹出提示
        ElMessage({ message: '请选择操作', type: 'warning' });
        return;
      }
      }

  const payload = {
    chosenBookIds1: chosenBookIds1.value,
    chosenBookIds2: chosenBookIds2.value,
    savedBookId: savedBookId.value || '',
    op1: op1.value,
    op2: op2.value,
    op3: op3.value
  };
previewData.value=(await getWordMapByBookWordsByBookIds_ByLogical(payload)).data;
switch (n){
  case 1:previewData.value=previewData.value.preview1;break;
  case 2:previewData.value=previewData.value.preview2;break;
  case 3:previewData.value=previewData.value.preview3;break;
}
  openWordMsgList.value=true;
}
watch(lan, (newLan) => {
  console.log('newlan:',newLan," oldlan:",lan )
  if (newLan) {
    fetchBooks();
  }
});
</script>

<style scoped>
</style>
