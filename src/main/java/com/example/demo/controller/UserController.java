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
import com.example.demo.util.ResponseUtil;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
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
    public Response create(@RequestParam("nickname") String nickname , @RequestParam("password") String password){
        return ResponseUtil.result(
                userService.createUser(new User(nickname, password)),
                        100,
                        "注册成功"
                        );
    }

    @PostMapping("/edit")
    public Response edit(User user){
        System.out.println("user: " + user.getNickname() + user.getPassword() + user.getSex());
         return ResponseUtil.result(
                 userService.editUser(user),
                 100,
                 "修改成功"
                 );
    }

    @PostMapping("/login")
    public Response query(@RequestParam(value = "uid", required = false) Long uid,
                          @RequestParam(value = "password", required =  false) String password, HttpServletRequest request, HttpServletResponse response){
        if(CookieUtil.get(request, "uid") != null
                && CookieUtil.get(request, "password") != null ){
            Long tuid = Long.valueOf(
                    CookieUtil.get(request, "uid")
            );
            String tpassword = CookieUtil.get(request, "password");
            System.out.println("cookie{uid:" + tuid +",password:" + tpassword +"}");
            return ResponseUtil.result(userRepritory
                    .findUserByUidAndPassword(tuid, tpassword),
                    100,
                    "登录成功"
            );
        }
        if(uid != null && password != null){
            User user = userRepritory.findUserByUidAndPassword(uid, password);
            System.out.println("user{uid:" + uid +",password:" + password +"}");
            if(user != null){
                CookieUtil.set(response, "uid", String.valueOf(uid), 1000);
                CookieUtil.set(response, "password", password , 1000);
                return ResponseUtil.result(
                        user,
                        100,
                        "登陆成功"
                );
            }
            return ResponseUtil.result(200,"密码或账号错误");
        }
        return ResponseUtil.result(200, "未输入账号和密码");
    }

    @PostMapping("/logout")
    public void logOut(HttpServletResponse response){
        CookieUtil.set(response, "uid", "", 0);
        CookieUtil.set(response, "password", "", 0);
    }

    @PostMapping("/delete")
    public void delete(@RequestParam("uid") Long uid){
        userService.deleteUser(uid);
    }

    @RequestMapping("/hello") public String hello(){
        return "hello world";
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/query/users")
    public List<User>getUsers(){
        return userService.getUsers();
    }

    @RequestMapping("/uid/{uid}")
    public Response getUser(@PathVariable("uid") Long uid){
        return ResponseUtil.result(
                userRepritory.findUserByUid(uid),
                100,
                "success"
        );
    }

//    @RequestMapping("/uid/{id}/edit/avatar")
    @RequestMapping("/upload/avatar")
    public Response editAvatar(
            @RequestParam("file")MultipartFile file,
//            @PathVariable("uid")Long uid,
            HttpServletRequest request
    ){

        if(file.isEmpty()){
            return ResponseUtil.result(200, "文件为空");
        }
        String path = request
                .getServletContext()
                .getRealPath("/") + "avatar/";
        File severFile = new File(path + file.getOriginalFilename());
        File dir = new File(path);
        try {
            if(! dir.exists()){
                dir.mkdir();
            }
            file.transferTo(severFile);
        }catch (IOException e) {
            e.printStackTrace();
            return ResponseUtil.result(
                   200,
                   "上传失败"
            );
        }
        return ResponseUtil.result(
                severFile.getPath(),
                100,
                "上传成功"
        );
    }


}
