package com.example.Ceylone.Snippers.Back.End.services;


import com.example.Ceylone.Snippers.Back.End.dto.UserToPhotographerFeedbacksDto;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;

public interface UserToPhotographerFeedbacksService  {
    CommonResponse getAllPhotographerFeedbacksActive(String photographerId);

    CommonResponse saveFeedback(UserToPhotographerFeedbacksDto userToPhotographerFeedbacksDto);

    CommonResponse getAllPhotographerFeedbacksInactive(String photographerID);

    CommonResponse updateInactive(String uToPFeedbackID);

    CommonResponse updateActive(String uToPFeedbackID);

    CommonResponse updateDelete(String uToPFeedbackID);

    CommonResponse updateYes(String uToPFeedbackID);

    CommonResponse getFeedabacksSearchName(String userName, String photographerID);

    CommonResponse getFeedabacksSearchID(String userID, String photographerID);
}
