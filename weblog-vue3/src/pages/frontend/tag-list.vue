<template>
  <Header></Header>

  <!-- 主内容区域 -->
  <main class="container max-w-screen-xl mx-auto px-4 md:px-6 py-8">
    <!-- grid 表格布局，分为 4 列 -->
    <div class="grid grid-cols-1 lg:grid-cols-4 gap-8">
      <!-- 左边栏，占用 3 列 -->
      <div class="lg:col-span-3 mb-6">
        <!-- 标签 -->
        <div v-if="tags && tags.length > 0"
             class="w-full p-6 mb-6 bg-gradient-to-br from-white to-gray-50 border border-gray-200 rounded-xl shadow-sm hover:shadow-md transition-shadow duration-300 dark:from-gray-800 dark:to-gray-900 dark:border-gray-700">
          <!-- 标签标题 -->
          <div class="flex items-center mb-6 pb-4 border-b border-gray-100 dark:border-gray-700">
            <div class="w-2 h-8 bg-gradient-to-b from-pink-400 to-rose-500 rounded-full mr-3"></div>
            <h2 class="flex items-center font-bold text-xl text-gray-900 uppercase tracking-tight dark:text-white">
              <!-- 标签图标 -->
              <svg t="1698980289658" class="icon w-6 h-6 mr-2 text-rose-500" viewBox="0 0 1024 1024" version="1.1"
                   xmlns="http://www.w3.org/2000/svg" p-id="13858" width="200" height="200">
                <path
                    d="M646.4512 627.5584m-298.1888 0a298.1888 298.1888 0 1 0 596.3776 0 298.1888 298.1888 0 1 0-596.3776 0Z"
                    fill="currentColor" p-id="13859"></path>
                <path
                    d="M467.6096 962.5088c-34.4064 0-68.7616-13.1072-94.976-39.2704l-276.48-276.48c-52.3776-52.3776-52.3776-137.5744 0-189.9008L465.4592 87.552a105.216 105.216 0 0 1 76.8512-30.6176l308.6336 8.3456c55.3472 1.4848 100.096 46.0288 101.7856 101.376l9.5744 310.1696c0.8704 28.7744-10.2912 56.9344-30.6176 77.2608l-369.2032 369.2032c-26.112 26.112-60.4672 39.2192-94.8736 39.2192z m71.8848-844.1856c-11.4176 0-22.4768 4.5568-30.5664 12.6464L139.6224 500.2752c-28.416 28.416-28.416 74.6496 0 103.0144l276.48 276.48c28.416 28.416 74.6496 28.416 103.0144 0l369.2032-369.2032a43.4176 43.4176 0 0 0 12.6464-31.8976l-9.5744-310.1696c-0.7168-22.8864-19.2-41.2672-42.0352-41.8816l-308.6336-8.3456c-0.4608 0.0512-0.8192 0.0512-1.2288 0.0512z"
                    fill="currentColor" p-id="13860"></path>
                <path
                    d="M676.4032 445.5424c-62.208 0-112.8448-50.6368-112.8448-112.8448s50.6368-112.8448 112.8448-112.8448c62.208 0 112.8448 50.6368 112.8448 112.8448s-50.6368 112.8448-112.8448 112.8448z m0-164.1984c-28.3648 0-51.4048 23.04-51.4048 51.4048s23.04 51.4048 51.4048 51.4048c28.3648 0 51.4048-23.04 51.4048-51.4048s-23.0912-51.4048-51.4048-51.4048z"
                    fill="currentColor" p-id="13861"></path>
              </svg>
              标签
              <span class="ml-3 px-3 py-1 text-sm font-medium bg-rose-100 text-rose-800 rounded-full dark:bg-rose-900 dark:text-rose-200">
                                {{ tags.length }} 个标签
                            </span>
            </h2>
          </div>

          <!-- 标签列表 -->
          <div class="flex flex-wrap gap-4">
            <a v-for="(tag, index) in tags" :key="index"
               @click="goTagArticleListPage(tag.id, tag.name)"
               class="group cursor-pointer inline-flex items-center px-4 py-2 text-sm font-medium text-center rounded-full
                                  bg-white border border-gray-200 text-gray-700 hover:bg-gradient-to-r hover:from-rose-50 hover:to-pink-50
                                  hover:border-rose-200 hover:text-rose-700 transition-all duration-300 shadow-sm hover:shadow-md
                                  dark:bg-gray-700 dark:border-gray-600 dark:text-gray-300 dark:hover:bg-gray-600 dark:hover:border-rose-500 dark:hover:text-rose-300">

              <!-- 标签图标 -->
              <svg class="w-4 h-4 mr-2 text-gray-400 group-hover:text-rose-500 transition-colors duration-300"
                   aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M1 8h13m0 0h5m-5 0v8M1 8v8h18V8"/>
              </svg>

              {{ tag.name }}

              <span class="inline-flex items-center justify-center w-5 h-5 ml-2 text-xs font-semibold text-white bg-gradient-to-r from-rose-400 to-pink-500 rounded-full">
                                {{ tag.articlesTotal }}
                            </span>
            </a>
          </div>

        </div>

        <!-- 空状态 -->
        <div v-if="!tags || tags.length === 0"
             class="w-full p-12 bg-gradient-to-br from-white to-gray-50 border border-gray-200 rounded-xl shadow-sm dark:from-gray-800 dark:to-gray-900 dark:border-gray-700 flex flex-col items-center justify-center text-center">
          <svg class="w-16 h-16 text-gray-400 mb-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 1v16m9-9H3m7 7V3"/>
          </svg>
          <h3 class="text-lg font-medium text-gray-900 dark:text-white mb-1">暂无标签</h3>
          <p class="text-gray-500 dark:text-gray-400">还没有创建任何标签</p>
        </div>
      </div>

      <!-- 右边侧边栏，占用一列 -->
      <aside class="lg:col-span-1">
        <div class="sticky top-[5.5rem] space-y-6">
          <!-- 博主信息 -->
          <UserInfoCard></UserInfoCard>

          <!-- 分类 -->
          <CategoryListCard></CategoryListCard>
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
import CategoryListCard from '@/layouts/frontend/components/CategoryListCard.vue'
import ScrollToTopButton from '@/layouts/frontend/components/ScrollToTopButton.vue'
import { getTagList } from '@/api/frontend/tag'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 所有标签
const tags = ref([])
getTagList({}).then((res) => {
  if (res.success) {
    tags.value = res.data
  }
})

// 跳转标签文章列表页
const goTagArticleListPage = (id, name) => {
  // 跳转时通过 query 携带参数（标签 ID、标签名称）
  router.push({ path: '/tag/article/list', query: { id, name } })
}
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

  .flex-wrap {
    flex-direction: column;
  }
}
</style>



