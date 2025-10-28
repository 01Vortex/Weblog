package com.quanxiaoha.weblog.web.model.vo.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024-09-15 14:07
 * @description: 查询评论
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCommentListRspVO {

    /**
     * 总评论数
     */
    private Integer total;

    /**
     * 评论集合
     */
    private List<FindCommentItemRspVO> comments;

}
