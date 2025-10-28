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
 * @description: 删除留言
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "删除留言 VO")
public class DeleteGuestbookReqVO {

    @NotNull(message = "留言 ID 不能为空")
    private Long id;
}

