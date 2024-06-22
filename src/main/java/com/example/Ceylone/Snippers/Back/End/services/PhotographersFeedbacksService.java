package com.example.Ceylone.Snippers.Back.End.services;

import com.example.Ceylone.Snippers.Back.End.dto.PhotographersFeedbacksDto;
import com.example.Ceylone.Snippers.Back.End.entity.PhotographersFeedbacks;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;

public interface PhotographersFeedbacksService {


    CommonResponse getAllFeedbackActives();

    CommonResponse getAllFeedbacksInactive();

    CommonResponse saveFeedbacksPhotographers(PhotographersFeedbacksDto photographersFeedbacksDto);

    CommonResponse updatePhotographerFeedbacksReply_YES(String photographerFeedbackID);

    CommonResponse updatePhotographerFeedbacksInactive(String photographerFeedbackID);

    CommonResponse updatePhotographerFeedbacksDelete(String photographerFeedbackID);

    CommonResponse updatePhotographerFeedbacksActive(String photographerFeedbackID);

    CommonResponse getPhotographerFeedbacksDetails(String photographerID);

    CommonResponse getPhotographerFeedbacksDetails1( String photographerUserName);

}
