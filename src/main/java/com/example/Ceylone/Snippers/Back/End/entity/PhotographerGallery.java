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
@Table(name = "photographers_Gallery")
public class PhotographerGallery {
    @Id
    @Column(name = "gallery_ID")
    private Long galleryID;

    @Column(name = "portrait_ImageUrl")
    private String portraitImageUrl;

    @Column(name = "landscape_ImageUrl")
    private String landscapeImageUrl;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PhotographersProfiles photographersProfiles;

    @Enumerated
    private CommonStatus commonStatus;

}
