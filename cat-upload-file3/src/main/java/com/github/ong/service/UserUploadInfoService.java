package com.github.ong.service;

import com.github.ong.dao.h2.FileAddrDao;
import com.github.ong.dao.h2.UserUploadInfoDao;
import com.github.ong.enums.db.WholeAddr;
import com.github.ong.model.h2.FileAddr;
import com.github.ong.model.h2.UserUploadInfo;
import com.github.ong.utils.AliyunUtil;
import com.github.ong.vo.UserUploadInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserUploadInfoService {

    @Value("${file.root-path}")
    public String root_path;

    public String getRootPath() {
        return root_path;
    }

    @Resource
    private UserUploadInfoDao userUploadInfoDao;

    @Resource
    private FileAddrDao fileAddrDao;

    public UserUploadInfoVo getUploadInfo(String wechatCode) {
        UserUploadInfoVo resultVo = new UserUploadInfoVo();
        if (StringUtils.isBlank(wechatCode)) {
            return resultVo;
        }
        UserUploadInfo condition = new UserUploadInfo();
        condition.setWechatCode(wechatCode);

        UserUploadInfo userUploadInfo = userUploadInfoDao.findOne(Example.of(condition)).orElse(null);
        if (Objects.isNull(userUploadInfo)) {
            return resultVo;
        }
        resultVo.setUserUploadInfo(userUploadInfo);

        List<Long> fileIdList = new ArrayList<>();

        addFieldList(userUploadInfo, fileIdList);

        Map<Long, FileAddr> fileAddrMap = fileAddrDao.findAllById(fileIdList)
                .stream()
                .collect(Collectors.toMap(FileAddr::getId, Function.identity(), (a, b) -> b));

        setUserUploadInfoVo(fileAddrMap, userUploadInfo, resultVo);

        return resultVo;
    }

    private void addFieldList(UserUploadInfo userUploadInfo, List<Long> fileIdList) {
        addBefore(userUploadInfo, fileIdList);
        addInstall(userUploadInfo, fileIdList);
        addDisplay(userUploadInfo, fileIdList);
        addDispose(userUploadInfo, fileIdList);
    }

    private void addBefore(UserUploadInfo userUploadInfo, List<Long> fileIdList) {
        addBefore(userUploadInfo, fileIdList, true);
    }

    private void addBefore(UserUploadInfo userUploadInfo, List<Long> fileIdList, boolean includeVideoImg) {
        Optional.ofNullable(userUploadInfo.getBeforeImg1())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getBeforeImg2())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getBeforeVideo1())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getBeforeVideo2())
                .ifPresent(fileIdList::add);
        if (includeVideoImg) {
            Optional.ofNullable(userUploadInfo.getBeforeVideoImg1())
                    .ifPresent(fileIdList::add);
            Optional.ofNullable(userUploadInfo.getBeforeVideoImg2())
                    .ifPresent(fileIdList::add);
        }
    }

    private void addInstall(UserUploadInfo userUploadInfo, List<Long> fileIdList) {
        addInstall(userUploadInfo, fileIdList, true);
    }

    private void addInstall(UserUploadInfo userUploadInfo, List<Long> fileIdList, boolean includeVideoImg) {
        Optional.ofNullable(userUploadInfo.getInstallVideo1())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getInstallVideo2())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getInstallVideo3())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getInstallVideo4())
                .ifPresent(fileIdList::add);

        if (includeVideoImg) {
            Optional.ofNullable(userUploadInfo.getInstallVideoImg1())
                    .ifPresent(fileIdList::add);
            Optional.ofNullable(userUploadInfo.getInstallVideoImg2())
                    .ifPresent(fileIdList::add);
            Optional.ofNullable(userUploadInfo.getInstallVideoImg3())
                    .ifPresent(fileIdList::add);
            Optional.ofNullable(userUploadInfo.getInstallVideoImg4())
                    .ifPresent(fileIdList::add);
        }
    }

    private void addDisplay(UserUploadInfo userUploadInfo, List<Long> fileIdList) {
        addDisplay(userUploadInfo, fileIdList, true);
    }

    private void addDisplay(UserUploadInfo userUploadInfo, List<Long> fileIdList, boolean includeVideoImg) {
        Optional.ofNullable(userUploadInfo.getDisplayImg1())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisplayImg2())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisplayImg3())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisplayImg4())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisplayImg5())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisplayImg6())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisplayVideo1())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisplayVideo2())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisplayVideo3())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisplayVideo4())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisplayVideo5())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisplayVideo6())
                .ifPresent(fileIdList::add);

        if (includeVideoImg) {
            Optional.ofNullable(userUploadInfo.getDisplayVideoImg1())
                    .ifPresent(fileIdList::add);
            Optional.ofNullable(userUploadInfo.getDisplayVideoImg2())
                    .ifPresent(fileIdList::add);
            Optional.ofNullable(userUploadInfo.getDisplayVideoImg3())
                    .ifPresent(fileIdList::add);
            Optional.ofNullable(userUploadInfo.getDisplayVideoImg4())
                    .ifPresent(fileIdList::add);
            Optional.ofNullable(userUploadInfo.getDisplayVideoImg5())
                    .ifPresent(fileIdList::add);
            Optional.ofNullable(userUploadInfo.getDisplayVideoImg6())
                    .ifPresent(fileIdList::add);
        }
    }

    private void addDispose(UserUploadInfo userUploadInfo, List<Long> fileIdList) {
        addDispose(userUploadInfo, fileIdList, true);
    }

    private void addDispose(UserUploadInfo userUploadInfo, List<Long> fileIdList, boolean includeVideoImg) {
        Optional.ofNullable(userUploadInfo.getDisposeVideo1())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisposeVideo2())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisposeVideo3())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisposeVideo4())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisposeVideo5())
                .ifPresent(fileIdList::add);
        Optional.ofNullable(userUploadInfo.getDisposeVideo6())
                .ifPresent(fileIdList::add);
        if (includeVideoImg) {
            Optional.ofNullable(userUploadInfo.getDisposeVideoImg1())
                    .ifPresent(fileIdList::add);
            Optional.ofNullable(userUploadInfo.getDisposeVideoImg2())
                    .ifPresent(fileIdList::add);
            Optional.ofNullable(userUploadInfo.getDisposeVideoImg3())
                    .ifPresent(fileIdList::add);
            Optional.ofNullable(userUploadInfo.getDisposeVideoImg4())
                    .ifPresent(fileIdList::add);
            Optional.ofNullable(userUploadInfo.getDisposeVideoImg5())
                    .ifPresent(fileIdList::add);
            Optional.ofNullable(userUploadInfo.getDisposeVideoImg6())
                    .ifPresent(fileIdList::add);
        }
    }

    private void setUserUploadInfoVo(Map<Long, FileAddr> fileAddrMap,
                                     UserUploadInfo userUploadInfo,
                                     UserUploadInfoVo resultVo) {
        setBefore(fileAddrMap, userUploadInfo, resultVo);
        setInstall(fileAddrMap, userUploadInfo, resultVo);
        setDisplay(fileAddrMap, userUploadInfo, resultVo);
        setDispose(fileAddrMap, userUploadInfo, resultVo);
    }

    private void setBefore(Map<Long, FileAddr> fileAddrMap, UserUploadInfo userUploadInfo, UserUploadInfoVo resultVo) {
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getBeforeImg1()))
                .ifPresent( fileAddr -> {
                    if (WholeAddr.SSO.getCode().equals(fileAddr.getWholeAddr())) {
                        resultVo.setBeforeImg1Url(AliyunUtil.SSO_ROOT + fileAddr.getAddr());
                    } else {
                        resultVo.setBeforeImg1Url(fileAddr.getAddr());
                    }
                });
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getBeforeImg2()))
                .ifPresent( fileAddr -> resultVo.setBeforeImg2Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getBeforeVideo1()))
                .ifPresent( fileAddr -> resultVo.setBeforeVideo1Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getBeforeVideoImg1()))
                .ifPresent( fileAddr -> resultVo.setBeforeVideoImg1Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getBeforeVideo2()))
                .ifPresent( fileAddr -> resultVo.setBeforeVideo2Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getBeforeVideoImg2()))
                .ifPresent( fileAddr -> resultVo.setBeforeVideoImg2Url(getAddr(fileAddr)));
    }

    private void setInstall(Map<Long, FileAddr> fileAddrMap, UserUploadInfo userUploadInfo, UserUploadInfoVo resultVo) {
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getInstallVideo1()))
                .ifPresent( fileAddr -> resultVo.setInstallVideo1Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getInstallVideoImg1()))
                .ifPresent( fileAddr -> resultVo.setInstallVideoImg1Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getInstallVideo2()))
                .ifPresent( fileAddr -> resultVo.setInstallVideo2Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getInstallVideoImg2()))
                .ifPresent( fileAddr -> resultVo.setInstallVideoImg2Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getInstallVideo3()))
                .ifPresent( fileAddr -> resultVo.setInstallVideo3Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getInstallVideoImg3()))
                .ifPresent( fileAddr -> resultVo.setInstallVideoImg3Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getInstallVideo4()))
                .ifPresent( fileAddr -> resultVo.setInstallVideo4Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getInstallVideoImg4()))
                .ifPresent( fileAddr -> resultVo.setInstallVideoImg4Url(getAddr(fileAddr)));
    }

    private void setDisplay(Map<Long, FileAddr> fileAddrMap, UserUploadInfo userUploadInfo, UserUploadInfoVo resultVo) {
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayImg1()))
                .ifPresent( fileAddr -> resultVo.setDisplayImg1Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayImg2()))
                .ifPresent( fileAddr -> resultVo.setDisplayImg2Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayImg3()))
                .ifPresent( fileAddr -> resultVo.setDisplayImg3Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayImg4()))
                .ifPresent( fileAddr -> resultVo.setDisplayImg4Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayImg5()))
                .ifPresent( fileAddr -> resultVo.setDisplayImg5Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayImg6()))
                .ifPresent( fileAddr -> resultVo.setDisplayImg6Url(getAddr(fileAddr)));

        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayVideo1()))
                .ifPresent(fileAddr -> resultVo.setDisplayVideo1Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayVideo2()))
                .ifPresent(fileAddr -> resultVo.setDisplayVideo2Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayVideo3()))
                .ifPresent(fileAddr -> resultVo.setDisplayVideo3Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayVideo4()))
                .ifPresent(fileAddr -> resultVo.setDisplayVideo4Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayVideo5()))
                .ifPresent(fileAddr -> resultVo.setDisplayVideo5Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayVideo6()))
                .ifPresent(fileAddr -> resultVo.setDisplayVideo6Url(getAddr(fileAddr)));

        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayVideoImg1()))
                .ifPresent(fileAddr -> resultVo.setDisplayVideoImg1Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayVideoImg2()))
                .ifPresent(fileAddr -> resultVo.setDisplayVideoImg2Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayVideoImg3()))
                .ifPresent(fileAddr -> resultVo.setDisplayVideoImg3Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayVideoImg4()))
                .ifPresent(fileAddr -> resultVo.setDisplayVideoImg4Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayVideoImg5()))
                .ifPresent(fileAddr -> resultVo.setDisplayVideoImg5Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisplayVideoImg6()))
                .ifPresent(fileAddr -> resultVo.setDisplayVideoImg6Url(getAddr(fileAddr)));
    }

    private void setDispose(Map<Long, FileAddr> fileAddrMap, UserUploadInfo userUploadInfo, UserUploadInfoVo resultVo) {
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisposeVideo1()))
                .ifPresent(fileAddr -> resultVo.setDisposeVideo1Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisposeVideo2()))
                .ifPresent(fileAddr -> resultVo.setDisposeVideo2Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisposeVideo3()))
                .ifPresent(fileAddr -> resultVo.setDisposeVideo3Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisposeVideo4()))
                .ifPresent(fileAddr -> resultVo.setDisposeVideo4Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisposeVideo5()))
                .ifPresent(fileAddr -> resultVo.setDisposeVideo5Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisposeVideo6()))
                .ifPresent(fileAddr -> resultVo.setDisposeVideo6Url(getAddr(fileAddr)));

        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisposeVideoImg1()))
                .ifPresent(fileAddr -> resultVo.setDisposeVideoImg1Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisposeVideoImg2()))
                .ifPresent(fileAddr -> resultVo.setDisposeVideoImg2Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisposeVideoImg3()))
                .ifPresent(fileAddr -> resultVo.setDisposeVideoImg3Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisposeVideoImg4()))
                .ifPresent(fileAddr -> resultVo.setDisposeVideoImg4Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisposeVideoImg5()))
                .ifPresent(fileAddr -> resultVo.setDisposeVideoImg5Url(getAddr(fileAddr)));
        Optional.ofNullable(fileAddrMap.get(userUploadInfo.getDisposeVideoImg6()))
                .ifPresent(fileAddr -> resultVo.setDisposeVideoImg6Url(getAddr(fileAddr)));
    }

    public String getAddr(FileAddr fileAddr) {
        if (WholeAddr.SSO.getCode().equals(fileAddr.getWholeAddr())) {
            return AliyunUtil.SSO_ROOT + fileAddr.getAddr();
        }
        return fileAddr.getAddr();
    }
}
