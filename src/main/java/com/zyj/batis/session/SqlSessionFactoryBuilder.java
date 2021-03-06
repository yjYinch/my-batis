package com.zyj.batis.session;

import com.zyj.batis.builder.XMLConfigBuilder;
import com.zyj.batis.entity.Configuration;
import com.zyj.batis.session.defaults.DefaultSqlSessionFactory;

import java.io.InputStream;

/**
 * @author: zhangyijun
 * @date: created in 16:45 2021/2/28
 * @description
 */
public class SqlSessionFactoryBuilder {

    /**
     * 创建SqlSessionFactory对象
     * @param in
     * @return
     */
    public SqlSessionFactory builder(InputStream in){
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        // 解析配置文件"mybatis-config.xml"
        Configuration configuration = xmlConfigBuilder.parseConfig(in);
        return new DefaultSqlSessionFactory(configuration);
    }
}
