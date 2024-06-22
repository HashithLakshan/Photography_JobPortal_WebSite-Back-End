package com.example.Ceylone.Snippers.Back.End.entity;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Admin_And_SiteHandling")
public class AdminAndSiteHandaling {
    @Id
    @Column(name = "AdminID")
    private Long adminID;

    @Column(name = "Admin_User_Name")
    private String AdminUserName;

    @Column(name = "Admin_Password")
    private String password;


    @Column(name = "Join_WithUs_Discription")
    private String joinWithUsDiscription;

    @Column(name = "home_CoverPhotoUrl")
    private String homeCoverPhotoUrl;

    @Column(name = "home_ContactUsPhotoUrl")
    private String homeContactUsPhotoUrl;

    @Column(name = "choose_Snipper_Cover_PhotoUrl")
    private String choose_Snipper_Cover_PhotoUrl;

    @Column(name = "user_Login_PhotoUrl")
    private String userLoginPhotoUrl;

    @Column(name = "photographer_Login_PhotoUrl")
    private String photographer_Login_PhotoUrl;

    @Column(name = "admin_Login_PhotoUrl")
    private String adminLoginPhotoUrl;

    @Enumerated
    private CommonStatus commonStatus;

}
