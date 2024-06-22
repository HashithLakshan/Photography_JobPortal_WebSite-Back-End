package com.example.Ceylone.Snippers.Back.End.dto;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhotographerDto {
    private String photographerID;
    private String ShootType;
    private String photographerFirstName;
    private String photohrapherLastName;
    private String photographerUserName;
    private String phographerPassword;
    private String photographerNIC;
    private String photographerEmail;
    private String photogapherContactNumber;
    private String photographerGender;
    private String photographerAddress;
    private SiteRollDto siteRollDto;
    private CommonStatus commonStatus;
}
