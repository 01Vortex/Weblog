package com.quanxiaoha.weblog.common.domain.vo;

import lombok.Data;

/**
 * 留言板提交请求参数
 * 
 * @author quanxiaoha
 * @since 2024-01-15
 */
@Data
public class GuestbookSubmitReqVO {
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 留言内容
     */
    private String content;
    
    /**
     * 父留言ID（用于回复）
     */
    private Long parentId;
}
