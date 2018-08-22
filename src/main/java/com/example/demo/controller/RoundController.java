package com.example.demo.controller;

import com.example.demo.domain.Response;
import com.example.demo.domain.Round;
import com.example.demo.domain.User;
import com.example.demo.reporitory.RoundReporitory;
import com.example.demo.reporitory.UserRepritory;
import com.example.demo.util.DateUtil;
import com.example.demo.util.ResponseUtil;
import com.example.demo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Comparator;

@RestController
@RequestMapping("/score")
public class RoundController {

    @Autowired
    private RoundReporitory roundReporitory;

    @Autowired
    private UserRepritory userRepritory;

    @RequestMapping("/save")
    public Response save(Round round){
        round.setTime(DateUtil.getDate());
        User user = userRepritory.findUserByUid(round.getUid());
        user.setHighScore(StringUtil.max(user.getHighScore(), round.getScore()));
        userRepritory.save(user);
        return ResponseUtil.result(
                roundReporitory.save(round),
                100,
                "更新成功"
                );
    }

    @RequestMapping("/user")
    public Response queryUserScores(@RequestParam("uid")Long uid, @RequestParam("number")Integer number){
        if(number == null){
            number = 100;
        }
        ArrayList<Round> rounds = (ArrayList<Round>)roundReporitory.findRoundByUid(uid);
        rounds.sort(new Comparator<Round>() {
            @Override
            public int compare(Round o1, Round o2) {
                return StringUtil.descend(o1.getScore(), o2.getScore());
            }
        });
        return ResponseUtil.result(
                rounds.size() > number
        ? rounds.subList(0, number) : rounds,
                100,
                "success"
        );
    }

    // TODO: 18-8-20 use sql to replace it
    @RequestMapping("/rank")
    public Response rankList(Integer number){
        if(number == null){
            number = 50;
        }
        ArrayList<User> users = (ArrayList<User>)userRepritory.findAll();
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return StringUtil.descend(o1.getHighScore(), o2.getHighScore());
            }
        });

        System.out.println("/rank: " + users);
        return ResponseUtil.result(users.size() > number
        ? users.subList(0, number) : users,
                100,
                "success"

                );
    }


}
