package com.quanxiaoha.weblog.admin.model.vo.guestbook;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024-10-28
 * @description: 查询留言详情
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询留言详情 VO")
public class FindGuestbookDetailReqVO {

    @NotNull(message = "留言 ID 不能为空")
    private Long id;
}

