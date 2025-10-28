package com.quanxiaoha.weblog.admin.model.vo.guestbook;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024-10-28
 * @description: 更新留言
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "更新留言 VO")
public class UpdateGuestbookReqVO {

    @NotNull(message = "留言 ID 不能为空")
    private Long id;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "留言内容不能为空")
    private String content;

    /**
     * 点赞数
     */
    private Integer likeCount;
}

