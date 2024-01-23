package com.github.ong.controller;

import com.github.ong.controller.api.UploadFileApiController;
import com.github.ong.dao.h2.FileAddrDao;
import com.github.ong.dao.h2.UserUploadInfoDao;
import com.github.ong.enums.db.WholeAddr;
import com.github.ong.model.h2.FileAddr;
import com.github.ong.model.h2.UserUploadInfo;
import com.github.ong.service.FileAddrService;
import com.github.ong.service.UserUploadInfoService;
import com.github.ong.utils.StringUtil;
import com.github.ong.utils.UploadUserInfoUtil;
import com.github.ong.utils.VideoFrameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.persistence.Column;
import java.io.File;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Resource
    private UserUploadInfoService userUploadInfoService;

    @Resource
    private UserUploadInfoDao userUploadInfoDao;

    @Resource
    private FileAddrDao fileAddrDao;

    @Resource
    private FileAddrService fileAddrService;

    @RequestMapping("/test1")
    public String test1(String hello) {
//        log.info("hello param is {}", hello);
//        return "hello " + hello;

        String imagePath = userUploadInfoService.getRootPath() + "/ADMIN_CAT_NO_ROLL/before1Frame.jpg";
        String videoPath = userUploadInfoService.getRootPath() + "/ADMIN_CAT_NO_ROLL/before1.mp4";
        boolean getFrameRes = VideoFrameUtil.getFrame(imagePath, videoPath);
        log.info("getFrameRes is {}", getFrameRes);
        return "OK";
    }

    @RequestMapping("/test2")
    public String test2() {
        UserUploadInfo condition = new UserUploadInfo();
        condition.setWechatCode(UploadUserInfoUtil.WECHAT_CODE_ADMIN);
        UserUploadInfo userUploadInfo = userUploadInfoDao.findOne(Example.of(condition)).orElse(null);
        if (Objects.isNull(userUploadInfo)) {
            return "EMPTY";
        }
        changeFile(userUploadInfo.getBeforeImg1());
        changeFile(userUploadInfo.getBeforeImg2());
        changeFile(userUploadInfo.getBeforeVideo1());
        changeFile(userUploadInfo.getBeforeVideoImg1());
        changeFile(userUploadInfo.getBeforeVideo2());
        changeFile(userUploadInfo.getBeforeVideoImg2());

        changeFile(userUploadInfo.getInstallImg1());
        changeFile(userUploadInfo.getInstallImg2());
        changeFile(userUploadInfo.getInstallVideo1());
        changeFile(userUploadInfo.getInstallVideoImg1());
        changeFile(userUploadInfo.getInstallVideo2());
        changeFile(userUploadInfo.getInstallVideoImg2());
        changeFile(userUploadInfo.getInstallVideo3());
        changeFile(userUploadInfo.getInstallVideoImg3());
        changeFile(userUploadInfo.getInstallVideo4());
        changeFile(userUploadInfo.getInstallVideoImg4());

        changeFile(userUploadInfo.getDisplayImg1());
        changeFile(userUploadInfo.getDisplayImg2());
        changeFile(userUploadInfo.getDisplayImg3());
        changeFile(userUploadInfo.getDisplayImg4());
        changeFile(userUploadInfo.getDisplayImg5());
        changeFile(userUploadInfo.getDisplayImg6());
        changeFile(userUploadInfo.getDisplayVideo1());
        changeFile(userUploadInfo.getDisplayVideoImg1());
        changeFile(userUploadInfo.getDisplayVideo2());
        changeFile(userUploadInfo.getDisplayVideoImg2());
        changeFile(userUploadInfo.getDisplayVideo3());
        changeFile(userUploadInfo.getDisplayVideoImg3());
        changeFile(userUploadInfo.getDisplayVideo4());
        changeFile(userUploadInfo.getDisplayVideoImg4());
        changeFile(userUploadInfo.getDisplayVideo5());
        changeFile(userUploadInfo.getDisplayVideoImg5());
        changeFile(userUploadInfo.getDisplayVideo6());
        changeFile(userUploadInfo.getDisplayVideoImg6());

        changeFile(userUploadInfo.getDisposeVideo1());
        changeFile(userUploadInfo.getDisposeVideoImg1());
        changeFile(userUploadInfo.getDisposeVideo2());
        changeFile(userUploadInfo.getDisposeVideoImg2());
        changeFile(userUploadInfo.getDisposeVideo3());
        changeFile(userUploadInfo.getDisposeVideoImg3());
        changeFile(userUploadInfo.getDisposeVideo4());
        changeFile(userUploadInfo.getDisposeVideoImg4());
        changeFile(userUploadInfo.getDisposeVideo5());
        changeFile(userUploadInfo.getDisposeVideoImg5());
        changeFile(userUploadInfo.getDisposeVideo6());
        changeFile(userUploadInfo.getDisposeVideoImg6());

        return "OK";
    }

    private void changeFile(Long fileId) {
        if (Objects.isNull(fileId)) {
            return;
        }
        FileAddr fileAddr = fileAddrDao.getReferenceById(fileId);
        if (WholeAddr.SSO.getCode().equals(fileAddr.getWholeAddr())) {
            return;
        }
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("http://localhost" + fileAddr.getAddr()).build();
        MultiValueMap<String, String> queryParams = uriComponents.getQueryParams();
        List<String> codeList = queryParams.get("code");
        if (CollectionUtils.isEmpty(codeList)) {
            return;
        }
        List<String> fileNameList = queryParams.get("fileName");
        if (CollectionUtils.isEmpty(fileNameList)) {
            return;
        }
        File file = userUploadInfoService.getFile(codeList.get(0), fileNameList.get(0));
        String fileKey = codeList.get(0) + "/" + fileNameList.get(0);
        fileAddrService.uploadSsoFile(file, fileKey);

        fileAddr.setWholeAddr(WholeAddr.SSO.getCode());
        fileAddr.setAddr(fileKey);

        fileAddrDao.save(fileAddr);
    }
}
