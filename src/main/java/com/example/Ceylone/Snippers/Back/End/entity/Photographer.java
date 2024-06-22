package com.example.Ceylone.Snippers.Back.End.entity;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "photographer_Registraion")
public class Photographer {
    @Id
    @Column(name = "photographer_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long photographerID;



    @Column(name = "photographer_First_Name")
    private String photographerFirstName;

    @Column(name = "photographer_Last_Name")
    private String photohrapherLastName;

    @Column(name = "photographer_User_Name")
    private String photographerUserName;

    @Column(name = "photographer_Password")
    private String phographerPassword;

    @Column(name = "photographer_NIC")
    private String photographerNIC;

    @Column(name = "photographer_Email")
    private String photographerEmail;

    @Column(name = "photographer_ContactNumber")
    private String photogapherContactNumber;

    @Column(name = "photographer_Gender")
    private String photographerGender;

    @Column(name = "photographer_Address")
    private String photographerAddress;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roll_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SiteRoll siteRoll;

    @Enumerated
    private CommonStatus photographerStatus;



}
