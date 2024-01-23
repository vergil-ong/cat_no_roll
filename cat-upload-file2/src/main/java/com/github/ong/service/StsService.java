package com.github.ong.service;

import com.aliyun.sts20150401.Client;
import com.aliyun.sts20150401.models.AssumeRoleResponse;
import com.aliyun.sts20150401.models.AssumeRoleResponseBody;
import com.github.ong.utils.StringUtil;
import com.github.ong.vo.StsInfo;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
public class StsService {

    LoadingCache<String, StsInfo> stsInfoCache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(Duration.ofSeconds(50 * 60L))
            .build(new CacheLoader<String, StsInfo>() {
                @Override
                public StsInfo load(String key) throws Exception {
                    return getStsInfo();
                }
            });

    public static com.aliyun.sts20150401.Client createClient(String accessKeyId,
                                                             String accessKeySecret,
                                                             String endpoint) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/Sts
        config.endpoint = endpoint;
        return new com.aliyun.sts20150401.Client(config);
    }

    public StsInfo getStsInfo() {
        String endpoint = "sts.cn-hangzhou.aliyuncs.com";
        // cat-no-roll@1254504270607038.onaliyun.com
        String accessKeyId = "LTAI5t6RXw3rgXyPQQeXA23v";
        String accessKeySecret = "8NyhkXjXDBuPSZumpDL4x5GxvIHb7Z";
        // RamOss roleArn
        String roleArn = "acs:ram::1254504270607038:role/ramoss";
        // 自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest。
        String roleSessionName = "SessionOss";
        // 设置临时访问凭证的有效时间为3600秒。
        Long durationSeconds = 3600L;
        StsInfo stsInfo = new StsInfo();
        try {
            Client client = createClient(accessKeyId, accessKeySecret, endpoint);
            com.aliyun.sts20150401.models.AssumeRoleRequest assumeRoleRequest = new com.aliyun.sts20150401.models.AssumeRoleRequest()
                    .setDurationSeconds(3600L)
                    .setRoleArn(roleArn)
                    .setDurationSeconds(durationSeconds)
                    .setRoleSessionName(roleSessionName);
            com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();

            AssumeRoleResponse assumeRoleResponse = client.assumeRoleWithOptions(assumeRoleRequest, runtime);
            AssumeRoleResponseBody body = assumeRoleResponse.getBody();
            stsInfo.setAccessKeyId(body.getCredentials().getAccessKeyId());
            stsInfo.setAccessKeySecret(body.getCredentials().getAccessKeySecret());
            stsInfo.setStsToken(body.getCredentials().getSecurityToken());
            return stsInfo;
        } catch (Exception e) {
            log.info("getStsInfo error {}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public StsInfo getStsInfoFromCache() {
        return stsInfoCache.getUnchecked(StringUtil.BLANK);
    }
}
