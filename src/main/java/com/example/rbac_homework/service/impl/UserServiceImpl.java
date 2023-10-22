package com.example.rbac_homework.service.impl;

import com.example.rbac_homework.dao.UserMapper;
import com.example.rbac_homework.pojo.Permission;
import com.example.rbac_homework.pojo.Role;
import com.example.rbac_homework.pojo.User;
import com.example.rbac_homework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User findById(int id) {
        return null;
    }


    @Override
    public User findByUserName(String userName) {
        return null;
    }

    @Override
    public User findByUserPassword(String userPassword) {
        return null;
    }

    @Override
    public int findUserIdByNameAndPassword(String userName, String userPassword) {
        return userMapper.getUserIdByNameAndPassword(userName,userPassword);
    }

    @Override
    public User login(String userName, String userPassword) {
        return userMapper.selectByNamePassword(userName,userPassword);
    }

    @Override
    public List<Role> getRoleByUserId(int userId) {
        return userMapper.rolesByuserId(userId);
    }

    @Override
    public List<Permission> getPermissionByRoles(List<Role> roles) {
        List<Permission> permissions=new ArrayList<Permission>();
        for(Role role:roles){
            List<Permission> permissions1=userMapper.getPermissionsByRole(role);
            permissions.addAll(permissions1);
        }
        return permissions;
    }
}
