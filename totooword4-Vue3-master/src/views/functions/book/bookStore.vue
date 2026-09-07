<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="书本拥有者ID" prop="ownerId">
        <el-input
            v-model="queryParams.ownerId"
            placeholder="请输入书本拥有者ID"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="书本标题" prop="title">
        <el-input
            v-model="queryParams.title"
            placeholder="请输入书本标题"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="书本创建时间" prop="addTime">
        <el-date-picker clearable
                        v-model="queryParams.addTime"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择书本创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="书本简介" prop="intro">
        <el-input
            v-model="queryParams.intro"
            placeholder="请输入书本简介"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="书本语言" prop="language">
        <el-select v-model="queryParams.language" placeholder="请选择书本语言" clearable>
          <el-option
              v-for="dict in word_language"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            @click="handleUpdate"
            v-hasPermi="['system:book:edit']"
        >更改收藏关系</el-button>
      </el-col>

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bookList" ref="orgidsListRef" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="书本ID" align="center" prop="bookId" />
      <el-table-column label="书本拥有者ID" align="center" prop="ownerId" />
      <el-table-column label="书本标题" align="center" prop="title" />
      <el-table-column label="书本创建时间" align="center" prop="addTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.addTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="书本详细描述" align="center" prop="description" />
      <el-table-column label="书本简介" align="center" prop="intro" />
      <el-table-column label="书本语言" align="center" prop="language">
        <template #default="scope">
          <dict-tag :options="word_language" :value="scope.row.language"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
        <el-button link type="primary"  @click="handleShowWordMsgs(scope.row)" >查看单词</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <!-- 添加或修改书本信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="bookRef" :model="form" :rules="rules" label-width="80px">
        <el-divider content-position="center">用户收藏书本信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddFunUserBook">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeleteFunUserBook">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="funUserBookList" :row-class-name="rowFunUserBookIndex" @selection-change="handleFunUserBookSelectionChange" ref="funUserBook">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="收藏时间" prop="collectionTime" width="240">
            <template #default="scope">
              <el-date-picker clearable
                              v-model="scope.row.collectionTime"
                              type="date"
                              value-format="YYYY-MM-DD"
                              placeholder="请选择收藏时间">
              </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column label="收藏备注" prop="collectionNote" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.collectionNote" placeholder="请输入收藏备注" />
            </template>
          </el-table-column>
          <el-table-column label="是否拥有此书" prop="isOwned" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.isOwned" placeholder="请输入是否拥有此书" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
    <el-dialog :title="title" v-if="openWordMsgList" v-model="openWordMsgList" width="80%" append-to-body>
      <book-word-list-component :bookId="openBookId"/>
    </el-dialog>
  </div>
</template>

<script setup name="Book">
import { listBook, getBook, delBook, addBook, updateBook ,listByUserBookBySelf} from "../../../api/functions/book.js";
import {ElTable} from "element-plus";
import {editByBookIdsWordIdSelf} from "../../../api/functions/userBook.js";
import BookWordListComponent from "../components/bookWordListComponent.vue";

const { proxy } = getCurrentInstance();
const { publish_state, word_language } = proxy.useDict('publish_state', 'word_language');

const bookList = ref([]);
const selectedBookList = ref([]);//被选中的书本
const selectedTotal = ref(0);
const openWordMsgList = ref(false);
const openBookId= ref(null);
const selectedIndexList= ref([]);
const funUserBookList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const checkedFunUserBook = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const orgidsListRef = ref(null);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    ownerId: null,
    title: null,
    addTime: null,
    description: null,
    intro: null,
    isPublished: null,
    language: null
  },
  rules: {
    title: [
      { required: true, message: "书本标题不能为空", trigger: "blur" }
    ],
    addTime: [
      { required: true, message: "书本创建时间不能为空", trigger: "blur" }
    ],
    language: [
      { required: true, message: "书本语言不能为空", trigger: "change" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询书本信息列表 */
function getList() {
  loading.value = true;
  queryParams.value.isPublished=1
  listBook(queryParams.value).then(response => {
    bookList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });

    listByUserBookBySelf(queryParams.value).then(response => {
      selectedBookList.value = response.rows;
      selectedTotal.value = response.total;
      // console.log(selectedBookList.value);
      // //将selectedBookList的bookId加入到ids
      // ids.value = response.rows.map(item => item.bookId);
      // console.log(ids.value)

      // 手动设置表格的选中状态
      nextTick(() => {
        selectedBookList.value.forEach(book => {
          const row = bookList.value.find(item => item.bookId === book.bookId);
          if (row) {
            orgidsListRef.value.toggleRowSelection(row, true);
          }
        });
      });
    });
}

onMounted(() => {
  orgidsListRef .value.toggleRowSelection(2, true);
  console.log("orgidsListRef",orgidsListRef.value)
  console.log("orgidsListRef getSelectionRows",orgidsListRef.value.getSelectionRows())

});

// 取消按钮
function cancel() {
  open.value = false;
  openWordMsgList.value = false;
  openBookId.value=-1;
  reset();
}

function handleShowWordMsgs(row){
  openBookId.value=row.bookId;
  console.log("row",row.bookId)
  openWordMsgList.value = true;
}

// 表单重置
function reset() {
  form.value = {
    bookId: null,
    ownerId: null,
    title: null,
    addTime: null,
    isPublished: null,
    description: null,
    intro: null,
    language: null
  };
  funUserBookList.value = [];
  proxy.resetForm("bookRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.bookId);
  // ids.value = selection;
  console.log(ids.value)
  single.value = selection.length != 1;
  multiple.value = !selection.length;
  console.log("orgidsListRef getSelectionRows",orgidsListRef.value.getSelectionRows())

}


/** 修改按钮操作 */
function handleUpdate(row) {
  console.log("ids",ids)
  editByBookIdsWordIdSelf({bookIds: ids.value}).then(
      () => {
        //成功通知
        proxy.$modal.msgSuccess("修改成功");
        //刷新表格
        getList();
      },
      //失败通知
      () => {
        proxy.$modal.msgError("修改失败");
      }
  )
}


/** 用户收藏书本序号 */
function rowFunUserBookIndex({ row, rowIndex }) {
  row.index = rowIndex + 1;
}




/** 复选框选中数据 */
function handleFunUserBookSelectionChange(selection) {
  checkedFunUserBook.value = selection.map(item => item.index)
}



getList();

</script>
