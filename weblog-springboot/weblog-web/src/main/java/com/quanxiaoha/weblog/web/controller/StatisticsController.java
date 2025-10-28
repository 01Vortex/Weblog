package com.quanxiaoha.weblog.web.controller;

import com.quanxiaoha.weblog.common.aspect.ApiOperationLog;
import com.quanxiaoha.weblog.common.utils.Response;
import com.quanxiaoha.weblog.web.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2025-03-27 08:01
 * @description: 统计信息
 **/
@RestController
@RequestMapping("/statistics")
@Api(tags = "统计信息")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @PostMapping("/info")
    @ApiOperation(value = "前台获取统计信息")
    @ApiOperationLog(description = "前台获取统计信息")
    public Response findInfo() {
        return statisticsService.findInfo();
    }


}
