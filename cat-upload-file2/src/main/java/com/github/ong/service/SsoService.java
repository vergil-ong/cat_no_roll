package com.github.ong.service;

import com.aliyun.oss.OSS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ong.config.http.OssClientConfig;
import com.github.ong.model.sso.ZipInfo;
import com.github.ong.utils.AliyunUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Objects;

@Service
@Slf4j
public class SsoService {

    @Resource(name = "zipRestTemplate")
    private RestTemplate zipRestTemplate;

    @Resource(name = "ossClient")
    private OSS ossClient;

    @Resource
    private ObjectMapper objectMapper;

    public String zipFileDir(ZipInfo zipInfo, String wechatCode) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String jsonStr;
            try {
                jsonStr = objectMapper.writeValueAsString(zipInfo);
            } catch (JsonProcessingException e) {
                log.info("JsonProcessingException exception {}", ExceptionUtils.getStackTrace(e));
                return null;
            }

            HttpEntity<String> request = new HttpEntity<>(jsonStr, headers);

            ResponseEntity<String> responseEntity = zipRestTemplate.postForEntity(AliyunUtil.FUNC_ROOT, request, String.class);
            HttpHeaders responseEntityHeaders = responseEntity.getHeaders();
            URI location = responseEntityHeaders.getLocation();
            if (Objects.isNull(location)) {
                return null;
            }
            String zipFilePath = location.getPath();
            String fileKey = zipFilePath.replaceFirst("/", "");
            log.info("fileKey is {}", fileKey);
            String newFileKey = "output/" + wechatCode + ".zip";
            ossClient.copyObject(OssClientConfig.BUCKET_NAME, fileKey, OssClientConfig.BUCKET_NAME, newFileKey);
            ossClient.deleteObject(OssClientConfig.BUCKET_NAME, fileKey);
            return newFileKey;
        } catch (Exception e) {
            log.info("zipFileDir exception {}", ExceptionUtils.getStackTrace(e));
            return null;
        }
    }
}
