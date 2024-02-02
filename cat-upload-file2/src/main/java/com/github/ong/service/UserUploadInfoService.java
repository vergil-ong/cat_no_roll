package com.github.ong.service;

import com.github.ong.config.http.OssClientConfig;
import com.github.ong.dao.h2.AdminUploadVideoDao;
import com.github.ong.dao.h2.FileAddrDao;
import com.github.ong.dao.h2.UserUploadInfoDao;
import com.github.ong.enums.biz.UploadFileIndex;
import com.github.ong.enums.db.WholeAddr;
import com.github.ong.model.h2.AdminUploadVideo;
import com.github.ong.model.h2.FileAddr;
import com.github.ong.model.h2.UserUploadInfo;
import com.github.ong.model.sso.ZipInfo;
import com.github.ong.qo.admin.UploadQo;
import com.github.ong.utils.AliyunUtil;
import com.github.ong.utils.BeanUtil;
import com.github.ong.utils.StringUtil;
import com.github.ong.utils.UploadUserInfoUtil;
import com.github.ong.vo.AdminUploadVideoVo;
import com.github.ong.vo.UserUploadInfoVo;
import com.github.ong.vo.common.TablePageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.io.File;
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

    public File getFile(String code, String fileName) {
        return new File(new File(getRootPath(), code), fileName);
    }

    @Resource
    private UserUploadInfoDao userUploadInfoDao;

    @Resource
    private FileAddrDao fileAddrDao;

    @Resource
    private AdminUploadVideoDao adminUploadVideoDao;

    @Resource
    private SsoService ssoService;

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

    private void setUserUploadInfoVo(Map<Long, FileAddr> fileAddrMap,
                                     UserUploadInfo userUploadInfo,
                                     UserUploadInfoVo resultVo) {
        setBefore(fileAddrMap, userUploadInfo, resultVo);
        setInstall(fileAddrMap, userUploadInfo, resultVo);
        setDisplay(fileAddrMap, userUploadInfo, resultVo);
        setDispose(fileAddrMap, userUploadInfo, resultVo);
    }

    public TablePageVo<UserUploadInfoVo> pageUploadInfo(UploadQo uploadQo) {
        String wechatCode = uploadQo.getWechatCode();

        Page<UserUploadInfo> userUploadInfoPage = userUploadInfoDao.findAll(((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(criteriaBuilder.notEqual(root.get("wechatCode"), UploadUserInfoUtil.WECHAT_CODE_ADMIN));
            if (StringUtils.isNotBlank(wechatCode)) {
                predicateList.add(criteriaBuilder.equal(root.get("wechatCode"), wechatCode));
            }
            return criteriaQuery.where(predicateList.toArray(new Predicate[0])).getRestriction();
        }), PageRequest.of(uploadQo.getPage(), uploadQo.getLimit()));
        TablePageVo<UserUploadInfoVo> tablePageVo = new TablePageVo<>();
        List<UserUploadInfo> userUploadInfoList = userUploadInfoPage.getContent();
        if (CollectionUtils.isEmpty(userUploadInfoList)) {
            tablePageVo.setRows(Collections.emptyList());
            tablePageVo.setTotal((int) userUploadInfoPage.getTotalElements());
            return tablePageVo;
        }

        List<Long> fileIdList = new ArrayList<>();
        List<String> wechatCodeList = new ArrayList<>();
        for (UserUploadInfo userUploadInfo : userUploadInfoList) {
            addFieldList(userUploadInfo, fileIdList);
            wechatCodeList.add(userUploadInfo.getWechatCode());
        }

        Map<String, List<AdminUploadVideo>> adminUploadVideoWechatMap = adminUploadVideoDao.findAll((root, criteriaQuery, criteriaBuilder) -> {
                    List<Predicate> predicateList = new ArrayList<>();
                    if (!CollectionUtils.isEmpty(wechatCodeList)) {
                        CriteriaBuilder.In<Object> wechatCodeIn = criteriaBuilder.in(root.get("wechatCode"));
                        for (String wechatCodeParam : wechatCodeList) {
                            wechatCodeIn.value(wechatCodeParam);
                        }
                        predicateList.add(wechatCodeIn);
                    }

                    return criteriaQuery.where(predicateList.toArray(new Predicate[0])).getRestriction();
                }).stream()
                .map(adminUploadVideo -> {
                    fileIdList.add(adminUploadVideo.getVideoImgId());
                    fileIdList.add(adminUploadVideo.getVideoId());
                    return adminUploadVideo;
                })
                .collect(Collectors.groupingBy(AdminUploadVideo::getWechatCode));


        Map<Long, FileAddr> fileAddrMap = fileAddrDao.findAllById(fileIdList)
                .stream()
                .collect(Collectors.toMap(FileAddr::getId, Function.identity(), (a, b) -> b));
        List<UserUploadInfoVo> userUploadInfoVoList = new ArrayList<>();
        for (UserUploadInfo userUploadInfo : userUploadInfoList) {
            UserUploadInfoVo userUploadInfoVo = new UserUploadInfoVo();
            userUploadInfoVo.setUserUploadInfo(userUploadInfo);
            setUserUploadInfoVo(fileAddrMap, userUploadInfo, userUploadInfoVo);
            userUploadInfoVoList.add(userUploadInfoVo);

            String wechatCodeItem = userUploadInfo.getWechatCode();
            List<AdminUploadVideo> adminUploadVideoList = adminUploadVideoWechatMap.get(wechatCodeItem);
            List<AdminUploadVideoVo> adminUploadVideoVoList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(adminUploadVideoList)) {
                for (AdminUploadVideo adminUploadVideo : adminUploadVideoList) {
                    AdminUploadVideoVo adminUploadVideoVo = UploadUserInfoUtil.getAdminUploadVideoVo(adminUploadVideo, fileAddrMap);
                    adminUploadVideoVoList.add(adminUploadVideoVo);
                }
                userUploadInfoVo.setAdminUploadVideoVoList(adminUploadVideoVoList);
            }
        }

        tablePageVo.setRows(userUploadInfoVoList);
        tablePageVo.setTotal((int) userUploadInfoPage.getTotalElements());

        return tablePageVo;
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
    public void updateUserUploadInfo(UserUploadInfo userUploadInfo, String wechatCode) {
        UserUploadInfo condition = new UserUploadInfo();
        condition.setWechatCode(wechatCode);

        UserUploadInfo userUploadInfoDB = userUploadInfoDao.findOne(Example.of(condition)).orElse(null);
        if (Objects.isNull(userUploadInfoDB)) {
            userUploadInfo.setWechatCode(wechatCode);
            userUploadInfoDao.save(userUploadInfo);
            return;
        }
        BeanUtil.copyPropertiesIgnoreNull(userUploadInfo, userUploadInfoDB);
        userUploadInfoDao.save(userUploadInfoDB);
    }

    public void deleteImage(
            UploadFileIndex uploadFileIndex,
            String wechatCode) {
        if (Objects.isNull(uploadFileIndex)) {
            log.info("uploadFileIndex is null");
            return;
        }
        UserUploadInfo condition = new UserUploadInfo();
        condition.setWechatCode(wechatCode);
        UserUploadInfo userUploadInfoDB = userUploadInfoDao.findOne(Example.of(condition)).orElse(null);
        if (Objects.isNull(userUploadInfoDB)) {
            return;
        }
        switch (uploadFileIndex) {
            case BEFORE_IMG_1:
                Long beforeImg1 = userUploadInfoDB.getBeforeImg1();
                if (Objects.isNull(beforeImg1)) {
                    break;
                }
                userUploadInfoDB.setBeforeImg1(null);
                fileAddrDao.deleteById(beforeImg1);
                break;
            case BEFORE_IMG_2:
                Long beforeImg2 = userUploadInfoDB.getBeforeImg2();
                if (Objects.isNull(beforeImg2)) {
                    break;
                }
                userUploadInfoDB.setBeforeImg2(null);
                fileAddrDao.deleteById(beforeImg2);
                break;
            case BEFORE_VIDEO_1:
                Long beforeVideo1 = userUploadInfoDB.getBeforeVideo1();
                if (Objects.nonNull(beforeVideo1)) {
                    fileAddrDao.deleteById(beforeVideo1);
                    userUploadInfoDB.setBeforeVideo1(null);
                }
                Long beforeVideoImg1 = userUploadInfoDB.getBeforeVideoImg1();
                if (Objects.nonNull(beforeVideoImg1)) {
                    fileAddrDao.deleteById(beforeVideoImg1);
                    userUploadInfoDB.setBeforeVideoImg1(null);
                }
                break;
            case BEFORE_VIDEO_2:
                Long beforeVideo2 = userUploadInfoDB.getBeforeVideo2();
                if (Objects.nonNull(beforeVideo2)) {
                    fileAddrDao.deleteById(beforeVideo2);
                    userUploadInfoDB.setBeforeVideo2(null);
                }
                Long beforeVideoImg2 = userUploadInfoDB.getBeforeVideoImg2();
                if (Objects.nonNull(beforeVideoImg2)) {
                    fileAddrDao.deleteById(beforeVideoImg2);
                    userUploadInfoDB.setBeforeVideoImg2(null);
                }
                break;
            case INSTALL_VIDEO_1:
                Long installVideo1 = userUploadInfoDB.getInstallVideo1();
                if (Objects.nonNull(installVideo1)) {
                    fileAddrDao.deleteById(installVideo1);
                    userUploadInfoDB.setInstallVideo1(null);
                }
                Long installVideoImg1 = userUploadInfoDB.getInstallVideoImg1();
                if (Objects.nonNull(installVideoImg1)) {
                    fileAddrDao.deleteById(installVideoImg1);
                    userUploadInfoDB.setInstallVideoImg1(null);
                }
                break;
            case INSTALL_VIDEO_2:
                Long installVideo2 = userUploadInfoDB.getInstallVideo2();
                if (Objects.nonNull(installVideo2)) {
                    fileAddrDao.deleteById(installVideo2);
                    userUploadInfoDB.setInstallVideo2(null);
                }
                Long installVideoImg2 = userUploadInfoDB.getInstallVideoImg2();
                if (Objects.nonNull(installVideoImg2)) {
                    fileAddrDao.deleteById(installVideoImg2);
                    userUploadInfoDB.setInstallVideoImg2(null);
                }
                break;
            case INSTALL_VIDEO_3:
                Long installVideo3 = userUploadInfoDB.getInstallVideo3();
                if (Objects.nonNull(installVideo3)) {
                    fileAddrDao.deleteById(installVideo3);
                    userUploadInfoDB.setInstallVideo3(null);
                }
                Long installVideoImg3 = userUploadInfoDB.getInstallVideoImg3();
                if (Objects.nonNull(installVideoImg3)) {
                    fileAddrDao.deleteById(installVideoImg3);
                    userUploadInfoDB.setInstallVideoImg3(null);
                }
                break;
            case INSTALL_VIDEO_4:
                Long installVideo4 = userUploadInfoDB.getInstallVideo4();
                if (Objects.nonNull(installVideo4)) {
                    fileAddrDao.deleteById(installVideo4);
                    userUploadInfoDB.setInstallVideo4(null);
                }
                Long installVideoImg4 = userUploadInfoDB.getInstallVideoImg4();
                if (Objects.nonNull(installVideoImg4)) {
                    fileAddrDao.deleteById(installVideoImg4);
                    userUploadInfoDB.setInstallVideoImg4(null);
                }
                break;
            case DISPLAY_IMG_1:
                Long displayImg1 = userUploadInfoDB.getDisplayImg1();
                if (Objects.nonNull(displayImg1)) {
                    fileAddrDao.deleteById(displayImg1);
                    userUploadInfoDB.setDisplayImg1(displayImg1);
                }
                break;
            case DISPLAY_IMG_2:
                Long displayImg2 = userUploadInfoDB.getDisplayImg2();
                if (Objects.nonNull(displayImg2)) {
                    fileAddrDao.deleteById(displayImg2);
                    userUploadInfoDB.setDisplayImg1(displayImg2);
                }
                break;
            case DISPLAY_IMG_3:
                Long displayImg3 = userUploadInfoDB.getDisplayImg3();
                if (Objects.nonNull(displayImg3)) {
                    fileAddrDao.deleteById(displayImg3);
                    userUploadInfoDB.setDisplayImg3(displayImg3);
                }
                break;
            case DISPLAY_IMG_4:
                Long displayImg4 = userUploadInfoDB.getDisplayImg4();
                if (Objects.nonNull(displayImg4)) {
                    fileAddrDao.deleteById(displayImg4);
                    userUploadInfoDB.setDisplayImg4(displayImg4);
                }
                break;
            case DISPLAY_IMG_5:
                Long displayImg5 = userUploadInfoDB.getDisplayImg5();
                if (Objects.nonNull(displayImg5)) {
                    fileAddrDao.deleteById(displayImg5);
                    userUploadInfoDB.setDisplayImg5(displayImg5);
                }
                break;
            case DISPLAY_IMG_6:
                Long displayImg6 = userUploadInfoDB.getDisplayImg6();
                if (Objects.nonNull(displayImg6)) {
                    fileAddrDao.deleteById(displayImg6);
                    userUploadInfoDB.setDisplayImg6(displayImg6);
                }
                break;
            case DISPLAY_VIDEO_1:
                Long displayVideo1 = userUploadInfoDB.getDisplayVideo1();
                if (Objects.nonNull(displayVideo1)) {
                    fileAddrDao.deleteById(displayVideo1);
                    userUploadInfoDB.setDisplayVideo1(null);
                }
                Long displayVideoImg1 = userUploadInfoDB.getDisplayVideoImg1();
                if (Objects.nonNull(displayVideoImg1)) {
                    fileAddrDao.deleteById(displayVideoImg1);
                    userUploadInfoDB.setDisplayVideoImg1(null);
                }
                break;
            case DISPLAY_VIDEO_2:
                Long displayVideo2 = userUploadInfoDB.getDisplayVideo2();
                if (Objects.nonNull(displayVideo2)) {
                    fileAddrDao.deleteById(displayVideo2);
                    userUploadInfoDB.setDisplayVideo2(null);
                }
                Long displayVideoImg2 = userUploadInfoDB.getDisplayVideoImg2();
                if (Objects.nonNull(displayVideoImg2)) {
                    fileAddrDao.deleteById(displayVideoImg2);
                    userUploadInfoDB.setDisplayVideoImg2(null);
                }
                break;
            case DISPLAY_VIDEO_3:
                Long displayVideo3 = userUploadInfoDB.getDisplayVideo3();
                if (Objects.nonNull(displayVideo3)) {
                    fileAddrDao.deleteById(displayVideo3);
                    userUploadInfoDB.setDisplayVideo3(null);
                }
                Long displayVideoImg3 = userUploadInfoDB.getDisplayVideoImg3();
                if (Objects.nonNull(displayVideoImg3)) {
                    fileAddrDao.deleteById(displayVideoImg3);
                    userUploadInfoDB.setDisplayVideoImg3(null);
                }
                break;
            case DISPLAY_VIDEO_4:
                Long displayVideo4 = userUploadInfoDB.getDisplayVideo4();
                if (Objects.nonNull(displayVideo4)) {
                    fileAddrDao.deleteById(displayVideo4);
                    userUploadInfoDB.setDisplayVideo4(null);
                }
                Long displayVideoImg4 = userUploadInfoDB.getDisplayVideoImg4();
                if (Objects.nonNull(displayVideoImg4)) {
                    fileAddrDao.deleteById(displayVideoImg4);
                    userUploadInfoDB.setDisplayVideoImg4(null);
                }
                break;
            case DISPLAY_VIDEO_5:
                Long displayVideo5 = userUploadInfoDB.getDisplayVideo5();
                if (Objects.nonNull(displayVideo5)) {
                    fileAddrDao.deleteById(displayVideo5);
                    userUploadInfoDB.setDisplayVideo5(null);
                }
                Long displayVideoImg5 = userUploadInfoDB.getDisplayVideoImg5();
                if (Objects.nonNull(displayVideoImg5)) {
                    fileAddrDao.deleteById(displayVideoImg5);
                    userUploadInfoDB.setDisplayVideoImg5(null);
                }
                break;
            case DISPLAY_VIDEO_6:
                Long displayVideo6 = userUploadInfoDB.getDisplayVideo6();
                if (Objects.nonNull(displayVideo6)) {
                    fileAddrDao.deleteById(displayVideo6);
                    userUploadInfoDB.setDisplayVideo6(null);
                }
                Long displayVideoImg6 = userUploadInfoDB.getDisplayVideoImg6();
                if (Objects.nonNull(displayVideoImg6)) {
                    fileAddrDao.deleteById(displayVideoImg6);
                    userUploadInfoDB.setDisplayVideoImg6(null);
                }
                break;
            case DISPOSE_VIDEO_1:
                Long disposeVideo1 = userUploadInfoDB.getDisposeVideo1();
                if (Objects.nonNull(disposeVideo1)) {
                    fileAddrDao.deleteById(disposeVideo1);
                    userUploadInfoDB.setDisposeVideo1(disposeVideo1);
                }
                Long disposeVideoImg1 = userUploadInfoDB.getDisposeVideoImg1();
                if (Objects.nonNull(disposeVideoImg1)) {
                    fileAddrDao.deleteById(disposeVideoImg1);
                    userUploadInfoDB.setDisposeVideoImg1(disposeVideoImg1);
                }
                break;
            case DISPOSE_VIDEO_2:
                Long disposeVideo2 = userUploadInfoDB.getDisposeVideo2();
                if (Objects.nonNull(disposeVideo2)) {
                    fileAddrDao.deleteById(disposeVideo2);
                    userUploadInfoDB.setDisposeVideo2(disposeVideo2);
                }
                Long disposeVideoImg2 = userUploadInfoDB.getDisposeVideoImg2();
                if (Objects.nonNull(disposeVideoImg2)) {
                    fileAddrDao.deleteById(disposeVideoImg2);
                    userUploadInfoDB.setDisposeVideoImg2(disposeVideoImg2);
                }
                break;
            case DISPOSE_VIDEO_3:
                Long disposeVideo3 = userUploadInfoDB.getDisposeVideo3();
                if (Objects.nonNull(disposeVideo3)) {
                    fileAddrDao.deleteById(disposeVideo3);
                    userUploadInfoDB.setDisposeVideo3(disposeVideo3);
                }
                Long disposeVideoImg3 = userUploadInfoDB.getDisposeVideoImg3();
                if (Objects.nonNull(disposeVideoImg3)) {
                    fileAddrDao.deleteById(disposeVideoImg3);
                    userUploadInfoDB.setDisposeVideoImg3(disposeVideoImg3);
                }
                break;
            case DISPOSE_VIDEO_4:
                Long disposeVideo4 = userUploadInfoDB.getDisposeVideo4();
                if (Objects.nonNull(disposeVideo4)) {
                    fileAddrDao.deleteById(disposeVideo4);
                    userUploadInfoDB.setDisposeVideo4(disposeVideo4);
                }
                Long disposeVideoImg4 = userUploadInfoDB.getDisposeVideoImg4();
                if (Objects.nonNull(disposeVideoImg4)) {
                    fileAddrDao.deleteById(disposeVideoImg4);
                    userUploadInfoDB.setDisposeVideoImg4(disposeVideoImg4);
                }
                break;
            case DISPOSE_VIDEO_5:
                Long disposeVideo5 = userUploadInfoDB.getDisposeVideo5();
                if (Objects.nonNull(disposeVideo5)) {
                    fileAddrDao.deleteById(disposeVideo5);
                    userUploadInfoDB.setDisposeVideo5(disposeVideo5);
                }
                Long disposeVideoImg5 = userUploadInfoDB.getDisposeVideoImg5();
                if (Objects.nonNull(disposeVideoImg5)) {
                    fileAddrDao.deleteById(disposeVideoImg5);
                    userUploadInfoDB.setDisposeVideoImg5(disposeVideoImg5);
                }
                break;
            case DISPOSE_VIDEO_6:
                Long disposeVideo6 = userUploadInfoDB.getDisposeVideo6();
                if (Objects.nonNull(disposeVideo6)) {
                    fileAddrDao.deleteById(disposeVideo6);
                    userUploadInfoDB.setDisposeVideo6(disposeVideo6);
                }
                Long disposeVideoImg6 = userUploadInfoDB.getDisposeVideoImg6();
                if (Objects.nonNull(disposeVideoImg6)) {
                    fileAddrDao.deleteById(disposeVideoImg6);
                    userUploadInfoDB.setDisposeVideoImg6(disposeVideoImg6);
                }
                break;
            default:
                break;
        }
        userUploadInfoDao.save(userUploadInfoDB);
    }

    public void batchDeleteVideo(UploadQo uploadQo) {
        List<String> checkIdList = uploadQo.getCheckIdList();
        if (CollectionUtils.isEmpty(checkIdList)) {
            return;
        }

        UserUploadInfo condition = new UserUploadInfo();
        List<Long> fileIdList = new ArrayList<>();

        for (String checkId : checkIdList) {
            if (StringUtils.contains(checkId, UploadUserInfoUtil.BEFORE)) {
                String wechatCode = checkId.replaceFirst(UploadUserInfoUtil.BEFORE, StringUtil.BLANK);
                condition.setWechatCode(wechatCode);
                userUploadInfoDao.findOne(Example.of(condition))
                        .ifPresent( userUploadInfo -> {
                            addBefore(userUploadInfo, fileIdList);
                            if (!CollectionUtils.isEmpty(fileIdList)) {
                                batchDeleteFileAddr(fileIdList);
                            }
                            userUploadInfo.setBeforeImg1(null);
                            userUploadInfo.setBeforeImg2(null);
                            userUploadInfo.setBeforeVideo1(null);
                            userUploadInfo.setBeforeVideo2(null);
                            userUploadInfo.setBeforeVideoImg1(null);
                            userUploadInfo.setBeforeVideoImg2(null);
                            userUploadInfoDao.save(userUploadInfo);
                        });
            } else if (StringUtils.contains(checkId, UploadUserInfoUtil.INSTALL)) {
                String wechatCode = checkId.replaceFirst(UploadUserInfoUtil.INSTALL, StringUtil.BLANK);
                condition.setWechatCode(wechatCode);
                userUploadInfoDao.findOne(Example.of(condition))
                        .ifPresent( userUploadInfo -> {
                            addInstall(userUploadInfo, fileIdList);
                            if (!CollectionUtils.isEmpty(fileIdList)) {
                                batchDeleteFileAddr(fileIdList);
                            }
                            userUploadInfo.setInstallImg1(null);
                            userUploadInfo.setInstallImg2(null);
                            userUploadInfo.setInstallVideo1(null);
                            userUploadInfo.setInstallVideo2(null);
                            userUploadInfo.setInstallVideo3(null);
                            userUploadInfo.setInstallVideo4(null);
                            userUploadInfo.setInstallVideoImg1(null);
                            userUploadInfo.setInstallVideoImg2(null);
                            userUploadInfo.setInstallVideoImg3(null);
                            userUploadInfo.setInstallVideoImg4(null);
                            userUploadInfoDao.save(userUploadInfo);
                        });
            } else if (StringUtils.contains(checkId, UploadUserInfoUtil.DISPLAY)) {
                String wechatCode = checkId.replaceFirst(UploadUserInfoUtil.DISPLAY, StringUtil.BLANK);
                condition.setWechatCode(wechatCode);
                userUploadInfoDao.findOne(Example.of(condition))
                        .ifPresent( userUploadInfo -> {
                            addDisplay(userUploadInfo, fileIdList);
                            if (!CollectionUtils.isEmpty(fileIdList)) {
                                batchDeleteFileAddr(fileIdList);
                            }
                            userUploadInfo.setDisplayImg1(null);
                            userUploadInfo.setDisplayImg2(null);
                            userUploadInfo.setDisplayImg3(null);
                            userUploadInfo.setDisplayImg4(null);
                            userUploadInfo.setDisplayImg5(null);
                            userUploadInfo.setDisplayImg6(null);
                            userUploadInfo.setDisplayVideo1(null);
                            userUploadInfo.setDisplayVideo2(null);
                            userUploadInfo.setDisplayVideo3(null);
                            userUploadInfo.setDisplayVideo4(null);
                            userUploadInfo.setDisplayVideo5(null);
                            userUploadInfo.setDisplayVideo6(null);
                            userUploadInfo.setDisplayVideoImg1(null);
                            userUploadInfo.setDisplayVideoImg2(null);
                            userUploadInfo.setDisplayVideoImg3(null);
                            userUploadInfo.setDisplayVideoImg4(null);
                            userUploadInfo.setDisplayVideoImg5(null);
                            userUploadInfo.setDisplayVideoImg6(null);
                            userUploadInfoDao.save(userUploadInfo);
                        });
            } else if (StringUtils.contains(checkId, UploadUserInfoUtil.DISPOSE)) {
                String wechatCode = checkId.replaceFirst(UploadUserInfoUtil.DISPOSE, StringUtil.BLANK);
                condition.setWechatCode(wechatCode);
                userUploadInfoDao.findOne(Example.of(condition))
                        .ifPresent( userUploadInfo -> {
                            addDispose(userUploadInfo, fileIdList);
                            if (!CollectionUtils.isEmpty(fileIdList)) {
                                batchDeleteFileAddr(fileIdList);
                            }
                            userUploadInfo.setDisposeVideo1(null);
                            userUploadInfo.setDisposeVideo2(null);
                            userUploadInfo.setDisposeVideo3(null);
                            userUploadInfo.setDisposeVideo4(null);
                            userUploadInfo.setDisposeVideo5(null);
                            userUploadInfo.setDisposeVideo6(null);
                            userUploadInfo.setDisposeVideoImg1(null);
                            userUploadInfo.setDisposeVideoImg2(null);
                            userUploadInfo.setDisposeVideoImg3(null);
                            userUploadInfo.setDisposeVideoImg4(null);
                            userUploadInfo.setDisposeVideoImg5(null);
                            userUploadInfo.setDisposeVideoImg6(null);
                            userUploadInfoDao.save(userUploadInfo);
                        });
            }
        }
    }

    private void batchDeleteFileAddr(List<Long> fieldList) {
        if (CollectionUtils.isEmpty(fieldList)) {
            return;
        }
        List<FileAddr> fileAddrList = fileAddrDao.findAllById(fieldList);
        fileAddrDao.deleteAll(fileAddrList);
    }

    public void zipUserUploadInfo() {
        UserUploadInfo condition = new UserUploadInfo();
        condition.setFileChange(1);
        List<UserUploadInfo> userUploadInfoList = userUploadInfoDao.findAll(Example.of(condition));
        if (CollectionUtils.isEmpty(userUploadInfoList)) {
            log.info("userUploadInfoList is empty");
            return;
        }
        log.info("userUploadInfoList size is {}", userUploadInfoList.size());

        for (UserUploadInfo userUploadInfo : userUploadInfoList) {
            List<Long> fileIdList = new ArrayList<>();
            addBefore(userUploadInfo, fileIdList, false);
            addInstall(userUploadInfo, fileIdList, false);
            addDisplay(userUploadInfo, fileIdList, false);
            addDispose(userUploadInfo, fileIdList, false);

            if (CollectionUtils.isEmpty(fileIdList)) {
                continue;
            }

            List<FileAddr> fileAddrList = fileAddrDao.findAllById(fileIdList);
            if (CollectionUtils.isEmpty(fileAddrList)) {
                continue;
            }

            List<String> pathList = fileAddrList
                    .stream()
                    .map(FileAddr::getAddr)
                    .collect(Collectors.toList());

            ZipInfo zipInfo = new ZipInfo();
            zipInfo.setBucket(OssClientConfig.BUCKET_NAME);
            zipInfo.setSourceFiles(pathList);
            String zipPath = ssoService.zipFileDir(zipInfo, userUploadInfo.getWechatCode());
            log.info("zipPath is {}", zipPath);
            if (StringUtils.isNotBlank(zipPath)) {
                userUploadInfo.setZipUrl(zipPath);
                userUploadInfo.setFileChange(0);
                userUploadInfoDao.save(userUploadInfo);
            }
        }
    }
}
