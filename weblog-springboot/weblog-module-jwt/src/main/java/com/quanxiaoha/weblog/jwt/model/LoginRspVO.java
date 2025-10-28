package com.quanxiaoha.weblog.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2025-03-27 08:27
 * @description: 用户登录
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRspVO {

    /**
     * Token 值
     */
    private String token;

}
