package com.quanxiaoha.weblog.web.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024/11/9 10:05
 * @description: 评论发布事件
 **/

@Getter
public class PublishCommentEvent extends ApplicationEvent {

    /**
     * 评论 ID
     */
    private Long commentId;

    public PublishCommentEvent(Object source, Long commentId) {
        super(source);
        this.commentId = commentId;
    }
}
