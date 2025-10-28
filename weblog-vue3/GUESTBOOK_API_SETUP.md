# 留言板API接口配置说明

## 当前状态
目前留言板使用模拟数据运行，你可以看到完整的UI效果。要启用真实的API接口，请按照以下步骤操作：

## 后端配置步骤

### 1. 创建数据库表
执行 `weblog-springboot/guestbook_table.sql` 文件中的SQL语句来创建留言板表。

### 2. 启动Spring Boot项目
确保你的Spring Boot后端项目在 `http://localhost:8080` 运行。

### 3. 验证API接口
访问以下接口确认后端正常运行：
- `GET http://localhost:8080/guestbook/page?current=1&size=10`
- `POST http://localhost:8080/guestbook/submit`
- `POST http://localhost:8080/guestbook/like/{messageId}`

## 前端配置步骤

### 1. 启用真实API
在 `src/pages/frontend/guestbook.vue` 文件中，找到以下三个函数：
- `loadMessages()` - 加载留言列表
- `submitMessage()` - 提交留言
- `handleLike()` - 点赞留言

### 2. 取消注释真实API调用
将每个函数中被注释的真实API调用代码取消注释，并注释掉模拟数据部分。

例如，在 `loadMessages()` 函数中：
```javascript
// 注释掉模拟数据部分
/*
await new Promise(resolve => setTimeout(resolve, 800))
const mockMessages = [...]
messages.value = mockMessages
*/

// 取消注释真实API调用
const response = await getGuestbookPageList({
    current: 1,
    size: 10
})
```

### 3. 重启前端项目
```bash
npm run dev
```

## API接口说明

### 1. 分页查询留言列表
- **接口**: `GET /guestbook/page`
- **参数**: 
  - `current`: 当前页码（默认1）
  - `size`: 每页大小（默认10）
- **返回**: 分页的留言列表

### 2. 提交留言
- **接口**: `POST /guestbook/submit`
- **参数**:
  - `nickname`: 昵称（必填，最多20字符）
  - `email`: 邮箱（必填，格式验证）
  - `content`: 留言内容（必填，最多500字符）
  - `parentId`: 父留言ID（可选，用于回复）

### 3. 点赞留言
- **接口**: `POST /guestbook/like/{messageId}`
- **参数**: `messageId` - 留言ID

### 4. 回复留言
- **接口**: `POST /guestbook/reply`
- **参数**: 同提交留言，但需要指定 `parentId`

### 5. 获取统计信息
- **接口**: `GET /guestbook/stats`
- **返回**: 留言总数、今日留言数等统计信息

## 数据库表结构

```sql
CREATE TABLE `t_guestbook` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '留言ID',
  `nickname` varchar(20) NOT NULL COMMENT '昵称',
  `email` varchar(100) NOT NULL COMMENT '邮箱',
  `content` varchar(500) NOT NULL COMMENT '留言内容',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父留言ID（用于回复）',
  `like_count` int(11) DEFAULT '0' COMMENT '点赞数',
  `is_admin` tinyint(1) DEFAULT '0' COMMENT '是否管理员留言',
  `is_audited` tinyint(1) DEFAULT '1' COMMENT '是否已审核',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='留言板表';
```

## 注意事项

1. **数据验证**: 后端已添加完整的数据验证
2. **安全防护**: 已添加基本的SQL注入防护
3. **分页查询**: 支持分页和排序
4. **审核机制**: 预留了审核字段，可根据需要启用
5. **软删除**: 使用软删除机制，数据不会真正删除

## 故障排除

### 1. 404错误
- 检查Spring Boot项目是否启动
- 确认端口号是否正确（默认8080）
- 检查API路径是否正确

### 2. 数据库连接错误
- 检查数据库连接配置
- 确认数据库表是否创建成功
- 检查数据库用户权限

### 3. 跨域问题
- 检查Spring Boot的跨域配置
- 确认前端代理配置是否正确

## 扩展功能

后续可以考虑添加的功能：
- 留言审核机制
- 敏感词过滤
- 邮件通知
- 留言搜索
- 留言置顶
- 表情支持
- 图片上传
