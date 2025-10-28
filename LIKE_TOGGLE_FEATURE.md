# 点赞切换功能实现说明

## 功能描述
实现点赞/取消点赞切换功能：
- 第一次点击 → 点赞（❤️ 实心红色，点赞数+1）
- 第二次点击 → 取消点赞（🤍 空心灰色，点赞数-1）

## 实现方式

### 后端实现

#### 1. Service层 - `GuestbookServiceImpl.java`
新增 `unlike()` 方法：
```java
@Transactional(rollbackFor = Exception.class)
public void unlike(Long messageId) {
    GuestbookDO guestbookDO = guestbookMapper.selectById(messageId);
    if (guestbookDO == null) {
        throw new RuntimeException("留言不存在");
    }
    
    // 减少点赞数，最小为0
    int currentLikeCount = guestbookDO.getLikeCount() != null ? guestbookDO.getLikeCount() : 0;
    guestbookDO.setLikeCount(Math.max(0, currentLikeCount - 1));
    guestbookDO.setUpdateTime(LocalDateTime.now());
    
    guestbookMapper.updateById(guestbookDO);
    log.info("取消点赞成功，留言ID：{}，当前点赞数：{}", messageId, guestbookDO.getLikeCount());
}
```

#### 2. Service接口 - `GuestbookService.java`
添加方法定义：
```java
void unlike(Long messageId);
```

#### 3. Controller层 - `GuestbookController.java`
新增API端点：
```java
@ApiOperation("取消点赞")
@PostMapping("/unlike/{messageId}")
public Response<String> unlike(@PathVariable Long messageId) {
    log.info("取消点赞，messageId：{}", messageId);
    
    try {
        guestbookService.unlike(messageId);
        return Response.success("取消点赞成功");
    } catch (Exception e) {
        log.error("取消点赞失败", e);
        return Response.fail("取消点赞失败");
    }
}
```

### 前端实现

#### 1. API层 - `guestbook.js`
新增取消点赞API：
```javascript
/**
 * 取消点赞
 * @param {number} messageId - 留言ID
 * @returns {Promise} 返回取消点赞结果
 */
export function unlikeGuestbookMessage(messageId) {
    return request({
        url: `/guestbook/unlike/${messageId}`,
        method: 'post'
    })
}
```

#### 2. 组件层 - `guestbook.vue`

**状态管理**：
```javascript
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
```

**点赞逻辑**：
```javascript
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
            }
        } else {
            // 点赞
            const response = await likeGuestbookMessage(messageId)
            if (response.success) {
                ElMessage.success('点赞成功')
                message.likeCount = (message.likeCount || 0) + 1
                likedMessages.value.add(messageId)
                saveLikedMessages()
            }
        }
    } catch (error) {
        console.error('点赞操作失败:', error)
        ElMessage.error('操作失败')
    }
}
```

**UI样式（动态类名）**：
```vue
<button @click="likeMessage(message.id)" 
    :class="[
        'group/btn flex items-center gap-2 px-4 py-2 text-sm font-medium rounded-lg border hover:shadow-md transition-all duration-200',
        isLiked(message.id) 
            ? 'text-red-500 bg-red-50 border-red-300 dark:bg-red-900/20 dark:text-red-400 dark:border-red-700' 
            : 'text-gray-600 hover:text-red-500 dark:text-gray-400 dark:hover:text-red-400 bg-white dark:bg-gray-700 border-gray-200 dark:border-gray-600 hover:border-red-300 dark:hover:border-red-700'
    ]">
    <svg class="w-5 h-5 group-hover/btn:scale-110 transition-transform" 
        :fill="isLiked(message.id) ? 'currentColor' : 'none'" 
        stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
    </svg>
    <span class="font-semibold">{{ message.likeCount || 0 }}</span>
</button>
```

## 技术亮点

### 1. LocalStorage持久化
- 使用 `localStorage` 存储用户点赞记录
- 刷新页面后点赞状态保持不变
- 跨会话保存点赞状态

### 2. 动态UI反馈
- **未点赞状态**：灰色边框，空心爱心图标
- **已点赞状态**：红色背景，实心爱心图标
- 平滑的颜色和图标过渡动画

### 3. 数据安全
- 后端使用 `Math.max(0, count - 1)` 确保点赞数不会为负数
- 数据库事务保证数据一致性

### 4. 用户体验
- 即时的视觉反馈（颜色、图标变化）
- 清晰的提示信息（"点赞成功"/"已取消点赞"）
- 本地状态与服务器同步

## API端点

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/guestbook/like/{messageId}` | 点赞留言 |
| POST | `/api/guestbook/unlike/{messageId}` | 取消点赞 |

## 数据流

```
用户点击 → 检查localStorage → 判断是否已点赞
    ↓
已点赞？
├─ 是 → 调用unlike API → 点赞数-1 → 从Set删除ID → 更新localStorage
└─ 否 → 调用like API → 点赞数+1 → 添加到Set → 更新localStorage
    ↓
更新UI → 显示成功提示
```

## 测试步骤

1. **首次点赞**：
   - 点击空心❤️
   - 图标变为实心红色❤️
   - 点赞数+1
   - 提示"点赞成功"

2. **取消点赞**：
   - 再次点击实心❤️
   - 图标变为空心灰色🤍
   - 点赞数-1
   - 提示"已取消点赞"

3. **刷新页面**：
   - 已点赞的留言保持红色实心状态
   - 未点赞的留言保持灰色空心状态

4. **清除缓存**：
   - 清除localStorage中的`guestbook_liked`
   - 所有点赞状态重置

## 注意事项

1. **浏览器兼容性**：localStorage在所有现代浏览器中都支持
2. **隐私模式**：在无痕浏览模式下，localStorage可能不持久化
3. **跨设备**：点赞状态仅在当前浏览器保存，不跨设备同步
4. **数据一致性**：前端状态与后端数据库独立，清除缓存后需重新点赞

## 未来优化方向

1. 将点赞记录保存到数据库，关联用户账号
2. 实现跨设备点赞状态同步
3. 添加点赞动画效果
4. 支持长按查看点赞用户列表

