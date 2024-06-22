package com.example.Ceylone.Snippers.Back.End.repository;

import com.example.Ceylone.Snippers.Back.End.entity.Pakeges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface PakegesRepository extends JpaRepository<Pakeges,Long> {
    List<Pakeges> findByPhotographersProfilesProfileID(Long profileID);


    List<Pakeges> findByPakegeCode(Long pakegeCode);

}
