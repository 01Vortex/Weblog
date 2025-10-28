package com.quanxiaoha.weblog.search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: 无心
 * @url: www.wuxin.com
 * @date: 2024-05-11 8:49
 * @description: lucene 配置
 **/
@ConfigurationProperties(prefix = "lucene")
@Component
@Data
public class LuceneProperties {
    private String indexDir;
}
