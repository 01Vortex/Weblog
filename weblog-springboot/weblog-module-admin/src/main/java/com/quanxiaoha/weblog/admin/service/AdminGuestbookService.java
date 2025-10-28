package com.quanxiaoha.weblog.admin.service;

import com.quanxiaoha.weblog.admin.model.vo.guestbook.*;
import com.quanxiaoha.weblog.common.utils.Response;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024-10-28
 * @description: 留言板管理
 **/
public interface AdminGuestbookService {

    /**
     * 查询留言分页数据
     * @param findGuestbookPageListReqVO
     * @return
     */
    Response findGuestbookPageList(FindGuestbookPageListReqVO findGuestbookPageListReqVO);

    /**
     * 删除留言
     * @param deleteGuestbookReqVO
     * @return
     */
    Response deleteGuestbook(DeleteGuestbookReqVO deleteGuestbookReqVO);

    /**
     * 批量删除留言
     * @param batchDeleteGuestbookReqVO
     * @return
     */
    Response batchDeleteGuestbook(BatchDeleteGuestbookReqVO batchDeleteGuestbookReqVO);

    /**
     * 更新留言
     * @param updateGuestbookReqVO
     * @return
     */
    Response updateGuestbook(UpdateGuestbookReqVO updateGuestbookReqVO);

    /**
     * 查询留言详情
     * @param findGuestbookDetailReqVO
     * @return
     */
    Response findGuestbookDetail(FindGuestbookDetailReqVO findGuestbookDetailReqVO);
}

