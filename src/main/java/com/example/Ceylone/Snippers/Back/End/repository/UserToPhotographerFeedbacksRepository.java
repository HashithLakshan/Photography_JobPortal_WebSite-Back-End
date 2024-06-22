package com.example.Ceylone.Snippers.Back.End.repository;

import com.example.Ceylone.Snippers.Back.End.entity.UserToPhotographerFeedbacks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface UserToPhotographerFeedbacksRepository extends JpaRepository<UserToPhotographerFeedbacks,Long> {
    List<UserToPhotographerFeedbacks> findByPhotographer_PhotographerID(Long photographerID);

    List<UserToPhotographerFeedbacks> findByUserUserNameAndPhotographerPhotographerID(String userName, Long photographerID);

    List<UserToPhotographerFeedbacks> findByUserUserIDAndPhotographerPhotographerID(Long userID, Long PhotographerID);
}
