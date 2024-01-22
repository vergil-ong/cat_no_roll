package com.github.ong.enums.biz;

import com.github.ong.enums.BaseDaoEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum UploadFileIndex implements BaseDaoEnum {
    BEFORE_IMG_1(1, "改造前图1", "uploadImg1", "0"),
    BEFORE_IMG_2(2, "改造前图2", "uploadImg1", "1"),
    BEFORE_VIDEO_1(3, "改造前视频1", "uploadVideo1", "0"),
    BEFORE_VIDEO_2(4, "改造前视频2", "uploadVideo1", "1"),
    INSTALL_VIDEO_1(5, "安装视频1", "uploadVideo2", "0"),
    INSTALL_VIDEO_2(6, "安装视频2", "uploadVideo2", "1"),
    INSTALL_VIDEO_3(7, "安装视频3", "uploadVideo2", "2"),
    INSTALL_VIDEO_4(8, "安装视频4", "uploadVideo2", "3"),
    DISPLAY_IMG_1(9, "展示图片1", "uploadImg3", "0"),
    DISPLAY_IMG_2(10, "展示图片2", "uploadImg3", "1"),
    DISPLAY_IMG_3(11, "展示图片3", "uploadImg3", "2"),
    DISPLAY_IMG_4(12, "展示图片4", "uploadImg3", "3"),
    DISPLAY_IMG_5(13, "展示图片5", "uploadImg3", "4"),
    DISPLAY_IMG_6(14, "展示图片6", "uploadImg3", "5"),
    DISPLAY_VIDEO_1(15, "展示视频1", "uploadVideo3", "0"),
    DISPLAY_VIDEO_2(16, "展示视频2", "uploadVideo3", "1"),
    DISPLAY_VIDEO_3(17, "展示视频3", "uploadVideo3", "2"),
    DISPLAY_VIDEO_4(18, "展示视频4", "uploadVideo3", "3"),
    DISPLAY_VIDEO_5(19, "展示视频5", "uploadVideo3", "4"),
    DISPLAY_VIDEO_6(20, "展示视频6", "uploadVideo3", "5"),
    DISPOSE_VIDEO_1(21, "布置视频1", "uploadVideo4", "0"),
    DISPOSE_VIDEO_2(22, "布置视频2", "uploadVideo4", "1"),
    DISPOSE_VIDEO_3(23, "布置视频3", "uploadVideo4", "2"),
    DISPOSE_VIDEO_4(24, "布置视频4", "uploadVideo4", "3"),
    DISPOSE_VIDEO_5(25, "布置视频5", "uploadVideo4", "4"),
    DISPOSE_VIDEO_6(26, "布置视频6", "uploadVideo4", "5"),
    ;

    private Integer code;

    private String name;

    private String fileId;

    private String fileIndex;

    UploadFileIndex(Integer code, String name, String fileId, String fileIndex) {
        this.code = code;
        this.name = name;
        this.fileId = fileId;
        this.fileIndex = fileIndex;
    }

    public static UploadFileIndex getFileIndex(String fileId, String fileIndex) {
        for (UploadFileIndex uploadFileIndex : values()) {
            if (StringUtils.equals(fileId, uploadFileIndex.getFileId())
                && StringUtils.equals(fileIndex, uploadFileIndex.getFileIndex())) {
                return uploadFileIndex;
            }
        }
        return null;
    }
}
