package com.github.ong.service;

import com.github.ong.dao.h2.FileAddrDao;
import com.github.ong.enums.db.FileType;
import com.github.ong.enums.db.WholeAddr;
import com.github.ong.model.h2.FileAddr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class FileAddrService {

    @Resource
    private FileAddrDao fileAddrDao;

    public FileAddr saveLocalImg(String path) {
        return saveFileAddr(path, FileType.IMAGE.getCode(), WholeAddr.LOCAL.getCode(), null);
    }

    public FileAddr saveLocalVideo(String path) {
        return saveFileAddr(path, FileType.VIDEO.getCode(), WholeAddr.LOCAL.getCode(), null);
    }

    public FileAddr saveLocalVideo(String path, String fileName) {
        return saveFileAddr(path, FileType.VIDEO.getCode(), WholeAddr.LOCAL.getCode(), fileName);
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
}
