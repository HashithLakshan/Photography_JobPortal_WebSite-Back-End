package com.example.Ceylone.Snippers.Back.End.dto;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserFeedbacksDto {
    private String userFeedbackID;
    private String userSubject;
    private String userDiscription;
    private  String replingThisUser;
    private  UserDto userDto;
    private CommonStatus commonStatus;
}
