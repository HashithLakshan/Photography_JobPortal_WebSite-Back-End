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
@Table(name = "System_Rolls")
public class SiteRoll {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "roll_ID")
    private Long rollID;

    @Column(name = "roll_Name")
    private String rollName;

    @Enumerated
    private CommonStatus commonStatus;

}
