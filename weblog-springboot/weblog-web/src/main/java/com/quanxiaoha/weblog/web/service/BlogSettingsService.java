package com.quanxiaoha.weblog.web.service;

import com.quanxiaoha.weblog.common.utils.Response;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024-09-15 14:03
 * @description: 博客设置
 **/
public interface BlogSettingsService {
    /**
     * 获取博客设置信息
     * @return
     */
    Response findDetail();
}
