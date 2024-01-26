package com.github.ong.config.http;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.github.ong.utils.AliyunUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssClientConfig {

    public static final String BUCKET_NAME = "cat-no-roll";

    @Bean("ossClient")
    public OSS buildOssClient() {
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = AliyunUtil.accessKeyId;
        String accessKeySecret = AliyunUtil.accessKeySecret;
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
