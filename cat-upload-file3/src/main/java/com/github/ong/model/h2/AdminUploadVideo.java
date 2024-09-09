package com.github.ong.model.h2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "admin_upload_video")
@Getter
@Setter
public class AdminUploadVideo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wechat_code")
    private String wechatCode;

    @Column(name = "video_id")
    private Long videoId;

    @Column(name = "video_img_id")
    private Long videoImgId;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "user_download_count")
    private Integer userDownCount;
}
