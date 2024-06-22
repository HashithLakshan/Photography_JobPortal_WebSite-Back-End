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
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "photographers_Profiles")
public class PhotographersProfiles {
    @Id
    @Column(name = "Profile_ID")
    private Long profileID;

    @Column(name = "profile_Photo_ImageUrl")
    private String profilePhotoImageUrl;

    @Column(name = "cover_Photo_ImageUrl")
    private String coverPhotoImageUrl;

    @Column(name = "profile_Name")
    private String ProfileName;

    @Column(name = "nick_Name")
    private String nickName;

    @Column(name = "profile_About")
    private String about;

    @Column(name = "official_Email")
    private String officialEmail;

    @Column(name = "official_PhoneNumber")
    private String officialPhoneNo;

    @Column(name = "official_provience")
    private String officialprovience;

//    ----------------- Forign Key-----------------

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "photographer_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Photographer photographer;

    @OneToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "catagory_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Catogory catogory;


//---------------------------------------------------------

    @Enumerated
    private CommonStatus commonStatus;
}
