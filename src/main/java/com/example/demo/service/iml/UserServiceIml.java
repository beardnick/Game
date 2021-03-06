package com.example.demo.service.iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.example.demo.domain.User;
import com.example.demo.reporitory.RoundReporitory;
import com.example.demo.reporitory.UserRepritory;
import com.example.demo.service.UserService;

@Service
public class UserServiceIml implements UserService {


    @Autowired
    private UserRepritory userRepritory;

    @Autowired
    private RoundReporitory roundReporitory;

    @Override
    public User findByIdAndPassword(Long id, String password) {
        return userRepritory.findUserByUidAndPassword(id, password);
    }

    @Override
    public User createUser(User user) {
        return userRepritory.save(user);
    }

    @Override
    public User editUser(User user) {
        if(userRepritory.findUserByUid(user.getUid()) != null) {
            return userRepritory.save(user);
        }else{
            return null;
        }
    }

    @Override
    public void deleteUser(Long uid) {
        userRepritory.deleteById(uid);
    }


    @Override
    public List<User> getUsers() {
        return userRepritory.findAll();
    }
}
