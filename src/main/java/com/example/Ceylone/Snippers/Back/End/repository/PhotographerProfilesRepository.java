package com.example.Ceylone.Snippers.Back.End.repository;

import com.example.Ceylone.Snippers.Back.End.entity.PhotographersProfiles;
import com.example.Ceylone.Snippers.Back.End.services.PhotographerProfilesServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface PhotographerProfilesRepository extends JpaRepository<PhotographersProfiles,Long> {


    List<PhotographersProfiles> findByCatogoryCatogoryID(Long catogoryID);


}
