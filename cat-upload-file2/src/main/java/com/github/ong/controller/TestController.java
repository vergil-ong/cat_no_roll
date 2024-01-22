package com.github.ong.controller;

import com.github.ong.controller.api.UploadFileApiController;
import com.github.ong.utils.VideoFrameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @RequestMapping("/test1")
    public String test1(String hello) {
//        log.info("hello param is {}", hello);
//        return "hello " + hello;

        String imagePath = UploadFileApiController.root_path + "/ADMIN_CAT_NO_ROLL/before1Frame.jpg";
        String videoPath = UploadFileApiController.root_path + "/ADMIN_CAT_NO_ROLL/before1.mp4";
        boolean getFrameRes = VideoFrameUtil.getFrame(imagePath, videoPath);
        log.info("getFrameRes is {}", getFrameRes);
        return "OK";
    }
}
