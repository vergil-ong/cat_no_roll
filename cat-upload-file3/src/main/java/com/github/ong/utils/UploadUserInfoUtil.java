package com.github.ong.utils;

import com.github.ong.enums.db.WholeAddr;
import com.github.ong.model.h2.AdminUploadVideo;
import com.github.ong.model.h2.FileAddr;
import com.github.ong.vo.AdminUploadVideoVo;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

@Slf4j
public class UploadUserInfoUtil {

    public static final String WECHAT_CODE_ADMIN = "ADMIN_CAT_NO_ROLL";

    public static final String BEFORE = "before_";

    public static final String INSTALL = "install_";

    public static final String DISPLAY = "display_";

    public static final String DISPOSE = "dispose_";

    public static AdminUploadVideoVo getAdminUploadVideoVo(AdminUploadVideo adminUploadVideo, Map<Long, FileAddr> fileAddrMap) {
        AdminUploadVideoVo adminUploadVideoVo = new AdminUploadVideoVo();
        adminUploadVideoVo.setAdminUploadVideo(adminUploadVideo);
        FileAddr videoFileAddr = fileAddrMap.get(adminUploadVideo.getVideoId());
        if (Objects.nonNull(videoFileAddr)) {
            if (WholeAddr.SSO.getCode().equals(videoFileAddr.getWholeAddr())) {
                adminUploadVideoVo.setVideoUrl(AliyunUtil.SSO_ROOT + videoFileAddr.getAddr());
                adminUploadVideoVo.setVideoFileName(adminUploadVideo.getId() + FileUtil.DOT + FileUtil.getFileSuffix(videoFileAddr.getOriginalFileName()));
            } else {
                adminUploadVideoVo.setVideoUrl(videoFileAddr.getAddr());
                adminUploadVideoVo.setVideoFileName(videoFileAddr.getOriginalFileName());
            }
        }

        FileAddr imgFileAddr = fileAddrMap.get(adminUploadVideo.getVideoImgId());
        if (Objects.nonNull(imgFileAddr)) {
            if (WholeAddr.SSO.getCode().equals(imgFileAddr.getWholeAddr())) {
                adminUploadVideoVo.setImgUrl(AliyunUtil.SSO_ROOT + imgFileAddr.getAddr());
            } else {
                adminUploadVideoVo.setImgUrl(imgFileAddr.getAddr());
            }

        }
        return adminUploadVideoVo;
    }
}
