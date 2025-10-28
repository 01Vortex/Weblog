<!-- src/components/AiAssistant.vue -->
<template>
  <div class="ai-assistant-container">
    <!-- 展开/收起按钮 -->
    <button class="toggle-button" @click="toggleAssistant" v-if="!isExpanded" >
      <span class="button-text">{{ isExpanded ? '' : '🤖' }}</span>
      <span class="pulse-dot" v-if="!isExpanded && hasNewMessage"></span>
    </button>

    <!-- 新增聊天气泡 -->
    <div class="chat-bubble" v-if="!isExpanded && showBubble">
      <div class="bubble-content">hi,我是博客助手</div>
      <div class="bubble-arrow"></div>
    </div>

    <!-- AI 助手对话窗口 -->
    <div v-if="isExpanded" class="chat-window">
      <div class="chat-header">
        <h3>AI 智能助手</h3>
        <button class="close-btn" @click="toggleAssistant">×</button>
      </div>

      <div class="chat-messages" ref="messagesContainer">
        <div
            v-for="(msg, index) in messages"
            :key="index"
            :class="['message', msg.role === 'user' ? 'user-message' : 'assistant-message']"
        >
          <div class="message-content" v-html="renderMarkdown(msg.content)"></div>
          <div class="message-time">{{ formatTime(msg.timestamp) }}</div>
        </div>

        <!-- 加载指示器 -->
        <div v-if="isLoading" class="message assistant-message typing-indicator">
          <div class="typing">
            <span></span>
            <span></span>
            <span></span>
          </div>
        </div>
      </div>

      <div class="input-area">
        <input
            v-model="userInput"
            @keyup.enter="sendMessage"
            placeholder="输入问题，按回车发送..."
            :disabled="isLoading"
        />
        <button
            @click="sendMessage"
            :disabled="isLoading || !userInput.trim()"
            class="send-button"
        >
          <svg v-if="!isLoading" width="16" height="16" viewBox="0 0 16 16">
            <path fill="currentColor" d="M1.426 1.924l12.15 5.293a1 1 0 0 1 0 1.838L1.426 14.076A1 1 0 0 1 0 13.17V2.83A1 1 0 0 1 1.426 1.924z"/>
          </svg>
          <div v-else class="loading-spinner"></div>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, nextTick } from 'vue'
import { marked } from 'marked'

// 控制助手展开/收起状态
const isExpanded = ref(false)
const hasNewMessage = ref(false)
const messagesContainer = ref(null)

const showBubble = ref(true)  // 控制聊天气泡显示

const toggleAssistant = () => {
  isExpanded.value = !isExpanded.value
  if (isExpanded.value) {
    hasNewMessage.value = false
    scrollToBottom()
  }
}

// 添加 Markdown 渲染函数
const renderMarkdown = (content) => {
  if (!content) return ''
  return marked.parse(content)
}




// 聊天消息列表
const messages = reactive([])

// 用户输入内容
const userInput = ref('')

// 加载状态
const isLoading = ref(false)

// 格式化时间
const formatTime = (timestamp) => {
  return new Date(timestamp).toLocaleTimeString('zh-CN', {
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 滚动到底部
const scrollToBottom = async () => {
  await nextTick()
  if (messagesContainer.value) {
    messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
  }
}

// 发送消息到后端 API
const sendMessage = async () => {
  if (!userInput.value.trim() || isLoading.value) return

  // 添加用户消息到聊天记录
  const userMessage = {
    role: 'user',
    content: userInput.value,
    timestamp: Date.now()
  }
  messages.push(userMessage)

  // 清空输入框并设置加载状态
  const question = userInput.value
  userInput.value = ''
  isLoading.value = true

  // 滚动到底部
  scrollToBottom()

  try {
    // 调用后端接口
    const response = await fetch('api/api/ai/ask', {  // 修复了错误的API路径
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ question })
    })

    const data = await response.json()

    // 添加 AI 回复到聊天记录
    const aiMessage = {
      role: 'assistant',
      content: data.answer || '抱歉，我没有理解您的问题。',
      timestamp: Date.now()
    }
    messages.push(aiMessage)

    // 如果窗口未展开，显示新消息提醒
    if (!isExpanded.value) {
      hasNewMessage.value = true
    }
  } catch (error) {
    console.error('Error calling AI service:', error)
    const errorMessage = {
      role: 'assistant',
      content: 'AI 服务暂时不可用，请稍后再试。',
      timestamp: Date.now()
    }
    messages.push(errorMessage)
  } finally {
    isLoading.value = false
    scrollToBottom()
  }
}

// 初始化欢迎消息
onMounted(() => {
  messages.push({
    role: 'assistant',
    content: '您好！我是您的AI助手，有什么我可以帮您的吗？',
    timestamp: Date.now()
  })
})
</script>

<style scoped>
.message-content :deep(pre) {
  background-color: #f6f8fa;
  border-radius: 6px;
  padding: 12px;
  overflow-x: auto;
  white-space: pre-wrap;
  word-wrap: break-word;
  line-height: 1.4;
}

.ai-assistant-container {
  position: fixed;
  bottom: 150px;
  right: 20px;
  z-index: 1000;
  font-size: 13px; /* 添加全局基础字体大小 */
}

.toggle-button {
  background: linear-gradient(135deg, #409eff, #3685ff);
  color: white;
  border: none;
  padding: 12px 20px;
  border-radius: 30px;
  cursor: pointer;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
  display: flex;
  align-items: center;
  gap: 8px;
  transition: all 0.3s ease;
  position: relative;
}

.toggle-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(64, 158, 255, 0.4);
}

.pulse-dot {
  width: 10px;
  height: 10px;
  background-color: #ff4d4f;
  border-radius: 50%;
  animation: pulse 1.5s infinite;
}

@keyframes pulse {
  0% { transform: scale(0.95); box-shadow: 0 0 0 0 rgba(255, 77, 79, 0.7); }
  70% { transform: scale(1); box-shadow: 0 0 0 10px rgba(255, 77, 79, 0); }
  100% { transform: scale(0.95); box-shadow: 0 0 0 0 rgba(255, 77, 79, 0); }
}

.chat-window {
  width: 380px;
  height: 550px;
  background: white;
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  margin-top: 12px;
  overflow: hidden;
  animation: slideUp 0.3s ease;
}

@keyframes slideUp {
  from { transform: translateY(20px); opacity: 0; }
  to { transform: translateY(0); opacity: 1; }
}

.chat-header {
  background: linear-gradient(135deg, #409eff, #3685ff);
  color: white;
  padding: 16px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.chat-header h3 {
  margin: 0;
  font-size: 16px;
  font-weight: 500;
}

.close-btn {
  background: none;
  border: none;
  color: white;
  font-size: 24px;
  cursor: pointer;
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  transition: background 0.2s;
}

.close-btn:hover {
  background: rgba(255, 255, 255, 0.2);
}

.chat-messages {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #f8fafc;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.message {
  max-width: 80%;
  padding: 12px 16px;
  border-radius: 18px;
  position: relative;
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(5px); }
  to { opacity: 1; transform: translateY(0); }
}

.user-message {
  background: linear-gradient(135deg, #409eff, #3685ff);
  color: white;
  margin-left: auto;
  border-bottom-right-radius: 4px;
}

.assistant-message {
  background-color: white;
  color: #333;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  border-bottom-left-radius: 4px;
}

.message-content {
  line-height: 1.5;
  word-wrap: break-word;
}

.message-time {
  font-size: 11px;
  margin-top: 6px;
  opacity: 0.7;
  text-align: right;
}

.typing-indicator {
  background-color: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  border-bottom-left-radius: 4px;
  padding: 12px 16px;
}

.typing {
  display: flex;
  align-items: center;
  height: 17px;
}

.typing span {
  height: 8px;
  width: 8px;
  float: left;
  margin: 0 1px;
  background-color: #9E9EA1;
  display: block;
  border-radius: 50%;
  opacity: 0.4;
}

.typing span:nth-of-type(1) {
  animation: typing 1s infinite;
}

.typing span:nth-of-type(2) {
  animation: typing 1s infinite 0.2s;
}

.typing span:nth-of-type(3) {
  animation: typing 1s infinite 0.4s;
}

@keyframes typing {
  0% { transform: translateY(0px); }
  50% { transform: translateY(-5px); }
  100% { transform: translateY(0px); }
}

.input-area {
  display: flex;
  padding: 15px;
  border-top: 1px solid #eee;
  background: white;
}

.input-area input {
  flex: 1;
  padding: 12px 16px;
  border: 1px solid #ddd;
  border-radius: 24px;
  margin-right: 10px;
  outline: none;
  transition: border-color 0.2s;
}

.input-area input:focus {
  border-color: #409eff;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.send-button {
  background: linear-gradient(135deg, #409eff, #3685ff);
  color: white;
  border: none;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
}

.send-button:hover:not(:disabled) {
  transform: scale(1.05);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
}

.send-button:disabled {
  background: #a0cfff;
  cursor: not-allowed;
}

.loading-spinner {
  width: 16px;
  height: 16px;
  border: 2px solid transparent;
  border-top-color: white;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

/* 新增聊天气泡样式 */
.chat-bubble {
  position: absolute;
  bottom: 60px;
  right: 0;
  background: white;
  border-radius: 18px;
  padding: 12px 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  animation: fadeIn 0.3s ease;
  white-space: nowrap;
  /* 添加以下样式使气泡更明显 */
  z-index: 1001;
}

.bubble-content {
  font-size: 14px;
  color: #333;
}

.bubble-arrow {
  position: absolute;
  bottom: -6px;
  right: 30px;
  width: 0;
  height: 0;
  border-left: 8px solid transparent;
  border-right: 8px solid transparent;
  border-top: 8px solid white;
}

</style>
