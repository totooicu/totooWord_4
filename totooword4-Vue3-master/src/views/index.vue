<template>
  <div class="app-container">
    <!-- 基础功能 -->
    <div class="menu-group">
      <h2>基础功能</h2>
      <el-button
          type="primary"
          @click="handleNavigate('functions/wordSearch')"
          class="menu-btn"
      >
        搜索单词
      </el-button>
    </div>

    <!-- 书本相关功能 -->
    <div class="menu-group">
      <h2>书本相关功能</h2>
      <el-row :gutter="20">
        <el-col :span="6" v-for="(item, index) in bookFunctions" :key="index">
          <el-button
              type="success"
              @click="handleNavigate(item.path)"
              class="menu-btn"
          >
            {{ item.title }}
          </el-button>
        </el-col>
      </el-row>
    </div>

    <!-- 数据分析功能 -->
    <div class="menu-group">
      <h2>数据分析功能</h2>
      <el-button-group>
        <el-button
            v-for="(item, index) in analysisFunctions"
            :key="index"
            type="warning"
            @click="handleNavigate(item.path)"
            class="menu-btn"
        >
          {{ item.title }}
        </el-button>
      </el-button-group>
    </div>

    <!-- 社交功能 -->
    <div class="menu-group">
      <h2>社交功能</h2>
      <el-button
          type="danger"
          @click="handleNavigate('functions/socializing/chat')"
          class="menu-btn"
      >
        聊天
      </el-button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

// 书本相关功能列表
const bookFunctions = [
  { title: '我的书本', path: 'functions/book/myBooks' },
  { title: '收藏的书本', path: 'functions/book/bookCollection' },
  { title: '书本商城', path: 'functions/book/bookStore' },
  { title: '书本操作', path: 'functions/book/operation' },
  { title: '背单词', path: 'functions/book/memorize' }
]

// 数据分析功能列表
const analysisFunctions = [
  { title: '文本分析', path: 'functions/analysis/text' },
  { title: '书本分析', path: 'functions/analysis/book' }
]

// 路由跳转方法
const handleNavigate = (path) => {
  router.push({
    path: `/${path}`  // 根据实际路由配置调整路径格式
  })
}
</script>

<style scoped>
.app-container {
  padding: 20px;
}

.menu-group {
  margin-bottom: 30px;
  padding: 20px;
  background-color: #f5f7fa;
  border-radius: 8px;
}

.menu-group h2 {
  color: #409eff;
  margin-bottom: 15px;
}

.menu-btn {
  margin: 5px;
  min-width: 120px;
}

.el-button-group {
  margin: 10px 0;
}
</style>