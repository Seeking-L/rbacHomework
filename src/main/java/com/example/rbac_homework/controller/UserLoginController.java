package com.example.rbac_homework.controller;

import com.example.rbac_homework.pojo.Permission;
import com.example.rbac_homework.pojo.User;
import com.example.rbac_homework.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/userLog")
public class UserLoginController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public String Login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        HttpServletResponse response) {
//        try {
        System.out.println("--------------username="+username+"  password="+password+"----------------");
        User user = new User();
        user.setUserName(username);
        user.setUserPassword(password);
        user.setUserId(userService.findUserIdByNameAndPassword(username,password));

        if (userService.login(username, password) == null) {//如果账号密码错误
            return "redirect:/userLog/login";
        }

        //账号密码正确
        List<Permission> permission = userService.getPermissionByRoles(userService.getRoleByUserId(user.getUserId()));

        HashSet<Integer> permissions = new HashSet<>();//将所有的权限以hashset的形式储存
        for(Permission aPermission:permission){
            permissions.add(Integer.valueOf(aPermission.getPermissionId()));
        }

        session.setAttribute("permissions", permissions);
        session.setAttribute("user", user);
        return "redirect:/mainPage/Page";
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return "redirect:/userLog/login";
//        }
    }
}
