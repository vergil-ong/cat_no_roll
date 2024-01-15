package com.github.ong.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
@Slf4j
public class UploadFileApiController {
    public static final String root_path = "/Users/wangshuo/IdeaGitProjects/cat_no_roll/files";

    @RequestMapping("/upload")
    public String uploadFile(String code,
                             MultipartHttpServletRequest request) {
        MultiValueMap<String, MultipartFile> multiFileMap = request.getMultiFileMap();
        List<MultipartFile> multipartFileList = multiFileMap.get("file");
        try {
            for (MultipartFile multipartFile: multipartFileList){
                // 对上传的文件重命名，避免文件重名
                String oldName = multipartFile.getOriginalFilename();
                File codeFile = new File(root_path, code);
                if (!codeFile.exists()) {
                    codeFile.mkdirs();
                }
                // 文件保存
                multipartFile.transferTo(new File(codeFile, oldName));

                return "/api/upload/files?code="+code+"&fileName=" + oldName;
            }
        } catch (IOException e) {
            log.info("IOException {}", e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public void cacheDownload(@RequestParam("fileName") String fileName,
                              @RequestParam("code") String code,
                              HttpServletResponse response) throws Exception {
        File imageFile = new File(new File(root_path, code), fileName);
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
}
