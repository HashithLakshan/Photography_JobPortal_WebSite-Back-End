package com.example.Ceylone.Snippers.Back.End.controller;

import com.example.Ceylone.Snippers.Back.End.services.PhotographerGalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/photographerGallery")
public class PhotographerGalleryController {

    private final PhotographerGalleryService photographerGalleryService;

@Autowired
    public PhotographerGalleryController(PhotographerGalleryService photographerGalleryService) {
        this.photographerGalleryService = photographerGalleryService;
    }






}
