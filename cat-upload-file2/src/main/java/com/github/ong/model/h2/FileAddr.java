package com.github.ong.model.h2;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "FILE_ADDR")
@Getter
@Setter
public class FileAddr {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "addr")
    private String addr;

    /**
     * @see com.github.ong.enums.db.WholeAddr
     */
    @Column(name = "whole_addr")
    private Integer wholeAddr;

    /**
     * @see com.github.ong.enums.db.FileType
     */
    @Column(name = "file_type")
    private Integer fileType;


    @Column(name = "original_file_name")
    private String originalFileName;
}
