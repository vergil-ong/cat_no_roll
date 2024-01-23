package com.github.ong.service;

import com.github.ong.controller.api.UploadFileApiController;
import com.github.ong.utils.VideoFrameUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class VideoServiceTest {

    @Test
    public void testGetFirstFrame() {
        String rootPath = "/Users/wangshuo/IdeaGitProjects/cat_no_roll/files";
        String imagePath = rootPath + "/ADMIN_CAT_NO_ROLL/install4_img.jpg";
        String videoPath = rootPath + "/ADMIN_CAT_NO_ROLL/install4.mp4";
        boolean getFrameRes = VideoFrameUtil.getFrame(imagePath, videoPath);
        log.info("getFrameRes is {}", getFrameRes);
    }
}
