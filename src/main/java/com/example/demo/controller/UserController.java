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
import com.example.demo.domain.User;
import com.example.demo.reporitory.UserRepritory;
import com.example.demo.service.UserService;

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
    public User query(Long uid, String password){
        return userRepritory.findUserByUidAndPassword(uid, password);
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
