package com.example.Ceylone.Snippers.Back.End.repository;

import com.example.Ceylone.Snippers.Back.End.entity.PhotographerGallery;
import com.example.Ceylone.Snippers.Back.End.services.PhotographerGalleryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface PhotographerGalleryRepository extends JpaRepository<PhotographerGallery,Long> {
}
