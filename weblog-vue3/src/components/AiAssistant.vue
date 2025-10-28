<!-- src/components/AiAssistant.vue -->
<template>
  <div class="ai-assistant-container">
    <!-- 展开/收起按钮 - 增强版 -->
    <button class="toggle-button" @click="toggleAssistant" v-if="!isExpanded">
      <div class="button-icon">
        <span class="robot-emoji">🤖</span>
      </div>
      <span class="pulse-ring" v-if="hasNewMessage"></span>
      <span class="status-dot"></span>
    </button>

    <!-- 聊天气泡 - 增强版 -->
    <transition name="bubble-fade">
      <div class="chat-bubble" v-if="!isExpanded && showBubble" @click="toggleAssistant">
        <div class="bubble-content">
          <span class="wave-emoji">👋</span>
          <span class="bubble-text">Hi，我是智能AI助手</span>
        </div>
        <button class="bubble-close" @click.stop="showBubble = false">×</button>
        <div class="bubble-arrow"></div>
      </div>
    </transition>

    <!-- AI 助手对话窗口 - 增强版 -->
    <transition name="window-slide">
      <div v-if="isExpanded" class="chat-window">
        <!-- 头部 -->
        <div class="chat-header">
          <div class="header-left">
            <div class="avatar-wrapper">
              <span class="avatar-emoji">🤖</span>
              <span class="online-indicator"></span>
            </div>
            <div class="header-info">
              <h3>AI 智能助手</h3>
              <span class="status-text">在线</span>
            </div>
          </div>
          <button class="close-btn" @click="toggleAssistant" title="关闭">
            <svg width="20" height="20" viewBox="0 0 20 20">
              <path fill="currentColor" d="M10 8.586L2.929 1.515 1.515 2.929 8.586 10l-7.071 7.071 1.414 1.414L10 11.414l7.071 7.071 1.414-1.414L11.414 10l7.071-7.071-1.414-1.414L10 8.586z"/>
            </svg>
          </button>
        </div>

        <!-- 消息区域 -->
        <div class="chat-messages" ref="messagesContainer">
          <transition-group name="message-list">
            <div
              v-for="(msg, index) in messages"
              :key="index"
              :class="['message-wrapper', msg.role === 'user' ? 'user-wrapper' : 'assistant-wrapper']"
            >
              <div class="message-avatar" v-if="msg.role === 'assistant'">
                <span class="avatar-emoji-small">🤖</span>
              </div>
              <div :class="['message', msg.role === 'user' ? 'user-message' : 'assistant-message']">
                <div class="message-content" v-html="renderMarkdown(msg.content)"></div>
                <div class="message-time">{{ formatTime(msg.timestamp) }}</div>
              </div>
            </div>
          </transition-group>

          <!-- 加载指示器 - 增强版 -->
          <div v-if="isLoading" class="message-wrapper assistant-wrapper typing-wrapper">
            <div class="message-avatar">
              <span class="avatar-emoji-small">🤖</span>
            </div>
            <div class="message assistant-message typing-indicator">
              <div class="typing">
                <span></span>
                <span></span>
                <span></span>
              </div>
            </div>
          </div>
        </div>

        <!-- 输入区域 - 增强版 -->
        <div class="input-area">
          <div class="input-wrapper">
            <input
              v-model="userInput"
              @keyup.enter="sendMessage"
              placeholder="输入问题，按回车发送..."
              :disabled="isLoading"
              class="message-input"
            />
            <button
              @click="sendMessage"
              :disabled="isLoading || !userInput.trim()"
              class="send-button"
              :class="{ 'has-content': userInput.trim() }"
            >
              <transition name="icon-fade" mode="out-in">
                <svg v-if="!isLoading" key="send" width="20" height="20" viewBox="0 0 24 24">
                  <path fill="currentColor" d="M2.01 21L23 12 2.01 3 2 10l15 2-15 2z"/>
                </svg>
                <div v-else key="loading" class="loading-spinner"></div>
              </transition>
            </button>
          </div>
        </div>
      </div>
    </transition>
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
/* ============ 基础容器样式 ============ */
.ai-assistant-container {
  position: fixed;
  bottom: 150px;
  right: 20px;
  z-index: 1000;
  font-size: 13px;
}

/* ============ 切换按钮样式 ============ */
.toggle-button {
  position: relative;
  width: 60px;
  height: 60px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 50%;
  cursor: pointer;
  box-shadow: 0 8px 24px rgba(102, 126, 234, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  overflow: visible;
}

.toggle-button:hover {
  transform: translateY(-4px) scale(1.05);
  box-shadow: 0 12px 32px rgba(102, 126, 234, 0.5);
}

.toggle-button:active {
  transform: translateY(-2px) scale(1.02);
}

.button-icon {
  display: flex;
  align-items: center;
  justify-content: center;
}

.robot-emoji {
  font-size: 32px;
  filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.2));
}

/* 状态指示点 */
.status-dot {
  position: absolute;
  bottom: 8px;
  right: 8px;
  width: 12px;
  height: 12px;
  background: #52c41a;
  border: 2px solid white;
  border-radius: 50%;
  animation: breathe 2s ease-in-out infinite;
}

@keyframes breathe {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.6; }
}

/* 脉冲环 */
.pulse-ring {
  position: absolute;
  top: -4px;
  left: -4px;
  right: -4px;
  bottom: -4px;
  border: 3px solid #ff4d4f;
  border-radius: 50%;
  animation: pulse-ring 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}

@keyframes pulse-ring {
  0% {
    transform: scale(0.95);
    opacity: 0.8;
  }
  70% {
    transform: scale(1.1);
    opacity: 0;
  }
  100% {
    transform: scale(0.95);
    opacity: 0;
  }
}

/* ============ 聊天气泡样式 ============ */
.chat-bubble {
  position: absolute;
  bottom: 70px;
  right: 0;
  background: white;
  border-radius: 16px;
  padding: 16px 20px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
  z-index: 1001;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 1px solid rgba(102, 126, 234, 0.1);
  max-width: 260px;
}

.chat-bubble:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 32px rgba(0, 0, 0, 0.15);
}

.bubble-content {
  display: flex;
  align-items: center;
  gap: 10px;
}

.bubble-text {
  font-size: 14px;
  color: #333;
  font-weight: 500;
  line-height: 1.5;
  white-space: nowrap;
}

.wave-emoji {
  font-size: 18px;
  animation: wave 0.6s ease-in-out infinite;
}

@keyframes wave {
  0%, 100% { transform: rotate(0deg); }
  25% { transform: rotate(-15deg); }
  75% { transform: rotate(15deg); }
}

.bubble-close {
  position: absolute;
  top: -6px;
  right: -6px;
  width: 20px;
  height: 20px;
  background: #ff4d4f;
  color: white;
  border: 2px solid white;
  border-radius: 50%;
  font-size: 12px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
}

.bubble-close:hover {
  transform: scale(1.1);
  background: #ff7875;
}

.bubble-arrow {
  position: absolute;
  bottom: -8px;
  right: 24px;
  width: 0;
  height: 0;
  border-left: 10px solid transparent;
  border-right: 10px solid transparent;
  border-top: 10px solid white;
  filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.08));
}

/* ============ 聊天窗口样式 ============ */
.chat-window {
  width: 400px;
  height: 600px;
  background: white;
  border-radius: 20px;
  box-shadow: 0 16px 48px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  border: 1px solid rgba(0, 0, 0, 0.08);
}

/* 头部样式 */
.chat-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 18px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.avatar-wrapper {
  position: relative;
  width: 40px;
  height: 40px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter: blur(10px);
}

.avatar-emoji {
  font-size: 28px;
}

.online-indicator {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 12px;
  height: 12px;
  background: #52c41a;
  border: 2px solid white;
  border-radius: 50%;
  animation: breathe 2s ease-in-out infinite;
}

.header-info h3 {
  margin: 0;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 0.3px;
}

.status-text {
  font-size: 12px;
  opacity: 0.9;
  font-weight: 400;
}

.close-btn {
  background: rgba(255, 255, 255, 0.2);
  border: none;
  color: white;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
  backdrop-filter: blur(10px);
}

.close-btn:hover {
  background: rgba(255, 255, 255, 0.3);
  transform: rotate(90deg);
}

/* ============ 消息区域样式 ============ */
.chat-messages {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background: linear-gradient(to bottom, #f8f9fa 0%, #ffffff 100%);
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.chat-messages::-webkit-scrollbar {
  width: 6px;
}

.chat-messages::-webkit-scrollbar-track {
  background: transparent;
}

.chat-messages::-webkit-scrollbar-thumb {
  background: rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.chat-messages::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 0, 0, 0.2);
}

/* 消息包装器 */
.message-wrapper {
  display: flex;
  gap: 10px;
  align-items: flex-end;
}

.user-wrapper {
  flex-direction: row-reverse;
}

.assistant-wrapper {
  flex-direction: row;
}

.message-avatar {
  width: 32px;
  height: 32px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.3);
}

.avatar-emoji-small {
  font-size: 20px;
}

/* 消息气泡 */
.message {
  max-width: 75%;
  padding: 12px 16px;
  border-radius: 16px;
  position: relative;
  word-wrap: break-word;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.user-message {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-bottom-right-radius: 4px;
}

.assistant-message {
  background: white;
  color: #333;
  border-bottom-left-radius: 4px;
  border: 1px solid rgba(0, 0, 0, 0.05);
}

.message-content {
  line-height: 1.6;
  word-wrap: break-word;
}

/* Markdown 样式 */
.message-content :deep(p) {
  margin: 0 0 8px 0;
}

.message-content :deep(p:last-child) {
  margin-bottom: 0;
}

.message-content :deep(code) {
  background: rgba(0, 0, 0, 0.05);
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 0.9em;
  font-family: 'Courier New', monospace;
}

.user-message .message-content :deep(code) {
  background: rgba(255, 255, 255, 0.2);
}

.message-content :deep(pre) {
  background: #f6f8fa;
  border-radius: 8px;
  padding: 12px;
  overflow-x: auto;
  margin: 8px 0;
  border: 1px solid #e1e4e8;
}

.message-content :deep(pre code) {
  background: none;
  padding: 0;
  font-size: 13px;
  line-height: 1.5;
}

.message-content :deep(ul),
.message-content :deep(ol) {
  margin: 8px 0;
  padding-left: 20px;
}

.message-content :deep(li) {
  margin: 4px 0;
}

.message-time {
  font-size: 10px;
  margin-top: 6px;
  opacity: 0.6;
  text-align: right;
}

/* 打字指示器 */
.typing-indicator {
  min-width: 60px;
}

.typing {
  display: flex;
  align-items: center;
  gap: 4px;
  height: 20px;
}

.typing span {
  width: 8px;
  height: 8px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  animation: typing-bounce 1.4s infinite ease-in-out;
}

.typing span:nth-of-type(1) {
  animation-delay: 0s;
}

.typing span:nth-of-type(2) {
  animation-delay: 0.2s;
}

.typing span:nth-of-type(3) {
  animation-delay: 0.4s;
}

@keyframes typing-bounce {
  0%, 60%, 100% {
    transform: translateY(0);
    opacity: 0.7;
  }
  30% {
    transform: translateY(-8px);
    opacity: 1;
  }
}

/* ============ 输入区域样式 ============ */
.input-area {
  padding: 16px 20px;
  background: white;
  border-top: 1px solid rgba(0, 0, 0, 0.08);
}

.input-wrapper {
  display: flex;
  align-items: center;
  gap: 10px;
  background: #f5f7fa;
  border-radius: 24px;
  padding: 4px 4px 4px 16px;
  transition: all 0.3s;
  border: 2px solid transparent;
}

.input-wrapper:focus-within {
  background: white;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.message-input {
  flex: 1;
  border: none;
  background: transparent;
  outline: none;
  font-size: 14px;
  padding: 8px 0;
  color: #333;
}

.message-input::placeholder {
  color: #999;
}

.send-button {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: none;
  background: #e8e8e8;
  color: #999;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  flex-shrink: 0;
}

.send-button.has-content:not(:disabled) {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

.send-button:hover:not(:disabled) {
  transform: scale(1.05);
}

.send-button:active:not(:disabled) {
  transform: scale(0.95);
}

.send-button:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}

.loading-spinner {
  width: 18px;
  height: 18px;
  border: 2px solid transparent;
  border-top-color: currentColor;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

/* ============ 过渡动画 ============ */
.bubble-fade-enter-active,
.bubble-fade-leave-active {
  transition: all 0.3s ease;
}

.bubble-fade-enter-from {
  opacity: 0;
  transform: translateY(10px) scale(0.9);
}

.bubble-fade-leave-to {
  opacity: 0;
  transform: translateY(-10px) scale(0.9);
}

.window-slide-enter-active {
  animation: window-slide-in 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}

.window-slide-leave-active {
  animation: window-slide-out 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

@keyframes window-slide-in {
  from {
    opacity: 0;
    transform: translateY(20px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

@keyframes window-slide-out {
  from {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
  to {
    opacity: 0;
    transform: translateY(20px) scale(0.95);
  }
}

.message-list-enter-active {
  animation: message-enter 0.3s ease;
}

@keyframes message-enter {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.icon-fade-enter-active,
.icon-fade-leave-active {
  transition: all 0.2s ease;
}

.icon-fade-enter-from,
.icon-fade-leave-to {
  opacity: 0;
  transform: scale(0.8);
}

/* ============ 响应式设计 ============ */
@media (max-width: 768px) {
  .chat-window {
    width: 340px;
    height: 520px;
  }
  
  .chat-bubble {
    max-width: 200px;
    white-space: normal;
  }
}
</style>
