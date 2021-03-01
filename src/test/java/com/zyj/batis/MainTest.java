package com.zyj.batis;

import com.zyj.batis.entity.Role;
import com.zyj.batis.io.Resources;
import com.zyj.batis.mapper.RoleMapper;
import com.zyj.batis.session.SqlSession;
import com.zyj.batis.session.SqlSessionFactory;
import com.zyj.batis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author: zhangyijun
 * @date: created in 23:28 2021/2/28
 * @description
 */
public class MainTest {

    @Test
    public void test(){
        try {
            InputStream in = Resources.getResourcesAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().builder(in);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            Role role = new Role();
            role.setId(1);
            // 反射调用invoke方法
            List<Role> roles = roleMapper.getRole(role);
            System.out.println(roles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
