package com.zyj.batis.session.defaults;

import com.zyj.batis.entity.Configuration;
import com.zyj.batis.session.SqlSession;
import com.zyj.batis.session.SqlSessionFactory;

/**
 * @author: zhangyijun
 * @date: created in 16:42 2021/2/28
 * @description
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
    }

    /**
     * 创建SqlSession对象
     * @return
     */
    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
