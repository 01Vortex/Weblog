package com.quanxiaoha.weblog.web.model.vo.wiki;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024-09-15 14:07
 * @description: 知识库目录
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindWikiCatalogListReqVO {

    @NotNull(message = "知识库 ID 不能为空")
    private Long id;

}
