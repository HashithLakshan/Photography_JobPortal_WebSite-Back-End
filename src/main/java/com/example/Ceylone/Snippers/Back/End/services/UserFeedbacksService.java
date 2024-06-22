package com.example.Ceylone.Snippers.Back.End.services;

import com.example.Ceylone.Snippers.Back.End.dto.UserFeedbacksDto;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;

public interface UserFeedbacksService {
    CommonResponse getAllFeedbackActives();

    CommonResponse getAllFeedbacksInactive();

    CommonResponse saveFeedbacksUsers(UserFeedbacksDto userFeedbacksDto);

    CommonResponse updateUsersFeedbacksReply_YES(String userFeedbackID);

    CommonResponse updateUsersFeedbacksInactive(String userFeedbackID);

    CommonResponse updateUserFeedbacksDelete(String userFeedbackID);

    CommonResponse updateUsersFeedbacksActive(String userFeedbackID);

    CommonResponse getUserFeedbacksDetails(String userID);

    CommonResponse getUserFeedbacksDetails1(String userName);
}
