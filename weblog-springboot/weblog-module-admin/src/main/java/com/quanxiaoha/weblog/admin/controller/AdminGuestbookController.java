package com.quanxiaoha.weblog.admin.controller;

import com.quanxiaoha.weblog.admin.model.vo.guestbook.*;
import com.quanxiaoha.weblog.admin.service.AdminGuestbookService;
import com.quanxiaoha.weblog.common.aspect.ApiOperationLog;
import com.quanxiaoha.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024-10-28
 * @description: 留言板管理模块
 **/
@RestController
@RequestMapping("/admin/guestbook")
@Api(tags = "Admin 留言板管理模块")
public class AdminGuestbookController {

    @Autowired
    private AdminGuestbookService guestbookService;

    @PostMapping("/list")
    @ApiOperation(value = "查询留言分页数据")
    @ApiOperationLog(description = "查询留言分页数据")
    public Response findGuestbookPageList(@RequestBody @Validated FindGuestbookPageListReqVO findGuestbookPageListReqVO) {
        return guestbookService.findGuestbookPageList(findGuestbookPageListReqVO);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除留言")
    @ApiOperationLog(description = "删除留言")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteGuestbook(@RequestBody @Validated DeleteGuestbookReqVO deleteGuestbookReqVO) {
        return guestbookService.deleteGuestbook(deleteGuestbookReqVO);
    }

    @PostMapping("/batch/delete")
    @ApiOperation(value = "批量删除留言")
    @ApiOperationLog(description = "批量删除留言")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response batchDeleteGuestbook(@RequestBody @Validated BatchDeleteGuestbookReqVO batchDeleteGuestbookReqVO) {
        return guestbookService.batchDeleteGuestbook(batchDeleteGuestbookReqVO);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新留言")
    @ApiOperationLog(description = "更新留言")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updateGuestbook(@RequestBody @Validated UpdateGuestbookReqVO updateGuestbookReqVO) {
        return guestbookService.updateGuestbook(updateGuestbookReqVO);
    }

    @PostMapping("/detail")
    @ApiOperation(value = "查询留言详情")
    @ApiOperationLog(description = "查询留言详情")
    public Response findGuestbookDetail(@RequestBody @Validated FindGuestbookDetailReqVO findGuestbookDetailReqVO) {
        return guestbookService.findGuestbookDetail(findGuestbookDetailReqVO);
    }
}

