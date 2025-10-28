package com.quanxiaoha.weblog.admin.service;

import com.quanxiaoha.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import com.quanxiaoha.weblog.common.utils.Response;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024-09-15 14:03
 * @description: TODO
 **/
public interface AdminFileService {
    /**
     * 上传文件
     * @param file
     * @return
     */
    Response uploadFile(MultipartFile file);
}
