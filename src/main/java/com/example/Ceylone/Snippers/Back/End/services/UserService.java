package com.example.Ceylone.Snippers.Back.End.services;

import com.example.Ceylone.Snippers.Back.End.dto.UserDto;
import com.example.Ceylone.Snippers.Back.End.entity.User;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    CommonResponse getAllUser();

    CommonResponse saveUser(UserDto userDto);

    CommonResponse updateUser(UserDto userDto);

    CommonResponse getUserByID(String userID);

    UserDto castUserIntoUSerDto(User user);

    User findByUserID(String userID);

    UserDto findById(Long userID);

    UserDto findByUserID(Long userID);

    CommonResponse getUserByName(String userName);

    CommonResponse updateUserInCommonStatusInactive(String userID);

    CommonResponse updateUserInCommonStatusDelete(String userID);

    CommonResponse updateUserInCommonStatusActive(String userID);



//    CommonResponse getUserByName(String userName);
}
