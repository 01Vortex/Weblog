package com.quanxiaoha.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 留言板实体类
 * 
 * @author quanxiaoha
 * @since 2024-01-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_guestbook")
public class GuestbookDO {
    
    /**
     * 留言ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 留言内容
     */
    private String content;
    
    /**
     * 父留言ID（用于回复）
     */
    private Long parentId;
    
    /**
     * 点赞数
     */
    private Integer likeCount;
    
    /**
     * 是否管理员留言
     */
    private Boolean isAdmin;
    
    /**
     * 是否已审核
     */
    private Boolean isAudited;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 是否删除
     */
    private Boolean isDeleted;
}
