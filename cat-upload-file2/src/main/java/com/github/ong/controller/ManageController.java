package com.github.ong.controller;

import com.github.ong.service.UserUploadInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/manage")
@Slf4j
@RestController
public class ManageController {

    @Resource
    private UserUploadInfoService userUploadInfoService;

    @RequestMapping("/zipUserUploadInfo")
    public void zipUserUploadInfo() {
        userUploadInfoService.zipUserUploadInfo();
    }
}
