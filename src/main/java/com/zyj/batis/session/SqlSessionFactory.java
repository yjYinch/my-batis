package com.zyj.batis.session;

/**
 * @author: zhangyijun
 * @date: created in 16:39 2021/2/28
 * @description
 */
public interface SqlSessionFactory {
    /**
     * 创建SqlSession
     * @return
     */
    SqlSession openSession();
}
