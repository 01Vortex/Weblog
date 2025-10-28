<template>
  <Header></Header>

  <!-- 主内容区域 -->
  <main class="container max-w-screen-xl mx-auto px-4 md:px-6 py-8">
    <!-- grid 表格布局，分为 4 列 -->
    <div class="grid grid-cols-1 lg:grid-cols-4 gap-8">
      <!-- 左边栏，占用 3 列 -->
      <div class="lg:col-span-3 mb-6">
        <!-- 分类列表 -->
        <div class="w-full p-6 mb-6 bg-gradient-to-br from-white to-gray-50 border border-gray-200 rounded-xl shadow-sm hover:shadow-md transition-shadow duration-300 dark:from-gray-800 dark:to-gray-900 dark:border-gray-700">
          <!-- 分类标题 -->
          <div class="flex items-center mb-6 pb-4 border-b border-gray-100 dark:border-gray-700">
            <div class="w-2 h-8 bg-gradient-to-b from-purple-400 to-pink-500 rounded-full mr-3"></div>
            <h2 class="flex items-center font-bold text-xl text-gray-900 uppercase tracking-tight dark:text-white">
              <!-- 文件夹图标 -->
              <svg t="1698998570037" class="inline icon w-6 h-6 mr-2 text-indigo-500" viewBox="0 0 1024 1024" version="1.1"
                   xmlns="http://www.w3.org/2000/svg" p-id="21572" width="200" height="200">
                <path
                    d="M938.666667 464.592593h-853.333334v-265.481482c0-62.577778 51.2-113.777778 113.777778-113.777778h128.948148c15.17037 0 28.444444 3.792593 41.718519 11.377778l98.607407 64.474074h356.503704c62.577778 0 113.777778 51.2 113.777778 113.777778v189.62963z"
                    fill="currentColor" p-id="21573"></path>
                <path
                    d="M805.925926 398.222222h-587.851852v-125.155555c0-24.651852 20.859259-45.511111 45.511111-45.511111h496.82963c24.651852 0 45.511111 20.859259 45.511111 45.511111V398.222222z"
                    fill="currentColor" opacity="0.6" p-id="21574"></path>
                <path
                    d="M843.851852 417.185185h-663.703704v-98.607407c0-28.444444 22.755556-53.096296 53.096296-53.096297h559.407408c28.444444 0 53.096296 22.755556 53.096296 53.096297V417.185185z"
                    fill="currentColor" opacity="0.4" p-id="21575"></path>
                <path
                    d="M786.962963 938.666667h-549.925926c-83.437037 0-151.703704-68.266667-151.703704-151.703704V341.333333s316.681481 37.925926 430.45926 37.925926c189.62963 0 422.874074-37.925926 422.874074-37.925926v445.62963c0 83.437037-68.266667 151.703704-151.703704 151.703704z"
                    fill="currentColor" opacity="0.8" p-id="21576"></path>
                <path
                    d="M559.407407 512h-75.851851c-20.859259 0-37.925926-17.066667-37.925926-37.925926s17.066667-37.925926 37.925926-37.925926h75.851851c20.859259 0 37.925926 17.066667 37.925926 37.925926s-17.066667 37.925926-37.925926 37.925926z"
                    fill="currentColor" opacity="0.3" p-id="21577"></path>
              </svg>
              分类
              <span v-if="categories && categories.length > 0"
                    class="ml-3 px-3 py-1 text-sm font-medium bg-indigo-100 text-indigo-800 rounded-full dark:bg-indigo-900 dark:text-indigo-200">
                                {{ categories.length }} 个分类
                            </span>
            </h2>
          </div>

          <!-- 分类列表 -->
          <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-4">
            <a @click="goCategoryArticleListPage(category.id, category.name)"
               v-for="(category, index) in categories" :key="index"
               class="group relative cursor-pointer flex flex-col items-center justify-center p-5 bg-white border border-gray-200 rounded-xl shadow-sm hover:shadow-lg transition-all duration-300 hover:-translate-y-1 dark:bg-gray-800 dark:border-gray-700">

              <!-- 分类图标 -->
              <div class="w-12 h-12 flex items-center justify-center mb-3 rounded-full bg-gradient-to-r from-indigo-100 to-purple-100 group-hover:from-indigo-200 group-hover:to-purple-200 dark:from-indigo-900 dark:to-purple-900">
                <svg class="w-6 h-6 text-indigo-500 dark:text-indigo-300" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
                  <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2"/>
                </svg>
              </div>

              <!-- 分类名称 -->
              <h3 class="text-base font-semibold text-gray-900 dark:text-white group-hover:text-indigo-600 dark:group-hover:text-indigo-400 transition-colors duration-300">
                {{ category.name }}
              </h3>

              <!-- 文章数量 -->
              <div class="mt-2 flex items-center text-sm text-gray-500 dark:text-gray-400">
                <svg class="w-4 h-4 mr-1 text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
                  <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 8h13m0 0h5m-5 0v8M1 8v8h18V8"/>
                </svg>
                {{ category.articlesTotal }} 篇
              </div>

              <!-- 悬停效果遮罩 -->
              <div class="absolute inset-0 bg-gradient-to-t from-indigo-500/10 to-transparent opacity-0 group-hover:opacity-100 rounded-xl transition-opacity duration-300"></div>
            </a>
          </div>

          <!-- 空状态 -->
          <div v-if="!categories || categories.length === 0"
               class="flex flex-col items-center justify-center py-12 text-center">
            <svg class="w-16 h-16 text-gray-400 mb-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 1v16m9-9H3m7 7V3"/>
            </svg>
            <h3 class="text-lg font-medium text-gray-900 dark:text-white mb-1">暂无分类</h3>
            <p class="text-gray-500 dark:text-gray-400">还没有创建任何分类</p>
          </div>
        </div>
      </div>

      <!-- 右边侧边栏，占用一列 -->
      <aside class="lg:col-span-1">
        <div class="sticky top-[5.5rem] space-y-6">
          <!-- 博主信息 -->
          <UserInfoCard></UserInfoCard>

          <!-- 标签 -->
          <TagListCard></TagListCard>
        </div>
      </aside>
    </div>

  </main>

  <!-- 返回顶部 -->
  <ScrollToTopButton></ScrollToTopButton>

  <Footer></Footer>
</template>

<script setup>
import Header from '@/layouts/frontend/components/Header.vue'
import Footer from '@/layouts/frontend/components/Footer.vue'
import UserInfoCard from '@/layouts/frontend/components/UserInfoCard.vue'
import TagListCard from '@/layouts/frontend/components/TagListCard.vue'
import CategoryListCard from '@/layouts/frontend/components/CategoryListCard.vue'
import ScrollToTopButton from '@/layouts/frontend/components/ScrollToTopButton.vue'
import { getCategoryList } from '@/api/frontend/category'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 跳转分类文章列表页
const goCategoryArticleListPage = (id, name) => {
  // 跳转时通过 query 携带参数（分类 ID、分类名称）
  router.push({ path: '/category/article/list', query: { id, name } })
}

// 所有分类
const categories = ref([])
getCategoryList({}).then((res) => {
  if (res.success) {
    categories.value = res.data
  }
})
</script>

<style scoped>
@media (max-width: 640px) {
  .container {
    padding-left: 1rem;
    padding-right: 1rem;
  }

  .grid {
    grid-template-columns: 1fr;
  }

  .lg\:col-span-3 {
    grid-column: span 1 / span 1;
  }

  .lg\:col-span-1 {
    grid-column: span 1 / span 1;
  }

  .grid-cols-2,
  .sm\:grid-cols-3,
  .md\:grid-cols-4 {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}
</style>



