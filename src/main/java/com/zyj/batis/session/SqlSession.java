package com.zyj.batis.session;

import java.util.List;

/**
 * @author: zhangyijun
 * @date: created in 16:40 2021/2/28
 * @description
 */
public interface SqlSession {
    /**
     * 查询多个对象
     *
     * @param statementId
     * @param param
     * @param <E>
     * @return
     */
    <E> List<E> selectList(String statementId, Object... param);

    /**
     * 查询结果为一条
     * @param statementId
     * @param param
     * @param <E>
     * @return
     * @throws Exception
     */
    <E> E selectOne(String statementId, Object... param) throws Exception;

    /**
     * 获取mapper代理对象
     * @param mapperClass
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<?> mapperClass);
}
