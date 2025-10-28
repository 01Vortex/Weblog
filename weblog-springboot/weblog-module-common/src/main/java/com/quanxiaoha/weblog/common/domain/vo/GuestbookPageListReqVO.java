package com.quanxiaoha.weblog.common.domain.vo;

import lombok.Data;

/**
 * 留言板分页查询请求参数
 * 
 * @author quanxiaoha
 * @since 2024-01-15
 */
@Data
public class GuestbookPageListReqVO {
    
    /**
     * 当前页码
     */
    private Long current = 1L;
    
    /**
     * 每页大小
     */
    private Long size = 10L;
}
