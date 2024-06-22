package com.example.Ceylone.Snippers.Back.End.dto;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhotographerGalleryDto {
    private String galleryID;
    private String portraitImageUrl;
    private String landscapeImageUrl;
    private CommonStatus commonStatus;
    private PhotographersProfilesDto photographersProfilesDto;


}
