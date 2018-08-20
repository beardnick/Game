package com.example.demo.reporitory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.example.demo.domain.Round;

public interface RoundReporitory extends JpaRepository<Round, Long> {

     List<Round> findRoundByUid(Long uid);

}
