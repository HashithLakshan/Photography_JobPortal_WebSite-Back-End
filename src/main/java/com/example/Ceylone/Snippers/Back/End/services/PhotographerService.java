package com.example.Ceylone.Snippers.Back.End.services;

import com.example.Ceylone.Snippers.Back.End.dto.PhotographerDto;
import com.example.Ceylone.Snippers.Back.End.entity.Photographer;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;

public interface PhotographerService {


    CommonResponse getAllPhotographers();

    CommonResponse savePhotographer(PhotographerDto photographerDto);

    CommonResponse updatePhotographer(PhotographerDto photographerDto);

    CommonResponse getPhotographerByID(String photographerID);

    PhotographerDto castPhotographerIntoPhotographerDto(Photographer photographer);

    Photographer findByPhotgrapherID(String photographerID);


    PhotographerDto findByID(String photographerID);

    CommonResponse getPhotographerUserName(String photographerUserName);

    CommonResponse updatePhotographerInCommonStatusInactive(String photographerID);

    CommonResponse getAllPhotographersInactive();

    CommonResponse updateUserInCommonStatusDelete(String photographerID);

    CommonResponse updateUserInCommonStatusActive(String photographerID);
}
