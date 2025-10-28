<template>
  <Header></Header>

  <!-- 主内容区域 -->
  <main class="container max-w-screen-xl mx-auto px-4 md:px-6 py-8">
    <!-- grid 表格布局，分为 4 列 -->
    <div class="grid grid-cols-1 lg:grid-cols-4 gap-8">
      <!-- 左边栏，占用 3 列 -->
      <div class="lg:col-span-3 mb-6">
        <!-- 归档列表 -->
        <div v-for="(archive, index) in archives" :key="index"
             class="p-6 mb-6 border border-gray-100 rounded-xl bg-gradient-to-br from-white to-gray-50 shadow-sm hover:shadow-md transition-shadow duration-300 dark:from-gray-800 dark:to-gray-900 dark:border-gray-700">

          <!-- 月份标题 -->
          <div class="flex items-center mb-4 pb-3 border-b border-gray-100 dark:border-gray-700">
            <div class="w-2 h-8 bg-gradient-to-b from-blue-400 to-purple-500 rounded-full mr-3"></div>
            <time class="text-xl font-bold text-gray-800 dark:text-white tracking-tight">
              {{ archive.month }}
            </time>
            <span class="ml-3 px-2.5 py-0.5 text-xs font-medium bg-blue-100 text-blue-800 rounded-full dark:bg-blue-900 dark:text-blue-200">
                            {{ archive.articles.length }} 篇
                        </span>
          </div>

          <!-- 文章列表 -->
          <ol class="space-y-4">
            <li v-for="(article, index2) in archive.articles" :key="index2">
              <a @click="goArticleDetailPage(article.id)"
                 class="group flex flex-col sm:flex-row items-start sm:items-center p-4 rounded-lg hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors duration-200">

                <!-- 文章封面 -->
                <div class="relative mb-3 sm:mb-0 sm:mr-4 flex-shrink-0">
                  <img class="w-32 h-20 object-cover rounded-lg shadow-sm group-hover:shadow-md transition-shadow duration-300"
                       :src="article.cover || 'https://placehold.co/128x80/e2e8f0/64748b?text=Cover'"
                       :alt="article.title"/>
                  <div class="absolute inset-0 bg-gradient-to-t from-black/20 to-transparent rounded-lg opacity-0 group-hover:opacity-100 transition-opacity duration-300"></div>
                </div>

                <!-- 文章信息 -->
                <div class="flex-grow min-w-0">
                  <h2 class="text-lg font-semibold text-gray-900 dark:text-white group-hover:text-blue-600 dark:group-hover:text-blue-400 transition-colors duration-200 line-clamp-2">
                    {{ article.title }}
                  </h2>

                  <div class="flex flex-wrap items-center mt-2 gap-3 text-sm">
                                        <span class="inline-flex items-center text-gray-500 dark:text-gray-400">
                                            <svg class="w-4 h-4 mr-1.5 text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                      d="M5 1v3m5-3v3m5-3v3M1 7h18M5 11h10M2 3h16a1 1 0 0 1 1 1v14a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1Z" />
                                            </svg>
                                            {{ formatDate(article.createDate) }}
                                        </span>

                    <span class="inline-flex items-center text-gray-500 dark:text-gray-400">
                                            <svg class="w-4 h-4 mr-1.5 text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                      d="M4 12.25V1m3.258 6.116a8.97 8.97 0 0 1 6.694 6.694M5 12H4m4 8h4M5 12a8.963 8.963 0 0 1-4-2m4 2v7m6.75-7a8.97 8.97 0 0 1 6.692 6.692M15 12.75h-2.25M15 12a8.966 8.966 0 0 1 4-2m0 0v7" />
                                            </svg>
                                            {{ article.category?.name || '未分类' }}
                                        </span>

                    <span class="inline-flex items-center text-gray-500 dark:text-gray-400">
                                            <svg class="w-4 h-4 mr-1.5 text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                      d="M13.408 7.5h.01M7.219 7.5a3.727 3.727 0 1 1 7.455-.001 3.727 3.727 0 0 1-7.455.001Zm8.814 3.727a3 3 0 0 1-6 0 3 3 0 0 1 6 0ZM1 11.243V19h18v-7.757M1 11.243a1 1 0 0 1 1-1h16a1 1 0 0 1 1 1v.757H1v-.757Z" />
                                            </svg>
                                            {{ article.views || 0 }} 阅读
                                        </span>
                  </div>

                  <p class="mt-2 text-sm text-gray-600 dark:text-gray-300 line-clamp-2">
                    {{ article.summary || '暂无摘要...' }}
                  </p>
                </div>
              </a>
            </li>
          </ol>
        </div>

        <!-- 分页 -->
        <nav aria-label="文章分页" class="mt-12 flex justify-center" v-if="pages > 1">
          <ul class="flex items-center -space-x-px h-12 text-sm font-medium">
            <!-- 上一页 -->
            <li>
              <a @click="getArchives(current - 1)"
                 class="flex items-center justify-center px-4 h-12 ml-0 rounded-l-lg border border-gray-300 bg-white text-gray-500 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                 :class="[current > 1 ? 'cursor-pointer' : 'cursor-not-allowed opacity-50']">
                <svg class="w-3.5 h-3.5 mr-2" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
                  <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 5H1m0 0 4 4M1 5l4-4"/>
                </svg>
                上一页
              </a>
            </li>

            <!-- 页码 -->
            <li v-for="pageNo in getVisiblePages()" :key="pageNo">
              <a @click="getArchives(pageNo)"
                 class="flex items-center justify-center px-4 h-12 border border-gray-300 bg-white text-gray-500 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                 :class="[pageNo === current
                                    ? 'z-10 text-blue-600 bg-blue-50 border-blue-300 hover:bg-blue-100 hover:text-blue-700 dark:border-gray-700 dark:bg-gray-700 dark:text-white'
                                    : '']">
                {{ pageNo }}
              </a>
            </li>

            <!-- 下一页 -->
            <li>
              <a @click="getArchives(current + 1)"
                 class="flex items-center justify-center px-4 h-12 rounded-r-lg border border-gray-300 bg-white text-gray-500 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                 :class="[current < pages ? 'cursor-pointer' : 'cursor-not-allowed opacity-50']">
                下一页
                <svg class="w-3.5 h-3.5 ml-2" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
                  <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 5h12m0 0L9 1m4 4L9 9"/>
                </svg>
              </a>
            </li>
          </ul>
        </nav>

      </div>

      <!-- 右边侧边栏，占用一列 -->
      <aside class="lg:col-span-1">
        <div class="sticky top-[5.5rem] space-y-6">
          <!-- 博主信息 -->
          <UserInfoCard></UserInfoCard>

          <!-- 分类 -->
          <CategoryListCard></CategoryListCard>

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
import { getArchivePageList } from '@/api/frontend/archive'
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 文章归档
const archives = ref([])
// 当前页码
const current = ref(1)
// 每页显示的文章数
const size = ref(10)
// 总文章数
const total = ref(0)
// 总共多少页
const pages = ref(0)

// 格式化日期
const formatDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

// 计算显示的页码范围
const getVisiblePages = computed(() => {
  return () => {
    const delta = 2
    const range = []
    const rangeWithDots = []

    for (let i = Math.max(2, current.value - delta); i <= Math.min(pages.value - 1, current.value + delta); i++) {
      range.push(i)
    }

    if (pages.value > 1) {
      rangeWithDots.push(1)
    }

    if (range[0] > 2) {
      rangeWithDots.push('...')
    }

    rangeWithDots.push(...range)

    if (range[range.length - 1] < pages.value - 1) {
      rangeWithDots.push('...')
    }

    if (pages.value > 1) {
      rangeWithDots.push(pages.value)
    }

    return rangeWithDots
  }
})

function getArchives(currentNo) {
  // 上下页是否能点击判断
  if (currentNo < 1 || (pages.value > 0 && currentNo > pages.value)) return
  // 调用分页接口渲染数据
  getArchivePageList({current: currentNo, size: size.value}).then((res) => {
    if (res.success) {
      archives.value = res.data
      current.value = res.current
      size.value = res.size
      total.value = res.total
      pages.value = res.pages
    }
  })
}

getArchives(current.value)

// 跳转文章详情页
const goArticleDetailPage = (articleId) => {
  router.push('/article/' + articleId)
}
</script>

<style scoped>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

/* 为分页添加省略号样式 */
li a[aria-label="ellipsis"] {
  cursor: default;
  pointer-events: none;
  color: #9ca3af;
}

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
}
</style>



