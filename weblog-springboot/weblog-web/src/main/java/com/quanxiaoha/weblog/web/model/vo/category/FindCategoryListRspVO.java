package com.quanxiaoha.weblog.web.model.vo.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024-04-19 16:06
 * @description: 分类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCategoryListRspVO {
    private Long id;
    private String name;
    private Integer articlesTotal;
}
