package com.example.Ceylone.Snippers.Back.End.services.serviceImpl;

import com.example.Ceylone.Snippers.Back.End.repository.PhotographerGalleryRepository;
import com.example.Ceylone.Snippers.Back.End.services.PhotographerGalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotographerGalleryServiceImpl implements PhotographerGalleryService {

    private final PhotographerGalleryRepository photographerGalleryRepository;

    @Autowired
    public PhotographerGalleryServiceImpl(PhotographerGalleryRepository photographerGalleryRepository) {
        this.photographerGalleryRepository = photographerGalleryRepository;
    }


}
