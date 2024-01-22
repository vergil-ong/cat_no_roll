package com.github.ong.vo;

import com.github.ong.model.h2.AdminUploadVideo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUploadVideoVo {

    private AdminUploadVideo adminUploadVideo;

    private String imgUrl;

    private String videoUrl;

    private String videoFileName;
}
