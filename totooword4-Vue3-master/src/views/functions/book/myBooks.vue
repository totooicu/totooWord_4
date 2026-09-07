<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
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

      <el-form-item label="书本是否已上架" prop="isPublished">
        <el-select v-model="queryParams.isPublished" placeholder="请选择书本是否已上架" clearable>
          <el-option
              v-for="dict in publish_state"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
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
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['system:book:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:book:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:book:remove']"
        >删除</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--            type="warning"-->
<!--            plain-->
<!--            icon="Download"-->
<!--            @click="handleExport"-->
<!--            v-hasPermi="['system:book:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bookList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="书本ID" align="center" prop="bookId" />
      <el-table-column label="书本标题" align="center" prop="title" />
      <el-table-column label="书本创建时间" align="center" prop="addTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.addTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="书本是否已上架" align="center" prop="isPublished">
        <template #default="scope">
          <dict-tag :options="publish_state" :value="scope.row.isPublished"/>
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
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:book:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:book:remove']">删除</el-button>
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
        <el-form-item label="书本标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入书本标题" />
        </el-form-item>
        <el-form-item label="书本详细描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="书本简介" prop="intro">
          <el-input v-model="form.intro" placeholder="请输入书本简介" />
        </el-form-item>
        <el-form-item label="书本语言" prop="language">
          <el-radio-group v-model="form.language">
            <el-radio
                v-for="dict in word_language"
                :key="dict.value"
                :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="书本出版" prop="isPublished">
          <el-radio-group v-model="form.isPublished">
            <el-radio
                v-for="dict in publish_state"
                :key="dict.value"
                :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
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
import {
  getBook,
  delBook,
  addBook,
  updateBook,
  listBySelf
} from "@/api/functions/book.js";
import BookWordListComponent from "../components/bookWordListComponent.vue";

const { proxy } = getCurrentInstance();
const { publish_state, word_language } = proxy.useDict('publish_state', 'word_language');

const bookList = ref([]);
const openWordMsgList = ref(false);
const openBookId= ref(null);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    title: null,
    addTime: null,
    isPublished: null,
    description: null,
    intro: null,
    language: null
  },
  rules: {
    title: [
      { required: true, message: "书本标题不能为空", trigger: "blur" }
    ],
    addTime: [
      { required: true, message: "书本创建时间不能为空", trigger: "blur" }
    ],
    isPublished: [
      { required: true, message: "书本是否已上架不能为空", trigger: "change" }
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
  listBySelf(queryParams.value).then(response => {
    bookList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  openBookId.value=-1;
  openWordMsgList.value = false;
  reset();
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
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}
function handleShowWordMsgs(row){
  openBookId.value=row.bookId;
  console.log("row",row.bookId)
  openWordMsgList.value = true;
}
/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加书本信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _bookId = row.bookId || ids.value
  getBook(_bookId).then(response => {
    form.value = response.data;
    // 确保 form.isPublished 的值与 publish_state 中的某个 value 匹配
    if (form.value.isPublished !== undefined && form.value.isPublished !== null) {
      form.value.isPublished = form.value.isPublished.toString();
    }
    open.value = true;
    title.value = "修改书本信息";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["bookRef"].validate(valid => {
    if (valid) {
      if (form.value.bookId != null) {
        updateBook(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addBook(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _bookIds = row.bookId || ids.value;
  proxy.$modal.confirm('是否确认删除书本信息编号为"' + _bookIds + '"的数据项？').then(function() {
    return delBook(_bookIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/book/export', {
    ...queryParams.value
  }, `book_${new Date().getTime()}.xlsx`)
}

getList();
</script>
