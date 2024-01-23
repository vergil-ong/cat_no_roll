package com.github.ong.controller.api;

import com.github.ong.service.StsService;
import com.github.ong.vo.StsInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/api/sts")
public class StsApiController {

    @Resource
    private StsService stsService;

    @RequestMapping("/info")
    public StsInfo getStsInfo() {
        return stsService.getStsInfoFromCache();
    }
}
