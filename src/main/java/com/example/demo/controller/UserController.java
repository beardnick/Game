package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.domain.User;
import com.example.demo.reporitory.UserRepritory;
import com.example.demo.service.UserService;
import com.example.demo.util.CookieUtil;

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
    public User create(@RequestParam("nickname") String nickname , @RequestParam("password") String password){
        return userService.createUser(new User(nickname, password));
    }

    @PostMapping("/edit")
    public User edit(User user){
        System.out.println("user: " + user.getNickname() + user.getPassword() + user.getSex());
         return userService.editUser(user);
    }

    @PostMapping("/login")
    public User query(@RequestParam(value = "uid", required = true) Long uid,
                      @RequestParam(value = "password", required =  true) String password, HttpServletRequest request, HttpServletResponse response){
        if(CookieUtil.get(request, "uid") != null
                && CookieUtil.get(request, "password") != null ){
            Long tuid = Long.valueOf(
                    CookieUtil.get(request, "uid")
            );
            String tpassword = CookieUtil.get(request, "password");
            System.out.println("cookie{uid:" + tuid +",password:" + tpassword +"}");
            return userRepritory.findUserByUidAndPassword(tuid, tpassword);
        }
        if(uid != null && password != null){
            User user = userRepritory.findUserByUidAndPassword(uid, password);
            System.out.println("user{uid:" + uid +",password:" + password +"}");
            if(user != null){
                CookieUtil.set(response, "uid", String.valueOf(uid), 1000);
                CookieUtil.set(response, "password", password , 1000);
                return user;
            }
        }
        return null;
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

    @RequestMapping("/hello")
    public String hello(){
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

    @RequestMapping("/query/id/{uid}")
    public User getUser(@PathVariable("uid") Long uid){
        return userRepritory.findUserByUid(uid);
    }

}
