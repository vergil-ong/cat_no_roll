package com.github.ong.model.sso;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ZipInfo {

    private String bucket;

    @JsonProperty("source-files")
    private List<String> sourceFiles;
}
