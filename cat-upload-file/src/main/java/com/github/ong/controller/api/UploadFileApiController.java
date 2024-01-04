package com.github.ong.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.el.util.ExceptionUtils;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
@Slf4j
public class UploadFileApiController {

    public static final String root_path = "/Users/wangshuo/IdeaGitProjects/cat_no_roll/cat-upload-file/src/main/resources/static/files";

    @RequestMapping("/delete")
    public String deleteFile(String code, String fileId) {
        File file = new File(new File(root_path, code), fileId);
        if (file.exists()) {
            file.delete();
        }

        log.info("path is {}", file.getPath());
        return "OK";
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

                String resHtml = "\"<div class=\"alert alert-warning\" role=\"alert\" style=\"font-size: 14px;\">\n" +
                        "    页面仅做展示，不是制作效果，请放心上传<br/>\n" +
                        "    有些图片格式浏览器中可能不能预览，但不影响制作<br/>\n" +
                        "    提交后，建议截图发给客服！\n" +
                        "</div>\n" +
                        "\n" +
                        "<div class=\"row\" style=\"padding: 5px;\" >\n" +
                        "    <div class=\"col-6 col-md-3 \" style=\" padding: 0;padding-left: 7px;margin-bottom: 15px; \">\n" +
                        "        <div class=\"card h-100 border-info\" >\n" +
                        "            <img src=\"/static/files/"+oldName+"\" class=\"card-img-top\">\n" +
                        "            <div class=\"card-body\" style=\"padding:3px\">\n" +
                        "\n" +
                        "            </div>\n" +
                        "            <div class=\"card-footer bg-transparent\">\n" +
                        "                <button type=\"button\" data-id=\"38437\" class=\"upbut btn  btn-light\" onclick=\"delImg(this)\"  style=\"font-size:1.5rem;border-radius: 0.5rem;\">\n" +
                        "                    删除\n" +
                        "                </button>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "</div>\"";
                return resHtml;
            }
        } catch (IOException e) {
            log.info("IOException {}", e.getMessage());
        }
        return null;
    }

    @RequestMapping("/orderImgs")
    public String orderImgs(String code) {
        File codeFile = new File(root_path, code);
        File[] files = codeFile.listFiles();
        List<String> pathList = new ArrayList<>();

        String resHtml = "<div class=\"alert alert-warning\" role=\"alert\" style=\"font-size: 14px;\">\n" +
                "    页面仅做展示，不是制作效果，请放心上传<br/>\n" +
                "    有些图片格式浏览器中可能不能预览，但不影响制作<br/>\n" +
                "    提交后，建议截图发给客服！\n" +
                "</div>\n" +
                "\n" +
                "<div class=\"row\" style=\"padding: 5px;\" >\n" +
                "    <div class=\"col-6 col-md-3 \" style=\" padding: 0;padding-left: 7px;margin-bottom: 15px; \">\n";
        for (File file : files) {
            String fileName = file.getName();
            String path = "/api/upload/files/?code=" + code + "&fileName=" + fileName;
            resHtml += "        <div class=\"card h-100 border-info\" >\n" +
                    "            <img src=\""+path+"\" class=\"card-img-top\">\n" +
                    "            <div class=\"card-body\" style=\"padding:3px\">\n" +
                    "\n" +
                    "            </div>\n" +
                    "            <div class=\"card-footer bg-transparent\">\n" +
                    "                <button type=\"button\" data-id=\"38437\" class=\"upbut btn  btn-light\" onclick=\"delImg('"+fileName+"')\"  style=\"font-size:1.5rem;border-radius: 0.5rem;\">\n" +
                    "                    删除\n" +
                    "                </button>\n" +
                    "            </div>\n" +
                    "        </div>\n" ;
        }

        resHtml += "    </div>\n" +
                "</div>";
        return resHtml;
    }
}
