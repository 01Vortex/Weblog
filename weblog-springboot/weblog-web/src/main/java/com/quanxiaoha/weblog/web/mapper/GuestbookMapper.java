package com.quanxiaoha.weblog.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quanxiaoha.weblog.common.domain.dos.GuestbookDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 留言板Mapper接口
 * 
 * @author quanxiaoha
 * @since 2024-01-15
 */
@Mapper
public interface GuestbookMapper extends BaseMapper<GuestbookDO> {
}
