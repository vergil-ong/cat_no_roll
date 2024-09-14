package com.github.ong.vo.common;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TablePageVo<T> {

    private List<T> rows = new ArrayList<T>();

    private int total;
}
