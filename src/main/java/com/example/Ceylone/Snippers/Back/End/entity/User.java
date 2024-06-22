package com.example.Ceylone.Snippers.Back.End.entity;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "user_Registraion")
public class User {
@Id
@Column(name = "user_ID")
    private Long userID;

    @Column(name = "user_Name")
    private String userName;

    @Column(name = "user_Password")
    private String password;

    @Column(name = "user_First_Name")
    private String userFirstName;

    @Column(name = "user_Last_Name")
    private String userLstName;

    @Column(name = "user_ProfileImgUrl")
    private String userProfileImgUrl;


    @Column(name = "gender")
    private String gender;

    @Column(name = "user_Email")
    private String userEmail;

    @Column(name = "user_Phone_Number")
    private String userContactNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roll_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SiteRoll siteRoll;



    @Enumerated
    private CommonStatus commonStatus;


}
