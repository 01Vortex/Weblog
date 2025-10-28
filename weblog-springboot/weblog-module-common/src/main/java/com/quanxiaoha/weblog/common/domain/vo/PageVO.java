package com.quanxiaoha.weblog.common.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页响应VO
 * 
 * @author quanxiaoha
 * @since 2024-01-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageVO<T> {
    
    /**
     * 当前页码
     */
    private Long current;
    
    /**
     * 每页大小
     */
    private Long size;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 总页数
     */
    private Long pages;
    
    /**
     * 数据列表
     */
    private List<T> records;
}
