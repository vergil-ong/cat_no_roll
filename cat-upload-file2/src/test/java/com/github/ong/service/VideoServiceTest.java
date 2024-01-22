package com.github.ong.service;

import com.github.ong.controller.api.UploadFileApiController;
import com.github.ong.utils.VideoFrameUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class VideoServiceTest {

    @Test
    public void testGetFirstFrame() {
        String imagePath = UploadFileApiController.root_path + "/ADMIN_CAT_NO_ROLL/install4_img.jpg";
        String videoPath = UploadFileApiController.root_path + "/ADMIN_CAT_NO_ROLL/install4.mp4";
        boolean getFrameRes = VideoFrameUtil.getFrame(imagePath, videoPath);
        log.info("getFrameRes is {}", getFrameRes);
    }
}
