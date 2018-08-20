package com.example.demo.service;

import com.example.demo.domain.User;

import java.util.List;


public interface UserService {

     User findByIdAndPassword(Long id, String password);

     User createUser(User user);

     User editUser(User user);

     List<User>getUsers();

     void deleteUser(Long uid);
}
