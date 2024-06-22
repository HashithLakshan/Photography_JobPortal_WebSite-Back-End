package com.example.Ceylone.Snippers.Back.End.dto;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private String userID;
    private String userName;
    private String userProfileImgUrl;
    private String password;
    private String userFirstName;
    private String userLstName;
    private String gender;
    private String userEmail;
    private String userContactNumber;
    private SiteRollDto siteRollDto;
    private CommonStatus commonStatus;



}


