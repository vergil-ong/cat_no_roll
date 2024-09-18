package com.github.ong.controller.api;

import com.github.ong.service.UserUploadInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Resource
    private UserUploadInfoService userUploadInfoService;

    @GetMapping("/zipUserUploadInfo")
    public void testZipUserUploadInfo() {
        userUploadInfoService.zipUserUploadInfo();
    }
}
