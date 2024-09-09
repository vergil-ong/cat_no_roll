package com.github.ong.model.h2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_upload_info")
@Getter
@Setter
public class UserUploadInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wechat_code")
    private String wechatCode;

    @Column(name = "before_img_1")
    private Long beforeImg1;

    @Column(name = "before_img_2")
    private Long beforeImg2;

    @Column(name = "before_video_1")
    private Long beforeVideo1;

    @Column(name = "before_video_img_1")
    private Long beforeVideoImg1;

    @Column(name = "before_video_2")
    private Long beforeVideo2;

    @Column(name = "before_video_img_2")
    private Long beforeVideoImg2;

    @Column(name = "install_img_1")
    private Long installImg1;

    @Column(name = "install_img_2")
    private Long installImg2;

    @Column(name = "install_video_1")
    private Long installVideo1;

    @Column(name = "install_video_img_1")
    private Long installVideoImg1;

    @Column(name = "install_video_2")
    private Long installVideo2;

    @Column(name = "install_video_img_2")
    private Long installVideoImg2;

    @Column(name = "install_video_3")
    private Long installVideo3;

    @Column(name = "install_video_img_3")
    private Long installVideoImg3;

    @Column(name = "install_video_4")
    private Long installVideo4;

    @Column(name = "install_video_img_4")
    private Long installVideoImg4;

    @Column(name = "display_img_1")
    private Long displayImg1;

    @Column(name = "display_img_2")
    private Long displayImg2;

    @Column(name = "display_img_3")
    private Long displayImg3;

    @Column(name = "display_img_4")
    private Long displayImg4;

    @Column(name = "display_img_5")
    private Long displayImg5;

    @Column(name = "display_img_6")
    private Long displayImg6;

    @Column(name = "display_video_1")
    private Long displayVideo1;

    @Column(name = "display_video_img_1")
    private Long displayVideoImg1;

    @Column(name = "display_video_2")
    private Long displayVideo2;

    @Column(name = "display_video_img_2")
    private Long displayVideoImg2;

    @Column(name = "display_video_3")
    private Long displayVideo3;

    @Column(name = "display_video_img_3")
    private Long displayVideoImg3;

    @Column(name = "display_video_4")
    private Long displayVideo4;

    @Column(name = "display_video_img_4")
    private Long displayVideoImg4;

    @Column(name = "display_video_5")
    private Long displayVideo5;

    @Column(name = "display_video_img_5")
    private Long displayVideoImg5;

    @Column(name = "display_video_6")
    private Long displayVideo6;

    @Column(name = "display_video_img_6")
    private Long displayVideoImg6;

    @Column(name = "dispose_video_1")
    private Long disposeVideo1;

    @Column(name = "dispose_video_img_1")
    private Long disposeVideoImg1;

    @Column(name = "dispose_video_2")
    private Long disposeVideo2;

    @Column(name = "dispose_video_img_2")
    private Long disposeVideoImg2;

    @Column(name = "dispose_video_3")
    private Long disposeVideo3;

    @Column(name = "dispose_video_img_3")
    private Long disposeVideoImg3;

    @Column(name = "dispose_video_4")
    private Long disposeVideo4;

    @Column(name = "dispose_video_img_4")
    private Long disposeVideoImg4;

    @Column(name = "dispose_video_5")
    private Long disposeVideo5;

    @Column(name = "dispose_video_img_5")
    private Long disposeVideoImg5;

    @Column(name = "dispose_video_6")
    private Long disposeVideo6;

    @Column(name = "dispose_video_img_6")
    private Long disposeVideoImg6;

    /**
     * 1 变化了
     * 0 没变化
     */
    @Column(name = "file_change")
    private Integer fileChange;

    @Column(name = "zip_url")
    private String zipUrl;
}