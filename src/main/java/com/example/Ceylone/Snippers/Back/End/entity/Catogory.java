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
@Table(name = "catogory")
public class Catogory {
    @Id
    @Column(name = "catagory_ID")
    private Long catogoryID;

    @Column(name = "catogory_Name")
    private String catogoryName;


    private String catagoryphotoLink;


    @Column(name = "catogory_Discription")
    private String catogoryDiscription;


    @Enumerated
    private CommonStatus commonStatus;


}
