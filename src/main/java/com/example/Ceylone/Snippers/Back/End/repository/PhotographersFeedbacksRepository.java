package com.example.Ceylone.Snippers.Back.End.repository;

import com.example.Ceylone.Snippers.Back.End.entity.PhotographersFeedbacks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface PhotographersFeedbacksRepository extends JpaRepository<PhotographersFeedbacks,Long> {





    List<PhotographersFeedbacks> findByPhotographerPhotographerUserName(String PhotographerUserName);


    List<PhotographersFeedbacks> findByPhotographer_PhotographerID(Long photographerID);
}
