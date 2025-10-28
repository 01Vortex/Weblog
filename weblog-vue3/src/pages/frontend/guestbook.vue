<template>
    <Header></Header>

    <!-- 主内容区域 -->
    <main class="container max-w-screen-xl mx-auto px-4 md:px-6 py-8">
        <div class="grid grid-cols-4 gap-7">
            <!-- 主内容区，占用 3 列 -->
            <div class="col-span-4 md:col-span-3 mb-3">
                <!-- 主内容区域 - 左右分栏布局 -->
                <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
                    <!-- 左侧：发表留言表单 -->
                    <div class="lg:col-span-1">
                        <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-lg border border-gray-100 dark:border-gray-700 p-6 hover:shadow-xl transition-all duration-300 animate__animated animate__fadeInLeft sticky top-4">
                            <div class="flex items-center gap-3 mb-6">
                                <div class="p-2 bg-gradient-to-br from-sky-500 to-blue-600 rounded-lg">
                                    <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z"></path>
                                    </svg>
                                </div>
                                <h2 class="text-xl font-bold bg-gradient-to-r from-sky-600 to-blue-600 bg-clip-text text-transparent">发表留言</h2>
                            </div>
                            
                            <form @submit.prevent="submitMessage">
                                <!-- 用户信息输入 -->
                                <div class="space-y-4 mb-5">
                                    <div class="group">
                                        <label class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2 flex items-center gap-2">
                                            <span class="w-1.5 h-1.5 bg-sky-500 rounded-full"></span>
                                            昵称 <span class="text-red-500">*</span>
                                        </label>
                                        <div class="relative">
                                            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                                <svg class="w-4 h-4 text-gray-400 group-focus-within:text-sky-500 transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
                                                </svg>
                                            </div>
                                            <input v-model="messageForm.nickname" type="text" required
                                                class="w-full pl-10 pr-3 py-2.5 border-2 border-gray-200 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-sky-500 focus:border-sky-500 dark:bg-gray-700 dark:text-white transition-all duration-200 text-sm"
                                                placeholder="您的昵称">
                                        </div>
                                    </div>
                                    <div class="group">
                                        <label class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2 flex items-center gap-2">
                                            <span class="w-1.5 h-1.5 bg-sky-500 rounded-full"></span>
                                            邮箱 <span class="text-red-500">*</span>
                                        </label>
                                        <div class="relative">
                                            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                                <svg class="w-4 h-4 text-gray-400 group-focus-within:text-sky-500 transition-colors" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"></path>
                                                </svg>
                                            </div>
                                            <input v-model="messageForm.email" type="email" required
                                                class="w-full pl-10 pr-3 py-2.5 border-2 border-gray-200 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-sky-500 focus:border-sky-500 dark:bg-gray-700 dark:text-white transition-all duration-200 text-sm"
                                                placeholder="您的邮箱">
                                        </div>
                                    </div>
                                </div>
                                
                                <!-- 留言内容 -->
                                <div class="mb-5 group">
                                    <label class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2 flex items-center gap-2">
                                        <span class="w-1.5 h-1.5 bg-sky-500 rounded-full"></span>
                                        留言内容 <span class="text-red-500">*</span>
                                    </label>
                                    <div class="relative">
                                        <textarea v-model="messageForm.content" rows="8" required
                                            class="w-full px-3 py-2.5 border-2 border-gray-200 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-sky-500 focus:border-sky-500 dark:bg-gray-700 dark:text-white resize-none transition-all duration-200 text-sm"
                                            placeholder="分享您的想法~ "></textarea>
                                        <div class="absolute bottom-2 right-2 text-xs text-gray-400">
                                            {{ messageForm.content.length }} / 500
                                        </div>
                                    </div>
                                </div>

                                <!-- 提交按钮 -->
                                <button type="submit" :disabled="submitting"
                                    class="w-full py-3 bg-gradient-to-r from-sky-500 to-blue-600 hover:from-sky-600 hover:to-blue-700 text-white rounded-lg font-semibold disabled:opacity-50 disabled:cursor-not-allowed transition-all duration-300 shadow-lg hover:shadow-xl transform hover:-translate-y-0.5">
                                    <span class="flex items-center justify-center gap-2">
                                        <svg v-if="!submitting" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8"></path>
                                        </svg>
                                        <svg v-else class="w-5 h-5 animate-spin" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"></path>
                                        </svg>
                                        {{ submitting ? '提交中...' : '发表留言' }}
                                    </span>
                                </button>
                            </form>
                        </div>
                    </div>

                    <!-- 右侧：留言列表 - 占据大部分空间 -->
                    <div class="lg:col-span-2">
                        <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-lg border border-gray-100 dark:border-gray-700 p-6 md:p-8 animate__animated animate__fadeInRight">
                    <div class="flex items-center justify-between mb-8">
                        <div class="flex items-center gap-3">
                            <div class="p-2 bg-gradient-to-br from-purple-500 to-pink-600 rounded-lg">
                                <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 8h2a2 2 0 012 2v6a2 2 0 01-2 2h-2v4l-4-4H9a1.994 1.994 0 01-1.414-.586m0 0L11 14h4a2 2 0 002-2V6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2v4l.586-.586z"></path>
                                </svg>
                            </div>
                            <h2 class="text-2xl font-bold bg-gradient-to-r from-purple-600 to-pink-600 bg-clip-text text-transparent">全部留言</h2>
                        </div>
                        <div class="flex items-center gap-2 px-4 py-2 bg-gradient-to-r from-sky-50 to-blue-50 dark:from-gray-700 dark:to-gray-600 rounded-full">
                            <span class="text-sm font-semibold text-gray-600 dark:text-gray-300">共</span>
                            <span class="text-lg font-bold text-sky-600 dark:text-sky-400">{{ total }}</span>
                            <span class="text-sm font-semibold text-gray-600 dark:text-gray-300">条</span>
                        </div>
                    </div>
                    
                    <!-- 留言项 -->
                    <div v-if="messages.length > 0" class="space-y-6">
                        <div v-for="(message, index) in messages" :key="message.id" 
                            class="group relative bg-gradient-to-br from-gray-50 to-white dark:from-gray-700 dark:to-gray-800 rounded-2xl p-6 border-2 border-gray-100 dark:border-gray-600 hover:border-sky-200 dark:hover:border-sky-700 hover:shadow-lg transition-all duration-300 animate__animated animate__fadeInUp"
                            :style="{ animationDelay: `${index * 0.1}s` }">
                            
                            <div class="flex gap-4">
                                <!-- 头像 -->
                                <div class="flex-shrink-0">
                                    <div class="relative">
                                        <div v-if="getQQAvatar(message.email)" 
                                            class="w-10 h-10 rounded-xl overflow-hidden shadow-md transform group-hover:scale-105 transition-all duration-300">
                                            <img :src="getQQAvatar(message.email)" 
                                                :alt="message.nickname"
                                                class="w-full h-full object-cover"
                                                @error="(e) => e.target.style.display = 'none'">
                                        </div>
                                        <div v-else class="w-10 h-10 rounded-xl bg-gradient-to-br from-sky-400 via-blue-500 to-indigo-600 flex items-center justify-center text-white font-semibold text-base shadow-md transform group-hover:scale-105 transition-all duration-300">
                                            {{ message.nickname ? message.nickname.charAt(0).toUpperCase() : 'U' }}
                                        </div>
                                    </div>
                                </div>
                                
                                <!-- 留言内容 -->
                                <div class="flex-1 min-w-0">
                                    <div class="flex items-center gap-2 mb-2 flex-wrap">
                                        <span class="font-semibold text-base text-gray-900 dark:text-white">{{ message.nickname }}</span>
                                        <span v-if="isAdmin(message.email)" 
                                            class="px-2.5 py-0.5 text-xs font-semibold bg-gradient-to-r from-red-500 to-pink-500 text-white rounded-full shadow-sm">
                                            博主
                                        </span>
                                        <div class="flex items-center gap-1.5 text-xs text-gray-500 dark:text-gray-400">
                                            <svg class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                                            </svg>
                                            <span>{{ formatDate(message.createTime) }}</span>
                                        </div>
                                    </div>
                                    <div class="bg-white/50 dark:bg-gray-900/30 rounded-xl p-3 mb-3">
                                        <p class="text-sm text-gray-700 dark:text-gray-300 leading-relaxed whitespace-pre-wrap">{{ message.content }}</p>
                                    </div>
                                    
                                    <!-- 操作按钮 -->
                                    <div class="flex items-center gap-3">
                                        <button @click="likeMessage(message.id)" 
                                            :class="[
                                                'group/btn flex items-center gap-1.5 px-3 py-1.5 text-xs font-medium rounded-lg border hover:shadow-md transition-all duration-200',
                                                isLiked(message.id) 
                                                    ? 'text-red-500 bg-red-50 border-red-300 dark:bg-red-900/20 dark:text-red-400 dark:border-red-700' 
                                                    : 'text-gray-600 hover:text-red-500 dark:text-gray-400 dark:hover:text-red-400 bg-white dark:bg-gray-700 border-gray-200 dark:border-gray-600 hover:border-red-300 dark:hover:border-red-700'
                                            ]">
                                            <svg class="w-4 h-4 group-hover/btn:scale-110 transition-transform" 
                                                :fill="isLiked(message.id) ? 'currentColor' : 'none'" 
                                                stroke="currentColor" viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
                                            </svg>
                                            <span class="font-semibold">{{ formatLikeCount(message.likeCount) }}</span>
                                        </button>
                                        <button @click="showReplyForm(message)" 
                                            class="group/btn flex items-center gap-1.5 px-3 py-1.5 text-xs font-medium text-gray-600 hover:text-sky-600 dark:text-gray-400 dark:hover:text-sky-400 bg-white dark:bg-gray-700 rounded-lg border border-gray-200 dark:border-gray-600 hover:border-sky-300 dark:hover:border-sky-700 hover:shadow-md transition-all duration-200">
                                            <svg class="w-4 h-4 group-hover/btn:scale-110 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 10h10a8 8 0 018 8v2M3 10l6 6m-6-6l6-6"></path>
                                            </svg>
                                            <span>回复</span>
                                        </button>
                                    </div>

                                    <!-- 回复表单 -->
                                    <div v-if="replyingTo === message.id" class="mt-4 p-4 bg-gradient-to-br from-sky-50 to-blue-50 dark:from-gray-600 dark:to-gray-700 rounded-lg border border-sky-200 dark:border-sky-700 animate__animated animate__fadeIn">
                                        <div class="flex items-center gap-1.5 mb-2.5">
                                            <svg class="w-4 h-4 text-sky-600 dark:text-sky-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z"></path>
                                            </svg>
                                            <span class="text-sm font-semibold text-sky-700 dark:text-sky-300">回复 @{{ message.nickname }}</span>
                                        </div>
                                        <form @submit.prevent="submitReply(message.id)">
                                            <textarea v-model="replyForm.content" rows="2" required
                                                class="w-full px-3 py-2 text-sm border border-sky-200 dark:border-sky-600 rounded-lg focus:ring-2 focus:ring-sky-500 focus:border-sky-500 dark:bg-gray-700 dark:text-white mb-2.5 resize-none transition-all"
                                                placeholder="输入回复内容..."></textarea>
                                            <div class="flex justify-end gap-2">
                                                <button type="button" @click="cancelReply"
                                                    class="px-3 py-1.5 text-xs font-medium text-gray-600 hover:text-gray-800 dark:text-gray-400 dark:hover:text-gray-200 bg-white dark:bg-gray-700 rounded-lg border border-gray-300 dark:border-gray-600 hover:shadow transition-all">
                                                    取消
                                                </button>
                                                <button type="submit" :disabled="submitting"
                                                    class="px-3 py-1.5 text-xs font-medium bg-gradient-to-r from-sky-500 to-blue-600 hover:from-sky-600 hover:to-blue-700 text-white rounded-lg disabled:opacity-50 shadow-md hover:shadow-lg transition-all">
                                                    <span class="flex items-center gap-1.5">
                                                        <svg v-if="!submitting" class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8"></path>
                                                        </svg>
                                                        <svg v-else class="w-3.5 h-3.5 animate-spin" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"></path>
                                                        </svg>
                                                        {{ submitting ? '发送中...' : '发送回复' }}
                                                    </span>
                                                </button>
                                            </div>
                                        </form>
                                    </div>

                                    <!-- 回复列表 -->
                                    <div v-if="message.replies && message.replies.length > 0" class="mt-5 space-y-3">
                                        <div class="flex items-center gap-2 text-sm text-gray-500 dark:text-gray-400 mb-2">
                                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 8h2a2 2 0 012 2v6a2 2 0 01-2 2h-2v4l-4-4H9a1.994 1.994 0 01-1.414-.586m0 0L11 14h4a2 2 0 002-2V6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2v4l.586-.586z"></path>
                                            </svg>
                                            <span class="font-medium">{{ message.replies.length }} 条回复</span>
                                        </div>
                                        <div v-for="reply in message.replies" :key="reply.id"
                                            class="flex gap-2.5 pl-4 border-l-3 border-sky-200 dark:border-sky-700 py-2.5 bg-white/50 dark:bg-gray-800/50 rounded-r-xl hover:bg-sky-50/50 dark:hover:bg-gray-700/50 transition-all">
                                            <div class="flex-shrink-0">
                                                <div v-if="getQQAvatar(reply.email)" 
                                                    class="w-8 h-8 rounded-lg overflow-hidden shadow">
                                                    <img :src="getQQAvatar(reply.email)" 
                                                        :alt="reply.nickname"
                                                        class="w-full h-full object-cover"
                                                        @error="(e) => e.target.style.display = 'none'">
                                                </div>
                                                <div v-else class="w-8 h-8 rounded-lg bg-gradient-to-br from-purple-400 via-pink-500 to-red-500 flex items-center justify-center text-white text-xs font-semibold shadow">
                                                    {{ reply.nickname ? reply.nickname.charAt(0).toUpperCase() : 'U' }}
                                                </div>
                                            </div>
                                            <div class="flex-1 min-w-0">
                                                <div class="flex items-center gap-1.5 mb-1 flex-wrap">
                                                    <span class="font-semibold text-xs text-gray-900 dark:text-white">{{ reply.nickname }}</span>
                                                    <span v-if="isAdmin(reply.email)" 
                                                        class="px-2 py-0.5 text-xs font-semibold bg-gradient-to-r from-red-500 to-pink-500 text-white rounded-full">
                                                        博主
                                                    </span>
                                                    <div class="flex items-center gap-1 text-xs text-gray-500 dark:text-gray-400">
                                                        <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                                                        </svg>
                                                        <span>{{ formatDate(reply.createTime) }}</span>
                                                    </div>
                                                </div>
                                                <p class="text-xs text-gray-700 dark:text-gray-300 leading-relaxed">{{ reply.content }}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 空状态 - 精美设计 -->
                    <div v-else class="text-center py-20">
                        <div class="inline-flex items-center justify-center w-24 h-24 bg-gradient-to-br from-sky-100 to-blue-100 dark:from-gray-700 dark:to-gray-600 rounded-full mb-6">
                            <svg class="w-12 h-12 text-sky-500 dark:text-sky-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"></path>
                            </svg>
                        </div>
                        <h3 class="text-xl font-bold text-gray-700 dark:text-gray-300 mb-2">暂无留言</h3>
                        <p class="text-gray-500 dark:text-gray-400">成为第一个留言的人，分享您的想法吧~ 🎉</p>
                    </div>
                </div>

                            <!-- 分页 - 现代设计 -->
                            <nav v-if="pages > 1" aria-label="Page navigation" class="mt-8 flex justify-center">
                                <ul class="flex items-center gap-2">
                                    <!-- 上一页 -->
                                    <li>
                                        <a @click="current > 1 && getMessages(current - 1)"
                                            class="group flex items-center justify-center px-4 py-2.5 rounded-xl font-medium transition-all duration-200"
                                            :class="[current > 1 
                                                ? 'bg-white dark:bg-gray-800 text-gray-700 dark:text-gray-300 border-2 border-gray-200 dark:border-gray-600 hover:border-sky-500 dark:hover:border-sky-500 hover:shadow-md cursor-pointer' 
                                                : 'bg-gray-100 dark:bg-gray-700 text-gray-400 dark:text-gray-500 border-2 border-gray-200 dark:border-gray-600 cursor-not-allowed']">
                                            <svg class="w-5 h-5 transform group-hover:-translate-x-1 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
                                            </svg>
                                        </a>
                                    </li>
                                    
                                    <!-- 页码 -->
                                    <li v-for="pageNo in pages" :key="pageNo">
                                        <a @click="getMessages(pageNo)"
                                            class="flex items-center justify-center min-w-[44px] px-4 py-2.5 rounded-xl font-semibold cursor-pointer transition-all duration-200 transform hover:scale-105"
                                            :class="[pageNo == current 
                                                ? 'bg-gradient-to-r from-sky-500 to-blue-600 text-white shadow-lg shadow-sky-500/30' 
                                                : 'bg-white dark:bg-gray-800 text-gray-700 dark:text-gray-300 border-2 border-gray-200 dark:border-gray-600 hover:border-sky-500 dark:hover:border-sky-500 hover:shadow-md']">
                                            {{ pageNo }}
                                        </a>
                                    </li>
                                    
                                    <!-- 下一页 -->
                                    <li>
                                        <a @click="current < pages && getMessages(current + 1)"
                                            class="group flex items-center justify-center px-4 py-2.5 rounded-xl font-medium transition-all duration-200"
                                            :class="[current < pages 
                                                ? 'bg-white dark:bg-gray-800 text-gray-700 dark:text-gray-300 border-2 border-gray-200 dark:border-gray-600 hover:border-sky-500 dark:hover:border-sky-500 hover:shadow-md cursor-pointer' 
                                                : 'bg-gray-100 dark:bg-gray-700 text-gray-400 dark:text-gray-500 border-2 border-gray-200 dark:border-gray-600 cursor-not-allowed']">
                                            <svg class="w-5 h-5 transform group-hover:translate-x-1 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                                            </svg>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>

            <!-- 右侧边栏，占用 1 列 -->
            <div class="hidden md:block col-span-1">
                <!-- 用户信息卡片 -->
                <UserInfoCard></UserInfoCard>
                <!-- 分类列表卡片 -->
                <CategoryListCard></CategoryListCard>
                <!-- 标签列表卡片 -->
                <TagListCard></TagListCard>
            </div>
        </div>
    </main>

    <Footer></Footer>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import Header from '@/layouts/frontend/components/Header.vue'
import Footer from '@/layouts/frontend/components/Footer.vue'
import UserInfoCard from '@/layouts/frontend/components/UserInfoCard.vue'
import CategoryListCard from '@/layouts/frontend/components/CategoryListCard.vue'
import TagListCard from '@/layouts/frontend/components/TagListCard.vue'
import { 
    getGuestbookPageList, 
    submitGuestbookMessage, 
    likeGuestbookMessage,
    unlikeGuestbookMessage,
    replyGuestbookMessage,
    getGuestbookStats,
    getGuestbookReplies
} from '@/api/frontend/guestbook'

// 博主邮箱
const ADMIN_EMAIL = '2911130943@qq.com'

// 已点赞的留言ID列表（使用localStorage持久化）
const likedMessages = ref(new Set(JSON.parse(localStorage.getItem('guestbook_liked') || '[]')))

// 保存点赞状态到localStorage
const saveLikedMessages = () => {
    localStorage.setItem('guestbook_liked', JSON.stringify([...likedMessages.value]))
}

// 检查是否已点赞
const isLiked = (messageId) => {
    return likedMessages.value.has(messageId)
}

// 留言列表数据
const messages = ref([])
const total = ref(0)
const current = ref(1)
const size = ref(10)
const pages = ref(0)

// 统计信息
const stats = ref({
    totalCount: 0,
    todayCount: 0
})

// 留言表单
const messageForm = ref({
    nickname: '',
    email: '',
    content: '',
    avatar: '' // QQ头像
})

// 回复相关
const replyingTo = ref(null)
const replyForm = ref({
    content: ''
})

// 提交状态
const submitting = ref(false)

// 获取QQ头像
const getQQAvatar = (email) => {
    if (!email) return ''
    // 判断是否为QQ邮箱
    const qqMatch = email.match(/^(\d+)@qq\.com$/)
    if (qqMatch) {
        const qq = qqMatch[1]
        // 使用QQ头像API
        return `https://q1.qlogo.cn/g?b=qq&nk=${qq}&s=100`
    }
    return ''
}

// 判断是否为博主
const isAdmin = (email) => {
    return email === ADMIN_EMAIL
}

// 获取留言列表
const getMessages = async (pageNo = 1) => {
    console.log('=== 获取留言列表 ===', 'pageNo:', pageNo)
    if (pageNo < 1 || (pages.value > 0 && pageNo > pages.value)) {
        console.log('页码超出范围，返回')
        return
    }

    try {
        current.value = pageNo
        const params = {
            current: pageNo,
            size: size.value
        }
        console.log('请求参数:', params)
        
        const response = await getGuestbookPageList(params)
        console.log('接口响应:', response)

        if (response.success) {
            messages.value = response.data.records || []
            total.value = response.data.total || 0
            pages.value = response.data.pages || 0
            current.value = response.data.current || 1
            
            console.log('解析后的数据:', {
                messages: messages.value.length,
                total: total.value,
                pages: pages.value,
                current: current.value
            })
            
            // 为每条留言加载回复
            await loadRepliesForMessages()
        } else {
            console.error('接口返回失败:', response.message)
        }
    } catch (error) {
        console.error('获取留言列表失败:', error)
        ElMessage.error('获取留言列表失败')
    }
}

// 加载留言的回复
const loadRepliesForMessages = async () => {
    for (const message of messages.value) {
        try {
            const response = await getGuestbookReplies(message.id)
            if (response.success) {
                message.replies = response.data || []
            } else {
                message.replies = []
            }
        } catch (error) {
            console.error(`加载留言 ${message.id} 的回复失败:`, error)
            message.replies = []
        }
    }
}

// 获取统计信息
const getStats = async () => {
    try {
        const response = await getGuestbookStats()
        if (response.success) {
            stats.value = response.data || { totalCount: 0, todayCount: 0 }
        }
    } catch (error) {
        console.error('获取统计信息失败:', error)
    }
}

// 提交留言
const submitMessage = async () => {
    if (!messageForm.value.nickname || !messageForm.value.email || !messageForm.value.content) {
        ElMessage.warning('请填写完整信息')
        return
    }

    submitting.value = true
    try {
        // 准备提交数据
        const submitData = {
            nickname: messageForm.value.nickname,
            email: messageForm.value.email,
            content: messageForm.value.content
        }

        // axios拦截器已经返回了response.data，所以这里直接获取响应对象
        const response = await submitGuestbookMessage(submitData)
        console.log('提交留言响应:', response)
        
        if (response.success) {
            ElMessage.success('留言提交成功')
            // 清空表单内容，但保留昵称和邮箱
            const savedNickname = messageForm.value.nickname
            const savedEmail = messageForm.value.email
            messageForm.value = {
                nickname: savedNickname,
                email: savedEmail,
                content: '',
                avatar: getQQAvatar(savedEmail)
            }
            // 刷新列表
            await getMessages(1)
            await getStats()
        } else {
            ElMessage.error(response.message || '留言提交失败')
        }
    } catch (error) {
        console.error('提交留言失败:', error)
        ElMessage.error('留言提交失败')
    } finally {
        submitting.value = false
    }
}

// 点赞/取消点赞留言
const likeMessage = async (messageId) => {
    try {
        const message = messages.value.find(m => m.id === messageId)
        if (!message) return
        
        const liked = isLiked(messageId)
        
        if (liked) {
            // 取消点赞
            const response = await unlikeGuestbookMessage(messageId)
            if (response.success) {
                ElMessage.success('已取消点赞')
                message.likeCount = Math.max(0, (message.likeCount || 0) - 1)
                likedMessages.value.delete(messageId)
                saveLikedMessages()
            } else {
                ElMessage.error(response.message || '取消点赞失败')
            }
        } else {
            // 点赞
            const response = await likeGuestbookMessage(messageId)
            if (response.success) {
                ElMessage.success('点赞成功')
                message.likeCount = (message.likeCount || 0) + 1
                likedMessages.value.add(messageId)
                saveLikedMessages()
            } else {
                ElMessage.error(response.message || '点赞失败')
            }
        }
    } catch (error) {
        console.error('点赞操作失败:', error)
        ElMessage.error('操作失败')
    }
}

// 显示回复表单
const showReplyForm = (message) => {
    replyingTo.value = message.id
    replyForm.value.content = ''
}

// 取消回复
const cancelReply = () => {
    replyingTo.value = null
    replyForm.value.content = ''
}

// 提交回复
const submitReply = async (parentId) => {
    if (!replyForm.value.content) {
        ElMessage.warning('请输入回复内容')
        return
    }

    if (!messageForm.value.nickname || !messageForm.value.email) {
        ElMessage.warning('请先填写昵称和邮箱')
        return
    }

    submitting.value = true
    try {
        const response = await replyGuestbookMessage({
            nickname: messageForm.value.nickname,
            email: messageForm.value.email,
            content: replyForm.value.content,
            parentId: parentId
        })

        if (response.success) {
            ElMessage.success('回复成功')
            cancelReply()
            // 刷新列表
            await getMessages(current.value)
        } else {
            ElMessage.error(response.message || '回复失败')
        }
    } catch (error) {
        console.error('回复失败:', error)
        ElMessage.error('回复失败')
    } finally {
        submitting.value = false
    }
}

// 格式化点赞数
const formatLikeCount = (count) => {
    if (!count || count === 0) return 0
    if (count < 10000) return count
    
    // 10000及以上用w表示
    const wan = count / 10000
    // 保留一位小数，如果小数部分为0则不显示
    const formatted = wan % 1 === 0 ? wan : wan.toFixed(1)
    return formatted + 'w'
}

// 格式化日期
const formatDate = (dateStr) => {
    if (!dateStr) return ''
    const date = new Date(dateStr)
    const now = new Date()
    const diff = now - date
    
    // 小于1分钟
    if (diff < 60000) {
        return '刚刚'
    }
    // 小于1小时
    if (diff < 3600000) {
        return Math.floor(diff / 60000) + '分钟前'
    }
    // 小于1天
    if (diff < 86400000) {
        return Math.floor(diff / 3600000) + '小时前'
    }
    // 小于7天
    if (diff < 604800000) {
        return Math.floor(diff / 86400000) + '天前'
    }
    
    // 格式化为 YYYY-MM-DD HH:mm
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0')
    const hours = String(date.getHours()).padStart(2, '0')
    const minutes = String(date.getMinutes()).padStart(2, '0')
    
    return `${year}-${month}-${day} ${hours}:${minutes}`
}

// 页面加载时获取数据
onMounted(() => {
    getMessages()
    getStats()
})
</script>

<style scoped>
/* 自定义样式 */
.whitespace-pre-wrap {
    white-space: pre-wrap;
    word-wrap: break-word;
}

/* 平滑滚动 */
html {
    scroll-behavior: smooth;
}

/* 渐变文字效果 */
.bg-clip-text {
    -webkit-background-clip: text;
    background-clip: text;
}

/* 毛玻璃效果 */
.backdrop-blur-sm {
    backdrop-filter: blur(4px);
}

.backdrop-blur-md {
    backdrop-filter: blur(12px);
}

/* 自定义动画 */
@keyframes float {
    0%, 100% {
        transform: translateY(0px);
    }
    50% {
        transform: translateY(-10px);
    }
}

.animate-float {
    animation: float 3s ease-in-out infinite;
}

/* 按钮点击效果 */
button:active {
    transform: scale(0.98);
}

/* 输入框聚焦光晕效果 */
input:focus,
textarea:focus {
    box-shadow: 0 0 0 4px rgba(14, 165, 233, 0.1);
}

/* 深色模式优化 */
@media (prefers-color-scheme: dark) {
    input:focus,
    textarea:focus {
        box-shadow: 0 0 0 4px rgba(14, 165, 233, 0.2);
    }
}

/* 滚动条样式 */
::-webkit-scrollbar {
    width: 8px;
    height: 8px;
}

::-webkit-scrollbar-track {
    background: #f1f1f1;
    border-radius: 10px;
}

::-webkit-scrollbar-thumb {
    background: linear-gradient(180deg, #0ea5e9, #3b82f6);
    border-radius: 10px;
}

::-webkit-scrollbar-thumb:hover {
    background: linear-gradient(180deg, #0284c7, #2563eb);
}

/* 深色模式滚动条 */
.dark ::-webkit-scrollbar-track {
    background: #374151;
}

.dark ::-webkit-scrollbar-thumb {
    background: linear-gradient(180deg, #0ea5e9, #3b82f6);
}

/* 卡片悬浮效果增强 */
.group:hover .group-hover\:scale-110 {
    transform: scale(1.1);
}

.group:hover .group-hover\:rotate-3 {
    transform: rotate(3deg);
}

/* 响应式优化 */
@media (max-width: 768px) {
    .animate__animated {
        animation-duration: 0.5s;
    }
}
</style>

