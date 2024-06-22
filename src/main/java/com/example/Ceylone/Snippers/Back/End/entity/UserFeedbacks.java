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
@Table(name = "user_Feedbacks")
public class UserFeedbacks {
    @Id
    @Column(name = "user_Feedback_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userFeedbackID;

    @Column(name = "user_Subject")
    private String userSubject;

    @Column(name = "user_Discription")
    private String userDiscription;

    @Column(name = "repling_This_User")
    private  String replingThisUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;


    @Enumerated
    private CommonStatus commonStatus;
}
