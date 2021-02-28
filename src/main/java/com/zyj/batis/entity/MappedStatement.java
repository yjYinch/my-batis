package com.zyj.batis.entity;

import lombok.Data;

/**
 * @author: zhangyijun
 * @date: created in 14:36 2021/2/28
 * @description
 */
@Data
public class MappedStatement {

    /**
     * XxxMapper.xml中<Select id = ></Select>中的id标识
     */
    private String id;

    /**
     * 查询后的返回值类型
     */
    private String resultType;

    /**
     * 传参类型
     */
    private String paramType;

    /**
     * sql语句
     */
    private String sql;
}
