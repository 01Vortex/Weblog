import axios from "@/axios";

/**
 * 获取留言板分页列表
 */
export function getGuestbookPageList(data) {
    return axios.post("/admin/guestbook/list", data)
}

/**
 * 删除留言
 */
export function deleteGuestbook(id) {
    return axios.post("/admin/guestbook/delete", { id })
}

/**
 * 更新留言
 */
export function updateGuestbook(data) {
    return axios.post("/admin/guestbook/update", data)
}

/**
 * 获取留言详情
 */
export function getGuestbookDetail(id) {
    return axios.post("/admin/guestbook/detail", { id })
}

/**
 * 批量删除留言
 */
export function batchDeleteGuestbook(ids) {
    return axios.post("/admin/guestbook/batch/delete", { ids })
}

