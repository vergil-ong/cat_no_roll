package com.github.ong.job;

import com.github.ong.service.UserUploadInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class UploadFileJob {

    @Resource
    private UserUploadInfoService userUploadInfoService;

    @Scheduled(cron = "0 0/2 * * * *")
    public void zipUserUploadInfo() {
        log.info("zipUserUploadInfo start");
        userUploadInfoService.zipUserUploadInfo();
        log.info("zipUserUploadInfo end");
    }
}
