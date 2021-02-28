package com.zyj.batis.mapper;

import com.zyj.batis.entity.Role;

import java.util.List;

/**
 * @author: zhangyijun
 * @date: created in 23:35 2021/2/28
 * @description
 */
public interface RoleMapper {
    List<Role> getRole(Role role);
}
