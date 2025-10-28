package com.quanxiaoha.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quanxiaoha.weblog.common.domain.dos.GuestbookDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 留言板Mapper接口
 * 
 * @author quanxiaoha
 * @since 2024-01-15
 */
@Mapper
public interface GuestbookMapper extends BaseMapper<GuestbookDO> {

    /**
     * 分页查询留言列表（管理端）
     * @param current 当前页
     * @param size 每页大小
     * @param nickname 昵称（模糊查询）
     * @param email 邮箱（模糊查询）
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 分页结果
     */
    default Page<GuestbookDO> selectPageList(long current, long size, String nickname, String email,
                                              LocalDate startDate, LocalDate endDate) {
        Page<GuestbookDO> page = new Page<>(current, size);

        LambdaQueryWrapper<GuestbookDO> wrapper = Wrappers.<GuestbookDO>lambdaQuery()
                .like(Objects.nonNull(nickname), GuestbookDO::getNickname, nickname)
                .like(Objects.nonNull(email), GuestbookDO::getEmail, email)
                .ge(Objects.nonNull(startDate), GuestbookDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), GuestbookDO::getCreateTime, 
                    Objects.nonNull(endDate) ? endDate.atTime(23, 59, 59) : null)
                .orderByDesc(GuestbookDO::getCreateTime);

        return selectPage(page, wrapper);
    }

    /**
     * 根据父留言ID删除回复
     * @param parentId 父留言ID
     * @return 删除数量
     */
    default int deleteByParentId(Long parentId) {
        return delete(Wrappers.<GuestbookDO>lambdaQuery()
                .eq(GuestbookDO::getParentId, parentId));
    }
}

