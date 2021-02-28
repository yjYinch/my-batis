package com.zyj.batis.session.defaults;

import com.zyj.batis.entity.Configuration;
import com.zyj.batis.entity.MappedStatement;
import com.zyj.batis.executor.Executor;
import com.zyj.batis.executor.impl.SimpleExecutor;
import com.zyj.batis.session.SqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author: zhangyijun
 * @date: created in 16:48 2021/2/28
 * @description
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... param) {
        // 构建SQL执行器
        Executor executor = new SimpleExecutor();
        // 获取mapper配置中所有sql配置信息
        Map<String, MappedStatement> mappedStatements = configuration.getMappedStatements();
        try {
            return executor.queryList(configuration, mappedStatements.get(statementId),param);
        } catch (SQLException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException
                | InstantiationException | IntrospectionException | InvocationTargetException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public <E> E selectOne(String statementId, Object... param) throws Exception {
        List<Object> objects = selectList(statementId, param);
        if (objects.size() > 1){
            throw new Exception("查询结果为多条");
        }
        return (E) objects.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            /**
             * 代理对象调用mapper接口中的方法都要调用invoke方法执行
             * @param proxy 当前代理对象的应用
             * @param method 当前被调用方法的引用
             * @param args 传递的参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // statementId: sql语句的唯一标识，有mapper文件的namespace.id组成
                // 获取方法名称
                String methodName = method.getName();
                // 获取类名称
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;
                Type genericReturnType = method.getGenericReturnType();
                // 判断是否进行了泛型类型参数化
                if (genericReturnType instanceof ParameterizedType) {
                    return selectList(statementId, args);
                } else {
                    return selectOne(statementId, args);
                }
            }
        });
        return (T) proxyInstance;
    }
}
