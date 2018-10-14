package com.example.demo.reporitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.User;

public interface UserRepritory extends JpaRepository<User, Long> {

    User findUserByUidAndPassword(Long uid, String password);
    User findUserByUid(Long uid);
    User findUserByNicknameAndPassword(String nickname, String password);
}
