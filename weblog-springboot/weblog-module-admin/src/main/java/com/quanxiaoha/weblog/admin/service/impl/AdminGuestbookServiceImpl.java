package com.quanxiaoha.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quanxiaoha.weblog.admin.model.vo.guestbook.*;
import com.quanxiaoha.weblog.admin.service.AdminGuestbookService;
import com.quanxiaoha.weblog.common.domain.dos.GuestbookDO;
import com.quanxiaoha.weblog.common.domain.mapper.GuestbookMapper;
import com.quanxiaoha.weblog.common.enums.ResponseCodeEnum;
import com.quanxiaoha.weblog.common.exception.BizException;
import com.quanxiaoha.weblog.common.utils.PageResponse;
import com.quanxiaoha.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024-10-28
 * @description: 留言板管理
 **/
@Service
@Slf4j
public class AdminGuestbookServiceImpl implements AdminGuestbookService {

    @Autowired
    private GuestbookMapper guestbookMapper;

    /**
     * 查询留言分页数据
     *
     * @param findGuestbookPageListReqVO
     * @return
     */
    @Override
    public Response findGuestbookPageList(FindGuestbookPageListReqVO findGuestbookPageListReqVO) {
        Long current = findGuestbookPageListReqVO.getCurrent();
        Long size = findGuestbookPageListReqVO.getSize();
        LocalDate startDate = findGuestbookPageListReqVO.getStartDate();
        LocalDate endDate = findGuestbookPageListReqVO.getEndDate();
        String nickname = findGuestbookPageListReqVO.getNickname();
        String email = findGuestbookPageListReqVO.getEmail();

        // 执行分页查询
        Page<GuestbookDO> guestbookDOPage = guestbookMapper.selectPageList(
                current, size, nickname, email, startDate, endDate);

        return PageResponse.success(guestbookDOPage, guestbookDOPage.getRecords());
    }

    /**
     * 删除留言
     *
     * @param deleteGuestbookReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response deleteGuestbook(DeleteGuestbookReqVO deleteGuestbookReqVO) {
        Long guestbookId = deleteGuestbookReqVO.getId();

        // 查询留言是否存在
        GuestbookDO guestbookDO = guestbookMapper.selectById(guestbookId);

        if (Objects.isNull(guestbookDO)) {
            log.warn("该留言不存在, guestbookId: {}", guestbookId);
            throw new BizException(ResponseCodeEnum.GUESTBOOK_NOT_FOUND);
        }

        // 删除留言
        guestbookMapper.deleteById(guestbookId);

        // 如果是父留言，删除所有回复
        if (Objects.isNull(guestbookDO.getParentId())) {
            guestbookMapper.deleteByParentId(guestbookId);
        }

        return Response.success();
    }

    /**
     * 批量删除留言
     *
     * @param batchDeleteGuestbookReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response batchDeleteGuestbook(BatchDeleteGuestbookReqVO batchDeleteGuestbookReqVO) {
        List<Long> ids = batchDeleteGuestbookReqVO.getIds();

        if (Objects.isNull(ids) || ids.isEmpty()) {
            throw new BizException(ResponseCodeEnum.PARAM_NOT_VALID);
        }

        // 批量删除
        for (Long id : ids) {
            DeleteGuestbookReqVO deleteReqVO = DeleteGuestbookReqVO.builder().id(id).build();
            deleteGuestbook(deleteReqVO);
        }

        return Response.success();
    }

    /**
     * 更新留言
     *
     * @param updateGuestbookReqVO
     * @return
     */
    @Override
    public Response updateGuestbook(UpdateGuestbookReqVO updateGuestbookReqVO) {
        Long guestbookId = updateGuestbookReqVO.getId();

        // 查询留言是否存在
        GuestbookDO guestbookDO = guestbookMapper.selectById(guestbookId);

        if (Objects.isNull(guestbookDO)) {
            log.warn("该留言不存在, guestbookId: {}", guestbookId);
            throw new BizException(ResponseCodeEnum.GUESTBOOK_NOT_FOUND);
        }

        // 构建更新对象
        GuestbookDO updateGuestbook = GuestbookDO.builder()
                .id(guestbookId)
                .nickname(updateGuestbookReqVO.getNickname())
                .email(updateGuestbookReqVO.getEmail())
                .content(updateGuestbookReqVO.getContent())
                .likeCount(updateGuestbookReqVO.getLikeCount())
                .build();

        // 更新留言
        guestbookMapper.updateById(updateGuestbook);

        return Response.success();
    }

    /**
     * 查询留言详情
     *
     * @param findGuestbookDetailReqVO
     * @return
     */
    @Override
    public Response findGuestbookDetail(FindGuestbookDetailReqVO findGuestbookDetailReqVO) {
        Long guestbookId = findGuestbookDetailReqVO.getId();

        // 查询留言
        GuestbookDO guestbookDO = guestbookMapper.selectById(guestbookId);

        if (Objects.isNull(guestbookDO)) {
            log.warn("该留言不存在, guestbookId: {}", guestbookId);
            throw new BizException(ResponseCodeEnum.GUESTBOOK_NOT_FOUND);
        }

        return Response.success(guestbookDO);
    }
}

