package com.github.ong.vo;

import com.github.ong.model.h2.UserUploadInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.List;

@Getter
@Setter
public class UserUploadInfoVo {

    private UserUploadInfo userUploadInfo;

    private String beforeImg1Url;

    private String beforeImg2Url;

    private String beforeVideo1Url;

    private String beforeVideoImg1Url;

    private String beforeVideo2Url;

    private String beforeVideoImg2Url;

    private String installVideo1Url;

    private String installVideoImg1Url;

    private String installVideo2Url;

    private String installVideoImg2Url;

    private String installVideo3Url;

    private String installVideoImg3Url;

    private String installVideo4Url;

    private String installVideoImg4Url;

    private String displayImg1Url;

    private String displayImg2Url;

    private String displayImg3Url;

    private String displayImg4Url;

    private String displayImg5Url;

    private String displayImg6Url;

    private String displayVideo1Url;

    private String displayVideoImg1Url;

    private String displayVideo2Url;

    private String displayVideoImg2Url;

    private String displayVideo3Url;

    private String displayVideoImg3Url;

    private String displayVideo4Url;

    private String displayVideoImg4Url;

    private String displayVideo5Url;

    private String displayVideoImg5Url;

    private String displayVideo6Url;

    private String displayVideoImg6Url;

    private String disposeVideo1Url;

    private String disposeVideoImg1Url;

    private String disposeVideo2Url;

    private String disposeVideoImg2Url;

    private String disposeVideo3Url;

    private String disposeVideoImg3Url;

    private String disposeVideo4Url;

    private String disposeVideoImg4Url;

    private String disposeVideo5Url;

    private String disposeVideoImg5Url;

    private String disposeVideo6Url;

    private String disposeVideoImg6Url;

    private List<AdminUploadVideoVo> adminUploadVideoVoList;
}
