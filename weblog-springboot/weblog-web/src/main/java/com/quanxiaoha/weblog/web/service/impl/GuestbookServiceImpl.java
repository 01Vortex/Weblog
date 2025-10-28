package com.quanxiaoha.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quanxiaoha.weblog.common.domain.dos.GuestbookDO;
import com.quanxiaoha.weblog.common.domain.vo.GuestbookPageListReqVO;
import com.quanxiaoha.weblog.common.domain.vo.GuestbookSubmitReqVO;
import com.quanxiaoha.weblog.common.domain.vo.PageVO;
import com.quanxiaoha.weblog.web.mapper.GuestbookMapper;
import com.quanxiaoha.weblog.web.service.GuestbookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 留言板服务实现类
 * 
 * @author quanxiaoha
 * @since 2024-01-15
 */
@Service
@Slf4j
public class GuestbookServiceImpl implements GuestbookService {
    
    // 博主邮箱
    private static final String ADMIN_EMAIL = "2911130943@qq.com";
    
    @Autowired
    private GuestbookMapper guestbookMapper;
    
    /**
     * 分页查询留言板列表
     */
    @Override
    public PageVO<GuestbookDO> pageList(GuestbookPageListReqVO pageListReqVO) {
        Long current = pageListReqVO.getCurrent();
        Long size = pageListReqVO.getSize();
        
        // 创建分页对象
        Page<GuestbookDO> page = new Page<>(current, size);
        
        // 构建查询条件：只查询未删除、已审核的留言
        LambdaQueryWrapper<GuestbookDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GuestbookDO::getIsDeleted, false)
               .eq(GuestbookDO::getIsAudited, true)
               .isNull(GuestbookDO::getParentId) // 只查询顶级留言
               .orderByDesc(GuestbookDO::getCreateTime);
        
        // 执行分页查询
        IPage<GuestbookDO> pageResult = guestbookMapper.selectPage(page, wrapper);
        
        // 封装返回结果
        return PageVO.<GuestbookDO>builder()
                .records(pageResult.getRecords())
                .total(pageResult.getTotal())
                .current(pageResult.getCurrent())
                .size(pageResult.getSize())
                .pages(pageResult.getPages())
                .build();
    }
    
    /**
     * 提交留言
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(GuestbookSubmitReqVO submitReqVO) {
        // 判断是否为博主
        boolean isAdmin = ADMIN_EMAIL.equals(submitReqVO.getEmail());
        
        // 构建留言对象
        GuestbookDO guestbookDO = GuestbookDO.builder()
                .nickname(submitReqVO.getNickname())
                .email(submitReqVO.getEmail())
                .content(submitReqVO.getContent())
                .parentId(submitReqVO.getParentId())
                .likeCount(0)
                .isAdmin(isAdmin)
                .isAudited(true) // 默认审核通过，可以根据需要调整
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .isDeleted(false)
                .build();
        
        // 插入数据库
        guestbookMapper.insert(guestbookDO);
        log.info("留言提交成功，留言ID：{}，是否为博主：{}", guestbookDO.getId(), isAdmin);
    }
    
    /**
     * 点赞/取消点赞留言
     * @param messageId 留言ID
     * @param isLike true-点赞，false-取消点赞
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void like(Long messageId) {
        // 查询留言
        GuestbookDO guestbookDO = guestbookMapper.selectById(messageId);
        if (guestbookDO == null) {
            throw new RuntimeException("留言不存在");
        }
        
        // 增加点赞数
        int currentLikeCount = guestbookDO.getLikeCount() != null ? guestbookDO.getLikeCount() : 0;
        guestbookDO.setLikeCount(currentLikeCount + 1);
        guestbookDO.setUpdateTime(LocalDateTime.now());
        
        // 更新数据库
        guestbookMapper.updateById(guestbookDO);
        log.info("点赞成功，留言ID：{}，当前点赞数：{}", messageId, guestbookDO.getLikeCount());
    }
    
    /**
     * 取消点赞
     * @param messageId 留言ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlike(Long messageId) {
        // 查询留言
        GuestbookDO guestbookDO = guestbookMapper.selectById(messageId);
        if (guestbookDO == null) {
            throw new RuntimeException("留言不存在");
        }
        
        // 减少点赞数，最小为0
        int currentLikeCount = guestbookDO.getLikeCount() != null ? guestbookDO.getLikeCount() : 0;
        guestbookDO.setLikeCount(Math.max(0, currentLikeCount - 1));
        guestbookDO.setUpdateTime(LocalDateTime.now());
        
        // 更新数据库
        guestbookMapper.updateById(guestbookDO);
        log.info("取消点赞成功，留言ID：{}，当前点赞数：{}", messageId, guestbookDO.getLikeCount());
    }
    
    /**
     * 回复留言
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reply(GuestbookSubmitReqVO replyReqVO) {
        // 验证父留言是否存在
        Long parentId = replyReqVO.getParentId();
        if (parentId == null) {
            throw new RuntimeException("父留言ID不能为空");
        }
        
        GuestbookDO parentMessage = guestbookMapper.selectById(parentId);
        if (parentMessage == null) {
            throw new RuntimeException("父留言不存在");
        }
        
        // 调用提交方法
        submit(replyReqVO);
    }
    
    /**
     * 获取留言的回复列表
     */
    @Override
    public List<GuestbookDO> getReplies(Long parentId) {
        LambdaQueryWrapper<GuestbookDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GuestbookDO::getParentId, parentId)
               .eq(GuestbookDO::getIsDeleted, false)
               .eq(GuestbookDO::getIsAudited, true)
               .orderByAsc(GuestbookDO::getCreateTime);
        
        return guestbookMapper.selectList(wrapper);
    }
    
    /**
     * 获取留言板统计信息
     */
    @Override
    public Object getStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // 总留言数（不包括删除的）
        LambdaQueryWrapper<GuestbookDO> totalWrapper = new LambdaQueryWrapper<>();
        totalWrapper.eq(GuestbookDO::getIsDeleted, false)
                   .eq(GuestbookDO::getIsAudited, true);
        Long totalCount = guestbookMapper.selectCount(totalWrapper);
        
        // 今日留言数
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LambdaQueryWrapper<GuestbookDO> todayWrapper = new LambdaQueryWrapper<>();
        todayWrapper.eq(GuestbookDO::getIsDeleted, false)
                   .eq(GuestbookDO::getIsAudited, true)
                   .ge(GuestbookDO::getCreateTime, startOfDay);
        Long todayCount = guestbookMapper.selectCount(todayWrapper);
        
        stats.put("totalCount", totalCount);
        stats.put("todayCount", todayCount);
        
        return stats;
    }
}

