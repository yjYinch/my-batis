package com.zyj.batis.executor;

import com.zyj.batis.entity.Configuration;
import com.zyj.batis.entity.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: zhangyijun
 * @date: created in 16:56 2021/2/28
 * @description
 */
public interface Executor {
    /**
     * 查询sql
     * @param configuration
     * @param mappedStatement
     * @param param
     * @param <E>
     * @return
     */
    <E> List<E> queryList(Configuration configuration, MappedStatement mappedStatement, Object... param) throws
            SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException,
            InstantiationException, IntrospectionException, InvocationTargetException;
}
