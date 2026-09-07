<!-- BookWordOperationPage.vue -->
<template>
  <div>
    <!-- 第一行：选择书本以及表达式 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <!-- 语言选择器 -->
      <el-col :span="4">
      <language-selector v-model:selected-language="lan"/>
      </el-col>
      <!-- 第一个选择组件 chosenBookIds1 -->
      <el-col :span="8">
        <div style="display: flex; flex-direction: column;">
          <el-select v-model="op1" placeholder="选择操作">
            <el-option label="交集" value="2" />
            <el-option label="并集" value="1" />
          </el-select>
          <BookSelectorComponent
              title="选择书本"
              :data="bookOptions"
              v-model:selectedValue="chosenBookIds1"
          />
        </div>
      </el-col>
      <!-- 运算符号选择 op2 -->
      <el-col :span="4">
        <el-select v-model="op2" placeholder="选择操作">
          <el-option label="交集" value="2" />
          <el-option label="并集" value="1" />
          <el-option label="补集" value="3" />
        </el-select>
      </el-col>
      <!-- 第二个选择组件 chosenBookIds2 -->
      <el-col :span="8">
        <div style="display: flex; flex-direction: column;">
          <el-select v-model="op3" placeholder="选择操作">
            <el-option label="交集" value="2" />
            <el-option label="并集" value="1" />
          </el-select>
          <BookSelectorComponent
              title="选择书本"
              :data="bookOptions"
              v-model:selectedValue="chosenBookIds2"
          />
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
              title="选择书本"
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
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { getBookByOwnWordIdOrLan, addBook } from "../../../api/functions/book.js";
import { addsByBookWordsByBookIds_ByLogical } from "../../../api/functions/bookWord.js";
import BookSelectorComponent from "../components/selector/BookSelectorComponent.vue";
import LanguageSelector from "../components/selector/LanguageSelector.vue"

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

const fetchBooks = async () => {
  try {
    const response = await getBookByOwnWordIdOrLan({ lan: lan.value });
    bookOptions.value = response.data.map(book => ({
      key: book.bookId,
      label: book.title,
      value: book.bookId
    }));
  } catch (error) {
    console.error('获取书籍失败:', error);
  }
};

const handleSave = async () => {
  const payload = {
    chosenBookIds1: chosenBookIds1.value,
    chosenBookIds2: chosenBookIds2.value,
    savedBookId: savedBookId.value[0] || '',
    op1: op1.value,
    op2: op2.value,
    op3: op3.value
  };

  if (saveOption.value === 'new') {
    try {
      const newBookResponse = await addBook(newBookForm.value);
      payload.savedBookId = newBookResponse.bookId;
      await addsByBookWordsByBookIds_ByLogical(payload);
      console.log('新书本创建并保存成功');
    } catch (error) {
      console.error('创建新书本失败:', error);
    }
  } else if (saveOption.value === 'append') {
    try {
      await addsByBookWordsByBookIds_ByLogical(payload);
      console.log('追加到现有书本成功');
    } catch (error) {
      console.error('追加到现有书本失败:', error);
    }
  }
};

watch(lan, (newLan) => {
  console.log('newlan:',newLan," oldlan:",lan )
  if (newLan) {
    fetchBooks();
  }
});
</script>

<style scoped>
</style>
