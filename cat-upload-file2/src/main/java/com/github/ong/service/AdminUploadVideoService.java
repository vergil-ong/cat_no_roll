package com.github.ong.service;

import com.github.ong.controller.api.UploadFileApiController;
import com.github.ong.dao.h2.AdminUploadVideoDao;
import com.github.ong.dao.h2.FileAddrDao;
import com.github.ong.enums.BaseDaoEnum;
import com.github.ong.enums.db.FileType;
import com.github.ong.enums.db.WholeAddr;
import com.github.ong.model.h2.AdminUploadVideo;
import com.github.ong.model.h2.FileAddr;
import com.github.ong.utils.AliyunUtil;
import com.github.ong.utils.FileUtil;
import com.github.ong.utils.UploadUserInfoUtil;
import com.github.ong.vo.AdminUploadVideoVo;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminUploadVideoService {

    @Resource
    private AdminUploadVideoDao adminUploadVideoDao;

    @Resource
    private FileAddrDao fileAddrDao;

    @Resource
    private UserUploadInfoService userUploadInfoService;

    public void recordVideo(AdminUploadVideo adminUploadVideo) {
        adminUploadVideoDao.save(adminUploadVideo);
    }

    public List<AdminUploadVideoVo> listVideo(String wechatCode) {
        AdminUploadVideo condition = new AdminUploadVideo();
        condition.setWechatCode(wechatCode);

        List<AdminUploadVideo> videoList = adminUploadVideoDao.findAll(Example.of(condition));
        if (CollectionUtils.isEmpty(videoList)) {
            return Collections.emptyList();
        }
        List<Long> videoIdList = new ArrayList<>();
        List<Long> imgIdList = new ArrayList<>();
        for (AdminUploadVideo adminUploadVideo : videoList) {
            videoIdList.add(adminUploadVideo.getVideoId());
            imgIdList.add(adminUploadVideo.getVideoImgId());
        }

        List<Long> fileIdList = new ArrayList<>(videoIdList);
        fileIdList.addAll(imgIdList);
        if (CollectionUtils.isEmpty(fileIdList)) {
            return Collections.emptyList();
        }
        Map<Long, FileAddr> fileAddrMap = fileAddrDao.findAllById(fileIdList)
                .stream()
                .collect(Collectors.toMap(FileAddr::getId, Function.identity(), (a, b) -> b));

        List<AdminUploadVideoVo> adminUploadVideoVoList = new ArrayList<>(videoList.size());
        for (AdminUploadVideo adminUploadVideo : videoList) {
            AdminUploadVideoVo adminUploadVideoVo = UploadUserInfoUtil.getAdminUploadVideoVo(adminUploadVideo, fileAddrMap);
            adminUploadVideoVoList.add(adminUploadVideoVo);
        }
        return adminUploadVideoVoList;
    }

    public void deleteVideo(Long adminVideoId) {
        AdminUploadVideo adminUploadVideo = adminUploadVideoDao.findById(adminVideoId).orElse(null);
        if (Objects.isNull(adminUploadVideo)) {
            log.info("adminUploadVideo is null");
            return;
        }
        List<Long> fileIdList = new ArrayList<>();
        fileIdList.add(adminUploadVideo.getVideoId());
        fileIdList.add(adminUploadVideo.getVideoImgId());

        List<FileAddr> fileAddrList = fileAddrDao.findAllById(fileIdList);
        for (FileAddr fileAddr : fileAddrList) {
            WholeAddr wholeAddr = BaseDaoEnum.getByCode(fileAddr.getWholeAddr(), WholeAddr.class);
            if (wholeAddr == WholeAddr.LOCAL) {
                UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("http://localhost" + fileAddr.getAddr()).build();
                MultiValueMap<String, String> queryParams = uriComponents.getQueryParams();
                List<String> codeList = queryParams.get("code");
                if (CollectionUtils.isEmpty(codeList)) {
                    continue;
                }
                List<String> fileNameList = queryParams.get("fileName");
                if (CollectionUtils.isEmpty(fileNameList)) {
                    continue;
                }
                File file = userUploadInfoService.getFile(codeList.get(0), fileNameList.get(0));
                if (file.exists()) {
                    file.delete();
                }
            }
        }

        if (!CollectionUtils.isEmpty(fileAddrList)) {
            fileAddrDao.deleteAll(fileAddrList);
        }
        adminUploadVideoDao.delete(adminUploadVideo);
    }

    public void deleteVideo(String wechatCode) {
        AdminUploadVideo condition = new AdminUploadVideo();
        condition.setWechatCode(wechatCode);

        List<AdminUploadVideo> adminUploadVideoList = adminUploadVideoDao.findAll(Example.of(condition));
        if (CollectionUtils.isEmpty(adminUploadVideoList)) {
            return;
        }

        List<Long> fileIdList = new ArrayList<>();
        for (AdminUploadVideo adminUploadVideo : adminUploadVideoList) {
            Long videoId = adminUploadVideo.getVideoId();
            if (Objects.nonNull(videoId)) {
                fileIdList.add(videoId);
            }

            Long videoImgId = adminUploadVideo.getVideoImgId();
            if (Objects.nonNull(videoImgId)) {
                fileIdList.add(videoImgId);
            }

            adminUploadVideoDao.delete(adminUploadVideo);
        }

        List<FileAddr> fileAddrList = fileAddrDao.findAllById(fileIdList);
        fileAddrDao.deleteAll(fileAddrList);


    }

    public void increaseDownload(Long adminUploadId) {
        adminUploadVideoDao.findById(adminUploadId)
                .ifPresent(adminUploadVideo -> {
                    Integer userDownCount = adminUploadVideo.getUserDownCount();
                    userDownCount ++;
                    adminUploadVideo.setUserDownCount(userDownCount);
                    adminUploadVideoDao.save(adminUploadVideo);
                });
    }
}
