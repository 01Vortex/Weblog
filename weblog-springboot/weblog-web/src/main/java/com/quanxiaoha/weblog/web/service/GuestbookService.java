package com.quanxiaoha.weblog.web.service;

import com.quanxiaoha.weblog.common.domain.dos.GuestbookDO;
import com.quanxiaoha.weblog.common.domain.vo.GuestbookPageListReqVO;
import com.quanxiaoha.weblog.common.domain.vo.GuestbookSubmitReqVO;
import com.quanxiaoha.weblog.common.domain.vo.PageVO;

import java.util.List;

/**
 * 留言板服务接口
 * 
 * @author quanxiaoha
 * @since 2024-01-15
 */
public interface GuestbookService {
    
    /**
     * 分页查询留言板列表
     * 
     * @param pageListReqVO 分页查询参数
     * @return 分页结果
     */
    PageVO<GuestbookDO> pageList(GuestbookPageListReqVO pageListReqVO);
    
    /**
     * 提交留言
     * 
     * @param submitReqVO 留言提交参数
     */
    void submit(GuestbookSubmitReqVO submitReqVO);
    
    /**
     * 点赞留言
     * 
     * @param messageId 留言ID
     */
    void like(Long messageId);
    
    /**
     * 取消点赞
     * 
     * @param messageId 留言ID
     */
    void unlike(Long messageId);
    
    /**
     * 回复留言
     * 
     * @param replyReqVO 回复参数
     */
    void reply(GuestbookSubmitReqVO replyReqVO);
    
    /**
     * 获取留言的回复列表
     * 
     * @param parentId 父留言ID
     * @return 回复列表
     */
    List<GuestbookDO> getReplies(Long parentId);
    
    /**
     * 获取留言板统计信息
     * 
     * @return 统计信息
     */
    Object getStats();
}

