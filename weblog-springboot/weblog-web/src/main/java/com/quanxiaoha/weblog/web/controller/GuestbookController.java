package com.quanxiaoha.weblog.web.controller;

import com.quanxiaoha.weblog.common.utils.Response;
import com.quanxiaoha.weblog.common.domain.dos.GuestbookDO;
import com.quanxiaoha.weblog.common.domain.vo.GuestbookPageListReqVO;
import com.quanxiaoha.weblog.common.domain.vo.GuestbookSubmitReqVO;
import com.quanxiaoha.weblog.common.domain.vo.PageVO;
import com.quanxiaoha.weblog.web.service.GuestbookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 留言板控制器
 * 
 * @author quanxiaoha
 * @since 2024-01-15
 */
@Api(tags = "留言板管理")
@RestController
@RequestMapping("/guestbook")
@Slf4j
public class GuestbookController {

    @Autowired
    private GuestbookService guestbookService;

    /**
     * 分页查询留言板列表
     */
    @ApiOperation("分页查询留言板列表")
    @GetMapping("/page")
    public Response<PageVO<GuestbookDO>> pageList(GuestbookPageListReqVO pageListReqVO) {
        log.info("=== 分页查询留言板列表 ===");
        log.info("入参：current={}, size={}", pageListReqVO.getCurrent(), pageListReqVO.getSize());
        
        try {
            PageVO<GuestbookDO> pageVO = guestbookService.pageList(pageListReqVO);
            log.info("查询结果：total={}, current={}, size={}, pages={}, records.size={}", 
                pageVO.getTotal(), pageVO.getCurrent(), pageVO.getSize(), 
                pageVO.getPages(), pageVO.getRecords() != null ? pageVO.getRecords().size() : 0);
            return Response.success(pageVO);
        } catch (Exception e) {
            log.error("分页查询留言板列表失败", e);
            return Response.fail("查询失败");
        }
    }

    /**
     * 提交留言
     */
    @ApiOperation("提交留言")
    @PostMapping("/submit")
    public Response<String> submit(@RequestBody GuestbookSubmitReqVO submitReqVO) {
        log.info("=== 提交留言 ===");
        log.info("入参：nickname={}, email={}, content={}, parentId={}", 
            submitReqVO.getNickname(), submitReqVO.getEmail(), 
            submitReqVO.getContent(), submitReqVO.getParentId());
        
        try {
            guestbookService.submit(submitReqVO);
            log.info("留言提交成功");
            return Response.success("留言提交成功");
        } catch (Exception e) {
            log.error("提交留言失败", e);
            return Response.fail("提交失败：" + e.getMessage());
        }
    }

    /**
     * 点赞留言
     */
    @ApiOperation("点赞留言")
    @PostMapping("/like/{messageId}")
    public Response<String> like(@PathVariable Long messageId) {
        log.info("点赞留言，messageId：{}", messageId);
        
        try {
            guestbookService.like(messageId);
            return Response.success("点赞成功");
        } catch (Exception e) {
            log.error("点赞留言失败", e);
            return Response.fail("点赞失败");
        }
    }

    /**
     * 取消点赞
     */
    @ApiOperation("取消点赞")
    @PostMapping("/unlike/{messageId}")
    public Response<String> unlike(@PathVariable Long messageId) {
        log.info("取消点赞，messageId：{}", messageId);
        
        try {
            guestbookService.unlike(messageId);
            return Response.success("取消点赞成功");
        } catch (Exception e) {
            log.error("取消点赞失败", e);
            return Response.fail("取消点赞失败");
        }
    }

    /**
     * 回复留言
     */
    @ApiOperation("回复留言")
    @PostMapping("/reply")
    public Response<String> reply(@RequestBody GuestbookSubmitReqVO replyReqVO) {
        log.info("回复留言，入参：{}", replyReqVO);
        
        try {
            guestbookService.reply(replyReqVO);
            return Response.success("回复成功");
        } catch (Exception e) {
            log.error("回复留言失败", e);
            return Response.fail("回复失败");
        }
    }

    /**
     * 获取留言的回复列表
     */
    @ApiOperation("获取留言的回复列表")
    @GetMapping("/replies/{parentId}")
    public Response<List<GuestbookDO>> getReplies(@PathVariable Long parentId) {
        log.info("获取留言回复列表，parentId：{}", parentId);
        
        try {
            List<GuestbookDO> replies = guestbookService.getReplies(parentId);
            return Response.success(replies);
        } catch (Exception e) {
            log.error("获取留言回复列表失败", e);
            return Response.fail("获取回复列表失败");
        }
    }

    /**
     * 获取留言板统计信息
     */
    @ApiOperation("获取留言板统计信息")
    @GetMapping("/stats")
    public Response<Object> stats() {
        log.info("获取留言板统计信息");
        
        try {
            Object stats = guestbookService.getStats();
            return Response.success(stats);
        } catch (Exception e) {
            log.error("获取留言板统计信息失败", e);
            return Response.fail("获取统计信息失败");
        }
    }
}
