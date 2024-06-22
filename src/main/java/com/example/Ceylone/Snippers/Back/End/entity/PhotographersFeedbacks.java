package com.example.Ceylone.Snippers.Back.End.entity;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "photographers_Feedbacks")
public class PhotographersFeedbacks {
    @Id
    @Column(name = "photographer_Feedback_ID")
    private Long photographerFeedbackID;

    @Column(name = "photographer_Subject")
    private String photographerSubject;

    @Column(name = "photographer_Discription")
    private String photographerDiscription;

    @Column(name = "repling_This_Photographer")
    private  String replingThisPhotographer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "photographer_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Photographer photographer;

    @Enumerated
    private CommonStatus commonStatus;
}
