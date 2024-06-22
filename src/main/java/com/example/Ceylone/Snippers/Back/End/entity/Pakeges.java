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
@Table(name = "Pakeges")
public class Pakeges {
    @Id
    @Column(name = "pakege_Code")
    private Long pakegeCode;


    @Column(name = "pakege_Price")
    private int pakegePrice;

    @Column(name = "pakege_Name")
    private String pakegeName;


    @Column(name = "paragraph1")
    private String paragraph1;

    @Column(name = "paragraph2")
    private String paragraph2;

    @Column(name = "paragraph3")
    private String paragraph3;

    @Column(name = "paragraph4")
    private String paragraph4;

    @Column(name = "paragraph5")
    private String paragraph5;

    //-----------------------Foreign Key------------------------

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PhotographersProfiles photographersProfiles;


    //----------------------------------------------------

    @Enumerated
    private CommonStatus commonStatus;
}
