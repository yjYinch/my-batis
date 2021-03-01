package com.zyj.batis.executor.impl;

import com.zyj.batis.entity.Configuration;
import com.zyj.batis.entity.MappedStatement;
import com.zyj.batis.executor.Executor;
import com.zyj.batis.mapping.BoundSql;
import com.zyj.batis.mapping.ParameterMapping;
import com.zyj.batis.parsing.GenericTokenParser;
import com.zyj.batis.parsing.ParameterMappingTokenHandler;
import com.zyj.batis.parsing.TokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangyijun
 * @date: created in 16:57 2021/2/28
 * @description
 */
public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> queryList(Configuration configuration, MappedStatement mappedStatement, Object... params)
            throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException {
        // 1. 获取连接
        Connection connection = configuration.getDataSource().getConnection();
        // 2. 获取SQL
        String sql = mappedStatement.getSql();
        // 3. 并将#{}转换为jdbc的占位符？, 并将#{}其中的值保存起来
        BoundSql boundSql = getBoundSql(sql);
        // 4. 预处理
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
        // 5. 设置参数类型
        String paramType = mappedStatement.getParamType();
        Class<?> classType = getClassType(paramType);
        // 6. 获取#{}标签里面的参数
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();

        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            // 反射获取对应的类的变量
            Field field = classType.getDeclaredField(content);
            // 由于参数是private类型，需要设置暴力访问
            field.setAccessible(true);
            Object o = field.get(params[0]);
            preparedStatement.setObject(i + 1, o);
        }
        // 7. 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();

        // 8. 封装结果集
        // 8.1 得到sql标签上的返回类型
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);
        List<Object> objects = new ArrayList<>();
        while (resultSet.next()) {
            Object object = resultTypeClass.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 遍历列
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                // 字段名
                String columnName = metaData.getColumnName(i + 1);
                // 驼峰转换
                String columnNameCamel = underlineToCamel(columnName);
                // 根据字段名获得对应的值
                Object value = resultSet.getObject(columnNameCamel);
                // 使用反射，根据数据库表和实体的响应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnNameCamel, resultTypeClass);
                Method method = propertyDescriptor.getWriteMethod();
                method.invoke(object, value);
            }
            objects.add(object);
        }

        return (List<E>) objects;
    }

    private BoundSql getBoundSql(String sql) {
        // 标记处理类：配置标记解析器完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        // 创建解析器，规定了解析器的开始标记和结束标记符号
        GenericTokenParser genericTokenParser =
                new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parseSql = genericTokenParser.parse(sql);
        // #{}解析出来的参数名称
        List<ParameterMapping> parameterMappingList = parameterMappingTokenHandler.getParameterMappingList();
        return new BoundSql(parseSql, parameterMappingList);
    }

    private Class<?> getClassType(String paramType) throws ClassNotFoundException {
        if (paramType != null) {
            Class<?> aClass = Class.forName(paramType);
            return aClass;
        }
        return null;
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串
     *
     * @param param
     * @return
     */
    private String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == '_') {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
