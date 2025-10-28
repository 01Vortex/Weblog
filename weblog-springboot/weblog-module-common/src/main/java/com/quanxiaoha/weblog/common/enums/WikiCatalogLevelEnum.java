package com.quanxiaoha.weblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024-08-15 10:33
 * @description: 知识库目录层级
 **/
@Getter
@AllArgsConstructor
public enum WikiCatalogLevelEnum {

    // 一级目录
    ONE(1),
    // 二级目录
    TWO(2);

    private Integer value;

}
