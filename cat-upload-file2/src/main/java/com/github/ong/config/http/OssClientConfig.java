package com.github.ong.config.http;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssClientConfig {

    public static final String BUCKET_NAME = "cat-no-roll";

    @Bean("ossClient")
    public OSS buildOssClient() {
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAI5t6RXw3rgXyPQQeXA23v";
        String accessKeySecret = "8NyhkXjXDBuPSZumpDL4x5GxvIHb7Z";
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
