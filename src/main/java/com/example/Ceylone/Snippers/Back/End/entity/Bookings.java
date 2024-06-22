package com.example.Ceylone.Snippers.Back.End.entity;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Bookings")
public class Bookings {
    @Id
    @Column(name = "booking_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookingID;
    //-------------------foreigen-----------------\\

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "photographer_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Photographer photographer;
    //-------------------------------------
    @Column(name = "shoot_Type")
    private String ShootType;

    @Column(name = "shoot_Time_Duration")
    private String shootTimeDuration;

    @Column(name = "location")
    private String location;

    @Column(name = "date")
    private LocalDate dates;

    @Column(name = "tell_Us_More")
    private String tellUsMoreDiscription;

    @Enumerated
    private CommonStatus commonStatus;
}
