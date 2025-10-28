package com.quanxiaoha.weblog.web.model.vo.article;

import com.quanxiaoha.weblog.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024-09-15 14:07
 * @description: 首页-文章分页
 **/
@Data
@Builder
@ApiModel(value = "首页查询文章分页 VO")
public class FindIndexArticlePageListReqVO extends BasePageQuery {
}
