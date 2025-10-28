package com.quanxiaoha.weblog.admin.model.vo.guestbook;

import com.quanxiaoha.weblog.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024-10-28
 * @description: 留言分页查询
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询留言分页数据入参 VO")
public class FindGuestbookPageListReqVO extends BasePageQuery {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 发布的起始日期
     */
    private LocalDate startDate;

    /**
     * 发布的结束日期
     */
    private LocalDate endDate;
}

