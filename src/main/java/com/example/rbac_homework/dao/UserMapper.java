package com.example.rbac_homework.dao;

import com.example.rbac_homework.pojo.Permission;
import com.example.rbac_homework.pojo.Role;
import com.example.rbac_homework.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE user_name =#{userName} and user_password=#{userPassword}")
    @Results({
            @Result(property = "userName",column = "user_name"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "userPassword",column = "user_password"),
    })
    User selectByNamePassword(String userName,String userPassword);

    @Select("select role.* from role,user_role where user_role.user_id=#{userId} and role.role_id=user_role.role_id")
    @Results({
            @Result(property = "roleId",column = "role_id"),
            @Result(property = "roleName",column = "role_name"),
            @Result(property = "roleDescription",column = "role_description"),
    })
    List<Role> rolesByuserId(int userId);

    @Select("select permission.* from permission,role_permission where role_permission.role_id=#{roleId} and permission.permission_id=role_permission.permission_id")
    @Results({
            @Result(property = "permissionId",column = "permission_id"),
            @Result(property = "permissionName",column = "permission_name"),
            @Result(property = "permissionDescription",column = "description"),
    })
    List<Permission> getPermissionsByRole(Role role);

    @Select("select user_id from user where user_name=#{userName} and user_password=#{userPassword}")
    @Result(property = "userId",column = "user_id")
    int getUserIdByNameAndPassword(String userName,String userPassword);
}
