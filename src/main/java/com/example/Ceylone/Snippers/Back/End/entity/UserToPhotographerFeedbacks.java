package com.example.Ceylone.Snippers.Back.End.entity;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.dto.PhotographerDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_To_Photorapher_Feedbacks")
public class UserToPhotographerFeedbacks {
    @Id
    @Column(name = "uToP_FeedbackID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uToP_FeedbackID;

    @Column(name = "uToP_Subject")
    private String uToPSubject;

    @Column(name = "uToP_Discription")
    private String uToPDiscription;

    @Column(name = "repling_User_To_Photographer")
    private String replingUserToPhotographer;

    @Enumerated
    private CommonStatus commonStatus;

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
}



