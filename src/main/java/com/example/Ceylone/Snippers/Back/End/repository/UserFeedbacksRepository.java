package com.example.Ceylone.Snippers.Back.End.repository;

import com.example.Ceylone.Snippers.Back.End.entity.UserFeedbacks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface UserFeedbacksRepository extends JpaRepository<UserFeedbacks,Long> {
    List<UserFeedbacks> findByUserUserID(Long userID);

    List<UserFeedbacks> findByUserUserName(String userName);
}
