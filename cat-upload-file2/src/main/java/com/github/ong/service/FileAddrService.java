package com.github.ong.service;

import com.aliyun.credentials.provider.CredentialsProviderFactory;
import com.aliyun.credentials.provider.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.github.ong.config.http.OssClientConfig;
import com.github.ong.dao.h2.FileAddrDao;
import com.github.ong.enums.db.FileType;
import com.github.ong.enums.db.WholeAddr;
import com.github.ong.model.h2.FileAddr;
import com.github.ong.utils.AliyunUtil;
import com.github.ong.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;

@Service
@Slf4j
public class FileAddrService {

    @Resource
    private FileAddrDao fileAddrDao;

    @Resource(name = "outRestTemplate")
    private RestTemplate outRestTemplate;

    @Resource(name = "ossClient")
    private OSS ossClient;

    public FileAddr saveLocalImg(String path) {
        return saveFileAddr(path, FileType.IMAGE.getCode(), WholeAddr.LOCAL.getCode(), null);
    }

    public FileAddr saveLocalVideo(String path) {
        return saveFileAddr(path, FileType.VIDEO.getCode(), WholeAddr.LOCAL.getCode(), null);
    }

    public FileAddr saveLocalVideo(String path, String fileName) {
        return saveFileAddr(path, FileType.VIDEO.getCode(), WholeAddr.LOCAL.getCode(), fileName);
    }

    public FileAddr saveSsoVideo(String path, String fileName) {
        return saveFileAddr(path, FileType.VIDEO.getCode(), WholeAddr.SSO.getCode(), fileName);
    }

    public FileAddr saveSsoImg(String path, String fileName) {
        return saveFileAddr(path, FileType.IMAGE.getCode(), WholeAddr.SSO.getCode(), fileName);
    }

    private FileAddr saveFileAddr(String path,
                                    Integer fileType,
                                    Integer wholeAddr,
                                    String fileName) {
        FileAddr fileAddr = new FileAddr();
        fileAddr.setAddr(path);
        fileAddr.setFileType(fileType);
        fileAddr.setWholeAddr(wholeAddr);
        fileAddr.setOriginalFileName(fileName);

        fileAddrDao.save(fileAddr);
        return fileAddr;
    }

    public FileAddr saveVideoFrame(String fileUrl, String wechatCode) {
        if (StringUtils.isBlank(fileUrl)) {
            return null;
        }
        ResponseExtractor<FileAddr> responseExtractor = clientHttpResponse -> {
            String filename = StringUtil.uuid() + ".jpeg";
            File tempFile = new File("~/temp/" + filename);

            try {
                if (!tempFile.getParentFile().exists()) {
                    tempFile.getParentFile().mkdirs();
                }
                StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(tempFile));
                String fileKey = wechatCode + "/" + filename;
                uploadSsoFile(tempFile, fileKey);

                return saveSsoImg(fileKey, filename);
            } catch (Exception e) {
                log.info("saveVideoFrame error {}", ExceptionUtils.getStackTrace(e));
            }
            return null;
        };

        return outRestTemplate.execute(AliyunUtil.SSO_ROOT + fileUrl + "?x-oss-process=video/snapshot,t_1000,f_jpg,w_800,h_600,m_fast", HttpMethod.GET, null, responseExtractor);
    }

    public void uploadSsoFile(File file, String fileKey) {
        PutObjectResult putObjectResult = ossClient.putObject(new PutObjectRequest(OssClientConfig.BUCKET_NAME, fileKey, file));
        log.info("putObjectResult is {}", putObjectResult.getResponse());
    }
}
