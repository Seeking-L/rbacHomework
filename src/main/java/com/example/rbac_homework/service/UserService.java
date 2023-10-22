package com.example.rbac_homework.service;

import com.example.rbac_homework.pojo.Permission;
import com.example.rbac_homework.pojo.Role;
import com.example.rbac_homework.pojo.User;

import java.util.List;

public interface UserService {
    User findById(int id);
    User findByUserName(String userName);
    User findByUserPassword(String userPassword);
    int findUserIdByNameAndPassword(String userName,String userPassword);
    User login(String userName,String userPassword);
    List<Role>  getRoleByUserId(int userId);
    List<Permission> getPermissionByRoles(List<Role> roles);
}
