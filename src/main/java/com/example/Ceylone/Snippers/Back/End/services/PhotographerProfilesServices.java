package com.example.Ceylone.Snippers.Back.End.services;

import com.example.Ceylone.Snippers.Back.End.dto.PhotographerDto;
import com.example.Ceylone.Snippers.Back.End.dto.PhotographersProfilesDto;
import com.example.Ceylone.Snippers.Back.End.entity.Photographer;
import com.example.Ceylone.Snippers.Back.End.entity.PhotographersProfiles;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface PhotographerProfilesServices {
    CommonResponse getAllUProfilesActives();


    CommonResponse updateProfiles(PhotographersProfilesDto photographersProfilesDto);

    CommonResponse getProfileByID(String profileID);

    PhotographersProfiles findByPhotographerProfileID(String profileID);

    PhotographersProfilesDto castPhotographerProfilesIntoPhotographerProfilesDto(PhotographersProfiles photographersProfiles);


    CommonResponse saveProfile(PhotographersProfilesDto photographersProfilesDto);

    CommonResponse getAllProfilesInactive();

    CommonResponse updateProfilesDeleteInactive(String profileID);

    CommonResponse updateProfilesActive(String profileID);

    CommonResponse updateProfilesDelete(String profileID);

    CommonResponse getAllProfilesDelete();

    CommonResponse getProfileBycatagoryID(String catogoryID);
}
