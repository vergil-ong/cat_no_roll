package com.github.ong.dao.h2;

import com.github.ong.model.h2.AdminUploadVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUploadVideoDao extends JpaRepository<AdminUploadVideo, Long>, JpaSpecificationExecutor<AdminUploadVideo> {
}
