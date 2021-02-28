package com.zyj.batis.entity;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangyijun
 * @date: created in 14:32 2021/2/28
 * @description mybatis-config.xml配置文件对应的实体类
 */
@Data
public class Configuration {

    /**
     * 数据库配置信息
     */
    private DataSource dataSource;

    /**
     * key: namespace+id
     * value: XxxMapper.xml信息
     */
    private final Map<String, MappedStatement> mappedStatements = new HashMap<>();
}
