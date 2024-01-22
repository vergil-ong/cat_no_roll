package com.github.ong.controller.api;

import com.github.ong.bo.FileBo;
import com.github.ong.enums.biz.UploadFileIndex;
import com.github.ong.model.h2.AdminUploadVideo;
import com.github.ong.model.h2.FileAddr;
import com.github.ong.model.h2.UserUploadInfo;
import com.github.ong.service.AdminUploadVideoService;
import com.github.ong.service.FileAddrService;
import com.github.ong.service.UserUploadInfoService;
import com.github.ong.utils.StringUtil;
import com.github.ong.utils.VideoFrameUtil;
import com.github.ong.vo.AdminUploadVideoVo;
import com.github.ong.vo.FileUploadVo;
import com.github.ong.vo.common.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.id.UUIDGenerator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/upload")
@Slf4j
public class UploadFileApiController {

    public static final String root_path = "/Users/wangshuo/IdeaGitProjects/cat_no_roll/files";

    @Resource
    private FileAddrService fileAddrService;

    @Resource
    private UserUploadInfoService userUploadInfoService;

    @Resource
    private AdminUploadVideoService adminUploadVideoService;

    @RequestMapping("/img/upload")
    public String uploadImage(String wechatCode,
                                String fileId,
                                String fileIndex,
                                MultipartHttpServletRequest request) {
        FileBo newFileBo = saveFile(wechatCode, request);
        if (Objects.isNull(newFileBo)) {
            return null;
        }
        FileAddr imageFileAddr = fileAddrService.saveLocalImg(newFileBo.getUrl());
        if (Objects.isNull(imageFileAddr)) {
            return newFileBo.getUrl();
        }

        UserUploadInfo userUploadInfo = new UserUploadInfo();
        UploadFileIndex uploadFileIndex = UploadFileIndex.getFileIndex(fileId, fileIndex);
        if (Objects.isNull(uploadFileIndex)){
            return newFileBo.getUrl();
        }
        switch (uploadFileIndex){
            case BEFORE_IMG_1:
                userUploadInfo.setBeforeImg1(imageFileAddr.getId());
                break;
            case BEFORE_IMG_2:
                userUploadInfo.setBeforeImg2(imageFileAddr.getId());
                break;
            case DISPLAY_IMG_1:
                userUploadInfo.setDisplayImg1(imageFileAddr.getId());
                break;
            case DISPLAY_IMG_2:
                userUploadInfo.setDisplayImg2(imageFileAddr.getId());
                break;
            case DISPLAY_IMG_3:
                userUploadInfo.setDisplayImg3(imageFileAddr.getId());
                break;
            case DISPLAY_IMG_4:
                userUploadInfo.setDisplayImg4(imageFileAddr.getId());
                break;
            case DISPLAY_IMG_5:
                userUploadInfo.setDisplayImg5(imageFileAddr.getId());
                break;
            case DISPLAY_IMG_6:
                userUploadInfo.setDisplayImg6(imageFileAddr.getId());
                break;
            default:
                break;
        }
        userUploadInfoService.updateUserUploadInfo(userUploadInfo, wechatCode);

        return newFileBo.getUrl();
    }

    @RequestMapping("/video/upload")
    public FileUploadVo uploadVideo(String wechatCode,
                                    String fileId,
                                    String fileIndex,
                                    MultipartHttpServletRequest request) {
        FileBo newFileBo = saveFile(wechatCode, request);
        if (Objects.isNull(newFileBo)) {
            return null;
        }
        FileUploadVo fileUploadVo = new FileUploadVo();
        fileUploadVo.setVideoUrl(newFileBo.getUrl());
        fileUploadVo.setImageUrl(newFileBo.getFrameUrl());

        FileAddr videoFileAddr = fileAddrService.saveLocalVideo(newFileBo.getUrl());
        if (Objects.isNull(videoFileAddr)) {
            return fileUploadVo;
        }
        FileAddr imageFileAddr = null;
        boolean frameBol = VideoFrameUtil.getFrame(newFileBo.getFramePath(), newFileBo.getPath());
        if (frameBol) {
            imageFileAddr = fileAddrService.saveLocalImg(newFileBo.getFrameUrl());
        }
        if (Objects.isNull(imageFileAddr)) {
            return fileUploadVo;
        }
        UserUploadInfo userUploadInfo = new UserUploadInfo();
        UploadFileIndex uploadFileIndex = UploadFileIndex.getFileIndex(fileId, fileIndex);
        if (Objects.isNull(uploadFileIndex)){
            return fileUploadVo;
        }
        switch (uploadFileIndex){
            case BEFORE_VIDEO_1:
                userUploadInfo.setBeforeVideo1(videoFileAddr.getId());
                userUploadInfo.setBeforeVideoImg1(imageFileAddr.getId());
                break;
            case BEFORE_VIDEO_2:
                userUploadInfo.setBeforeVideo2(videoFileAddr.getId());
                userUploadInfo.setBeforeVideoImg2(imageFileAddr.getId());
                break;
            case INSTALL_VIDEO_1:
                userUploadInfo.setInstallVideo1(videoFileAddr.getId());
                userUploadInfo.setInstallVideoImg1(imageFileAddr.getId());
                break;
            case INSTALL_VIDEO_2:
                userUploadInfo.setInstallVideo2(videoFileAddr.getId());
                userUploadInfo.setInstallVideoImg2(imageFileAddr.getId());
                break;
            case INSTALL_VIDEO_3:
                userUploadInfo.setInstallVideo3(videoFileAddr.getId());
                userUploadInfo.setInstallVideoImg3(imageFileAddr.getId());
                break;
            case INSTALL_VIDEO_4:
                userUploadInfo.setInstallVideo4(videoFileAddr.getId());
                userUploadInfo.setInstallVideoImg4(imageFileAddr.getId());
                break;
            case DISPLAY_VIDEO_1:
                userUploadInfo.setDisplayVideo1(videoFileAddr.getId());
                userUploadInfo.setDisplayVideoImg1(imageFileAddr.getId());
                break;
            case DISPLAY_VIDEO_2:
                userUploadInfo.setDisplayVideo2(videoFileAddr.getId());
                userUploadInfo.setDisplayVideoImg2(imageFileAddr.getId());
                break;
            case DISPLAY_VIDEO_3:
                userUploadInfo.setDisplayVideo3(videoFileAddr.getId());
                userUploadInfo.setDisplayVideoImg3(imageFileAddr.getId());
                break;
            case DISPLAY_VIDEO_4:
                userUploadInfo.setDisplayVideo4(videoFileAddr.getId());
                userUploadInfo.setDisplayVideoImg4(imageFileAddr.getId());
                break;
            case DISPLAY_VIDEO_5:
                userUploadInfo.setDisplayVideo5(videoFileAddr.getId());
                userUploadInfo.setDisplayVideoImg5(imageFileAddr.getId());
                break;
            case DISPLAY_VIDEO_6:
                userUploadInfo.setDisplayVideo6(videoFileAddr.getId());
                userUploadInfo.setDisplayVideoImg6(imageFileAddr.getId());
                break;
            case DISPOSE_VIDEO_1:
                userUploadInfo.setDisposeVideo1(videoFileAddr.getId());
                userUploadInfo.setDisposeVideoImg1(imageFileAddr.getId());
                break;
            case DISPOSE_VIDEO_2:
                userUploadInfo.setDisposeVideo2(videoFileAddr.getId());
                userUploadInfo.setDisposeVideoImg2(imageFileAddr.getId());
                break;
            case DISPOSE_VIDEO_3:
                userUploadInfo.setDisposeVideo3(videoFileAddr.getId());
                userUploadInfo.setDisposeVideoImg3(imageFileAddr.getId());
                break;
            case DISPOSE_VIDEO_4:
                userUploadInfo.setDisposeVideo4(videoFileAddr.getId());
                userUploadInfo.setDisposeVideoImg4(imageFileAddr.getId());
                break;
            case DISPOSE_VIDEO_5:
                userUploadInfo.setDisposeVideo5(videoFileAddr.getId());
                userUploadInfo.setDisposeVideoImg5(imageFileAddr.getId());
                break;
            case DISPOSE_VIDEO_6:
                userUploadInfo.setDisposeVideo6(videoFileAddr.getId());
                userUploadInfo.setDisposeVideoImg6(imageFileAddr.getId());
                break;
            default:
                break;
        }
        userUploadInfoService.updateUserUploadInfo(userUploadInfo, wechatCode);
        return fileUploadVo;
    }

    private FileBo saveFile(String code,
                            MultipartHttpServletRequest request) {
        FileBo fileBo = new FileBo();
        MultiValueMap<String, MultipartFile> multiFileMap = request.getMultiFileMap();
        List<MultipartFile> multipartFileList = multiFileMap.get("file");
        try {
            for (MultipartFile multipartFile: multipartFileList){
                String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
                String newFileName = StringUtil.uuid();
                String newFileWholeName = StringUtil.uuid() +"."+ extension;
                String newFrameWholeName = newFileName+".jpg";
                File codeFile = new File(root_path, code);
                if (!codeFile.exists()) {
                    codeFile.mkdirs();
                }
                File targetFile = new File(codeFile, newFileWholeName);
                multipartFile.transferTo(targetFile);

                fileBo.setUrl("/api/upload/files?code="+code+"&fileName=" + newFileWholeName);
                fileBo.setPath(targetFile.getPath());
                fileBo.setFramePath(new File(codeFile, newFrameWholeName).getPath());
                fileBo.setFrameUrl("/api/upload/files?code="+code+"&fileName="+newFrameWholeName);
                fileBo.setFileName(multipartFile.getOriginalFilename());
                return fileBo;
            }
        } catch (IOException e) {
            log.info("IOException {}", e.getMessage());
        }
        return null;
    }

    @RequestMapping("/user/down/increase")
    public ResultVo increaseUserDownloadCnt(Long adminUploadId) {
        adminUploadVideoService.increaseDownload(adminUploadId);
        return ResultVo.success();
    }

    @RequestMapping("/admin/video")
    public ResultVo uploadAdminVideo(String wechatCode,
                                     MultipartHttpServletRequest request) {
        ResultVo resultVo = ResultVo.fail();
        FileBo newFileBo = saveFile(wechatCode, request);
        if (Objects.isNull(newFileBo)) {
            resultVo.setMessage("保存文件失败");
            return resultVo;
        }
        FileAddr videoFileAddr = fileAddrService.saveLocalVideo(newFileBo.getUrl(), newFileBo.getFileName());
        if (Objects.isNull(videoFileAddr)) {
            resultVo.setMessage("保存视频文件关联关系失败");
            return resultVo;
        }
        FileAddr imageFileAddr = null;
        boolean frameBol = VideoFrameUtil.getFrame(newFileBo.getFramePath(), newFileBo.getPath());
        if (frameBol) {
            imageFileAddr = fileAddrService.saveLocalImg(newFileBo.getFrameUrl());
        }
        if (Objects.isNull(imageFileAddr)) {
            resultVo.setMessage("保存视频截图失败");
            return resultVo;
        }
        AdminUploadVideo adminUploadVideo = new AdminUploadVideo();
        adminUploadVideo.setWechatCode(wechatCode);
        adminUploadVideo.setVideoId(videoFileAddr.getId());
        adminUploadVideo.setVideoImgId(imageFileAddr.getId());
        adminUploadVideo.setUpdateTime(new Date());
        adminUploadVideo.setUserDownCount(0);

        adminUploadVideoService.recordVideo(adminUploadVideo);

        return ResultVo.success();
    }

    @RequestMapping("/img/delte")
    public void delImage(String wechatCode,
                        String fileId,
                        String fileIndex) {
        UploadFileIndex uploadFileIndex = UploadFileIndex.getFileIndex(fileId, fileIndex);
        if (Objects.isNull(uploadFileIndex)){
            log.info("file not exist");
            return;
        }
        userUploadInfoService.deleteImage(uploadFileIndex, wechatCode);
    }

    public static File getFile(String code, String fileName) {
        return new File(new File(root_path, code), fileName);
    }

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public void cacheDownload(@RequestParam("fileName") String fileName,
                              @RequestParam("code") String code,
                              HttpServletResponse response) throws Exception {
        File imageFile = getFile(code, fileName);
        FileSystemResource file = new FileSystemResource(imageFile);
        if (!file.exists()) {
            return;
        }
        String filename = file.getFilename();
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        try {
            inputStream = file.getInputStream();
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
            FileCopyUtils.copy(bufferedInputStream, bufferedOutputStream);
        }catch(Exception e){

        }finally {
            if(null!=inputStream){
                inputStream.close();
            }
            if(null!=bufferedInputStream){
                bufferedInputStream.close();
            }
            if(null!=bufferedOutputStream){
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
        }
    }

    @RequestMapping("/admin/video/list")
    public ResultVo listAdminVideo(String wechatCode) {
        List<AdminUploadVideoVo> adminUploadVideoVoList = adminUploadVideoService.listVideo(wechatCode);
        ResultVo success = ResultVo.success();
        success.setData(adminUploadVideoVoList);
        return success;
    }

    @RequestMapping("/admin/video/delete")
    public ResultVo deleteAdminVideo(Long adminVideoId) {
        adminUploadVideoService.deleteVideo(adminVideoId);
        return ResultVo.success();
    }
}
