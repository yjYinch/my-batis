package com.zyj.batis.mapping;

import lombok.Data;

import java.util.List;

/**
 * @author: zhangyijun
 * @date: created in 17:01 2021/2/28
 * @description
 */
@Data
public class BoundSql {
    private String sql;

    private List<ParameterMapping> parameterMappingList;

    public BoundSql(String sql, List<ParameterMapping> parameterMappingList) {
        this.sql = sql;
        this.parameterMappingList = parameterMappingList;
    }
}
