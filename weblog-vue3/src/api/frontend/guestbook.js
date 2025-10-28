import request from '@/axios'

/**
 * 获取留言板分页列表
 * @param {Object} params - 查询参数
 * @param {number} params.current - 当前页码
 * @param {number} params.size - 每页大小
 * @returns {Promise} 返回留言板分页数据
 */
export function getGuestbookPageList(params) {
    return request({
        url: '/guestbook/page',
        method: 'get',
        params
    })
}

/**
 * 提交留言
 * @param {Object} data - 留言数据
 * @param {string} data.nickname - 昵称
 * @param {string} data.email - 邮箱
 * @param {string} data.content - 留言内容
 * @returns {Promise} 返回提交结果
 */
export function submitGuestbookMessage(data) {
    return request({
        url: '/guestbook/submit',
        method: 'post',
        data
    })
}

/**
 * 点赞留言
 * @param {number} messageId - 留言ID
 * @returns {Promise} 返回点赞结果
 */
export function likeGuestbookMessage(messageId) {
    return request({
        url: `/guestbook/like/${messageId}`,
        method: 'post'
    })
}

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

/**
 * 回复留言
 * @param {Object} data - 回复数据
 * @param {number} data.messageId - 原留言ID
 * @param {string} data.nickname - 回复者昵称
 * @param {string} data.email - 回复者邮箱
 * @param {string} data.content - 回复内容
 * @returns {Promise} 返回回复结果
 */
export function replyGuestbookMessage(data) {
    return request({
        url: '/guestbook/reply',
        method: 'post',
        data
    })
}

/**
 * 获取留言的回复列表
 * @param {number} parentId - 父留言ID
 * @returns {Promise} 返回回复列表
 */
export function getGuestbookReplies(parentId) {
    return request({
        url: `/guestbook/replies/${parentId}`,
        method: 'get'
    })
}

/**
 * 获取留言板统计信息
 * @returns {Promise} 返回统计信息
 */
export function getGuestbookStats() {
    return request({
        url: '/guestbook/stats',
        method: 'get'
    })
}
