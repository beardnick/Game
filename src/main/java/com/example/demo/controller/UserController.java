package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.domain.Response;
import com.example.demo.domain.User;
import com.example.demo.reporitory.UserRepritory;
import com.example.demo.service.UserService;
import com.example.demo.util.CookieUtil;
import com.example.demo.util.ReflectUtil;
import com.example.demo.util.ResponseUtil;

import java.awt.CardLayout;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.List;

//all the data return as json
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Autowired
    private UserRepritory userRepritory;


    @PostMapping("/create")
    public Response create(@RequestParam("nickname") String nickname, @RequestParam("password") String password) {
        return ResponseUtil.result(
                userService.createUser(new User(nickname, password)),
                100,
                "注册成功"
        );
    }

    @PostMapping("/edit")
    public Response edit(User user) {
        User res;
        if (user.getUid() != null) {
            res = userRepritory.findUserByUid(user.getUid());
            if (res != null) {
                Class<User> cl = null;
                try {
                    cl = (Class<User>) Class.forName("com.example.demo.domain.User");
                    for (Field f : cl.getDeclaredFields()
                    ) {
                        Method getter = ReflectUtil.getGetter(cl, f.getName());
                        Method setter = ReflectUtil.getSetter(cl, f.getName(), f.getType());
                        if (getter != null && getter.invoke(user, null) != null) {
                            setter.invoke(res, getter.invoke(user));
                        }
                    }
                    return ResponseUtil.result(
                            userRepritory.save(res),
                            100,
                            "修改成功"
                    );
                } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                ResponseUtil.result(200, "id为" + user.getUid() + "的用户不存在");
            }
        }else {
            return ResponseUtil.result(200, "id为空");
        }
        return ResponseUtil.result(200,"服务器内部错误");
    }

    @PostMapping("/login")
    public Response query(@RequestParam(value = "nickname", required = false) String name,
                          @RequestParam(value = "password", required = false) String password, HttpServletRequest request, HttpServletResponse response) {
        if (CookieUtil.get(request, "name") != null
                && CookieUtil.get(request, "password") != null) {
            String tname = CookieUtil.get(request, "name");
            String tpassword = CookieUtil.get(request, "password");
            System.out.println("cookie{name:" + tname + ",password:" + tpassword + "}");
            return ResponseUtil.result(userRepritory
                            .findUserByNicknameAndPassword(tname, tpassword),
                    100,
                    "登录成功"
            );
        }
        if (name != null && password != null) {
//            User user = userRepritory.findUserByNicknameAndPassword(name, password);
            User user = userRepritory.findUserByNickname(name);
            System.out.println("user{name:" + name + ",password:" + password + "}");
            if (user != null) {
                user = userRepritory.findUserByNicknameAndPassword(name, password);
                if (user != null) {
                    CookieUtil.set(response, "name", String.valueOf(name), 1000);
                    CookieUtil.set(response, "password", password, 1000);
                    return ResponseUtil.result(
                            user,
                            100,
                            "登陆成功"
                    );
                } else {
                    return ResponseUtil.result(200, "密码错误");
                }
            }
            return ResponseUtil.result(200, "账号不存在");
        }
        return ResponseUtil.result(200, "未输入账号和密码");
    }


    @PostMapping("/logout")
    public void logOut(HttpServletResponse response) {
        CookieUtil.set(response, "name", "", 0);
        CookieUtil.set(response, "password", "", 0);
    }

    @PostMapping("/delete")
    public void delete(@RequestParam("uid") Long uid) {
        userService.deleteUser(uid);
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/query/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping("/uid/{uid}")
    public Response getUser(@PathVariable("uid") Long uid) {
        return ResponseUtil.result(
                userRepritory.findUserByUid(uid),
                100,
                "success"
        );
    }

    //    @RequestMapping("/uid/{id}/edit/avatar")
    @RequestMapping("/upload/avatar")
    public Response editAvatar(
            @RequestParam("file") MultipartFile file,
//            @PathVariable("uid")Long uid,
            HttpServletRequest request
    ) {

        if (file.isEmpty()) {
            return ResponseUtil.result(200, "文件为空");
        }
        String path = "/data/avatar/";
        File severFile = new File(path + file.getOriginalFilename());
        File dir = new File(path);
        try {
            if (!dir.exists()) {
                dir.mkdir();
            }
            file.transferTo(severFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseUtil.result(
                    200,
                    "上传失败"
            );
        }
        return ResponseUtil.result(
                "123.207.19.172/file/avatar/" + file.getOriginalFilename(),
                100,
                "上传成功"
        );

    }


}
