package com.quanxiaoha.weblog.admin.model.vo.guestbook;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024-10-28
 * @description: 批量删除留言
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "批量删除留言 VO")
public class BatchDeleteGuestbookReqVO {

    @NotEmpty(message = "留言 ID 列表不能为空")
    private List<Long> ids;
}

