package com.example.demo.service.iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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
    public Long[] findAllScore(Long uid) {
        ArrayList<Long> array = (ArrayList)roundReporitory.findRoundByUid(uid);
        Long[] scores = new Long[array.size()];
        for (int i = 0; i < scores.length ; i++) {
           scores[i] = array.get(i);
        }
        return scores;
    }

    @Override
    public User createUser(User user) {
        return userRepritory.save(user);
    }

    @Override
    public User editUser(User user) {
        return userRepritory.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepritory.delete(user);
    }


    @Override
    public List<User> getUsers() {
        return userRepritory.findAll();
    }
}
