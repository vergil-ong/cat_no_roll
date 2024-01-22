package com.github.ong.dao.h2;

import com.github.ong.model.h2.UserUploadInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserUploadInfoDao extends JpaRepository<UserUploadInfo, Long> {
}
