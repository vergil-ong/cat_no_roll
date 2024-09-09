package com.github.ong.dao.h2;

import com.github.ong.model.h2.FileAddr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAddrDao extends JpaRepository<FileAddr, Long> {

}
