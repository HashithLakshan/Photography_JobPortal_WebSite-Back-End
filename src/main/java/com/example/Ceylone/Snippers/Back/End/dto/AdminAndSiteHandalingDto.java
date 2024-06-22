package com.example.Ceylone.Snippers.Back.End.dto;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminAndSiteHandalingDto {
    private String adminID;
    private String AdminUserName;
    private String password;
    private String joinWithUsDiscription;
    private String homeCoverPhotoUrl;
    private String homeContactUsPhotoUrl;
    private String choose_Snipper_Cover_PhotoUrl;
    private String userLoginPhotoUrl;
    private String photographer_Login_PhotoUrl;
    private String adminLoginPhotoUrl;
    private CommonStatus commonStatus;



}
