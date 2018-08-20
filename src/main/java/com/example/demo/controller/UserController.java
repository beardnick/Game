package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;

import java.util.List;

//all the data return as json
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @RequestMapping("/create")
    public User create(@RequestParam("nickname") String nickname , @RequestParam("password") String password){
        return userService.createUser(new User(nickname, password));
    }

    @RequestMapping("/edit")
    public User edit(User user){
         return userService.editUser(user);
    }

    @RequestMapping("/query")
    public User query(User user){
       return userService.findByIdAndPassword(user.getUid(), user.getPassword()) ;
    }

    @RequestMapping("/delete")
    public void delete(User user){
        userService.deleteUser(user);
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

}
