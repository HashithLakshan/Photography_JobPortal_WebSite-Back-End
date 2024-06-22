package com.example.Ceylone.Snippers.Back.End.dto;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.entity.PhotographersProfiles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToPhotographerFeedbacksDto {
    private String uToPFeedbackID;
    private String uToPSubject;
    private String uToPDiscription;
    private  String replingUserToPhotographer;
    private CommonStatus commonStatus;
    private PhotographerDto photographerDto;

    private  UserDto  userDto;
}
