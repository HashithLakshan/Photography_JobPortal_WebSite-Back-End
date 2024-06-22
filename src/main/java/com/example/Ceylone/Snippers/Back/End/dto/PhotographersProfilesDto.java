package com.example.Ceylone.Snippers.Back.End.dto;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.entity.Photographer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhotographersProfilesDto {
    private String profileID;
    private String photographerID;
    private String profilePhotoImageUrl;
    private String coverPhotoImageUrl;
    private String profileName;
    private String profileNikeName;
    private String about;
    private String officialEmail;
    private String officialPhoneNo;
    private String officialProvince;
    private CommonStatus commonStatus;
    private CatogoryDto catogoryDto;
private PhotographerDto photographerDto;
}
