package com.example.Ceylone.Snippers.Back.End.dto;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotographersFeedbacksDto {
    private String photographerFeedbackID;
    private String photographerSubject;
    private String photographerDiscription;
    private  String replingThisPhotographer;
    private PhotographerDto photographerDto;
    private CommonStatus commonStatus;
}
