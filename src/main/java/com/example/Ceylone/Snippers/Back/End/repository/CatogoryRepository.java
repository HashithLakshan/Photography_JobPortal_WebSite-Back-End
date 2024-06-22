package com.example.Ceylone.Snippers.Back.End.repository;

import com.example.Ceylone.Snippers.Back.End.entity.Catogory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
@EnableJpaRepositories
@Repository

public interface CatogoryRepository extends JpaRepository<Catogory,Long> {



}
