package com.example.Ceylone.Snippers.Back.End.services.serviceImpl;

import com.example.Ceylone.Snippers.Back.End.constant.CommonMessage;
import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.dto.PhotographerDto;
import com.example.Ceylone.Snippers.Back.End.dto.PhotographersFeedbacksDto;
import com.example.Ceylone.Snippers.Back.End.dto.UserDto;
import com.example.Ceylone.Snippers.Back.End.dto.UserFeedbacksDto;
import com.example.Ceylone.Snippers.Back.End.entity.PhotographersFeedbacks;
import com.example.Ceylone.Snippers.Back.End.entity.UserFeedbacks;
import com.example.Ceylone.Snippers.Back.End.repository.UserFeedbacksRepository;
import com.example.Ceylone.Snippers.Back.End.services.UserFeedbacksService;
import com.example.Ceylone.Snippers.Back.End.services.UserService;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import com.example.Ceylone.Snippers.Back.End.utill.CommonValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Service
public class UserFeedbacksServiceImpl implements UserFeedbacksService {

    private  final UserFeedbacksRepository userFeedbacksRepository;

    private  final UserService userService;

@Autowired
    public UserFeedbacksServiceImpl(UserFeedbacksRepository userFeedbacksRepository, UserService userService) {
        this.userFeedbacksRepository = userFeedbacksRepository;
         this.userService = userService;
}


    @Override
    public CommonResponse getAllFeedbackActives() {
        CommonResponse commonResponse = new CommonResponse();
        List<UserFeedbacksDto> userFeedbacksDtoList = new ArrayList<>();
        try {
            Predicate<UserFeedbacks> filterOnStatus = userFeedbacks  -> userFeedbacks.getCommonStatus() == CommonStatus.ACTIVE;
            userFeedbacksDtoList= userFeedbacksRepository.findAll().stream().filter(filterOnStatus).map(this::castUsersFeedbacksIntoUsersFeedbacksDto).collect(Collectors.toList());

            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(userFeedbacksDtoList));
        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserFeedacksService -> getAll(ActiveFeedbacks)" + e);
        }
        return commonResponse;
    }


    @Override
    public CommonResponse getAllFeedbacksInactive() {
        CommonResponse commonResponse = new CommonResponse();
        List<UserFeedbacksDto> userFeedbacksDtoList = new ArrayList<>();
        try {
            Predicate<UserFeedbacks> filterOnStatus = userFeedbacks  -> userFeedbacks.getCommonStatus() == CommonStatus.INACTIVE;
            userFeedbacksDtoList= userFeedbacksRepository.findAll().stream().filter(filterOnStatus).map(this::castUsersFeedbacksIntoUsersFeedbacksDto).collect(Collectors.toList());

            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(userFeedbacksDtoList));
        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserFeedacksService -> getAll(InactiveFeedbacks)" + e);
        }
        return commonResponse;
    }

    @Override
    public CommonResponse saveFeedbacksUsers(UserFeedbacksDto userFeedbacksDto) {
        CommonResponse commonResponse = new CommonResponse();

        try {
            List<String> validationList = this.UserFeedbacksValidation(userFeedbacksDto);
            if (!validationList.isEmpty()) {
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }
            UserFeedbacks userFeedbacks = castUsersFeedbacksDTOIntoUsersFeedbacks(userFeedbacksDto);
            userFeedbacksRepository.save(userFeedbacks);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(userFeedbacks));


        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UsersFeedbacksService -> save(Feedbacks)" + e);
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updateUsersFeedbacksReply_YES(String userFeedbackID) {
    CommonResponse commonResponse = new CommonResponse();
        try {

            UserFeedbacks userFeedbacks= userFeedbacksRepository.findById(Long.valueOf(userFeedbackID)).get();
            if(userFeedbacks!=null){
                userFeedbacks.setReplingThisUser("Yes");
                userFeedbacksRepository.save(userFeedbacks);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(userFeedbacks));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("This UserFeedBack not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserFeedbacksService -> update(Yes)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating userFeedbacksYes"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updateUsersFeedbacksInactive(String userFeedbackID) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            UserFeedbacks userFeedbacks= userFeedbacksRepository.findById(Long.valueOf(userFeedbackID)).get();
            if(userFeedbacks!=null){
                userFeedbacks.setCommonStatus(CommonStatus.INACTIVE);
                userFeedbacksRepository.save(userFeedbacks);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(userFeedbacks));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("This UserFeedBack not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserFeedbacksService -> update(Inactive)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating UserFeedbacksInactives"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updateUserFeedbacksDelete(String userFeedbackID) {
            CommonResponse commonResponse = new CommonResponse();
            try {

                UserFeedbacks userFeedbacks= userFeedbacksRepository.findById(Long.valueOf(userFeedbackID)).get();
                if(userFeedbacks!=null){
                    userFeedbacks.setCommonStatus(CommonStatus.DELETE);
                    userFeedbacksRepository.save(userFeedbacks);
                    commonResponse.setStatus(true);
                    commonResponse.setPayload(Collections.singletonList(userFeedbacks));
                } else {
                    commonResponse.setErrorMessages(Collections.singletonList("This UserFeedBack not found"));
                }

            } catch (Exception e) {
                LOGGER.error("/**************** Exception in UserFeedbacksService -> update(Delete)" + e);
                commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating UserFeedbacksDelete"));
            }
            return commonResponse;
    }

    @Override
    public CommonResponse updateUsersFeedbacksActive(String userFeedbackID) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            UserFeedbacks userFeedbacks= userFeedbacksRepository.findById(Long.valueOf(userFeedbackID)).get();
            if(userFeedbacks!=null){
                userFeedbacks.setCommonStatus(CommonStatus.ACTIVE);
                userFeedbacksRepository.save(userFeedbacks);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(userFeedbacks));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("This UserFeedBack not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserFeedbacksService -> update(Active)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating UserFeedbacksActive"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getUserFeedbacksDetails(String userID) {
        CommonResponse commonResponse = new CommonResponse();


        UserDto userDto = new UserDto();
        UserFeedbacksDto  userFeedbacksDto =new UserFeedbacksDto();

        Predicate<UserFeedbacks> filterOnStatus = userFeedbacks    -> userFeedbacks.getCommonStatus() != CommonStatus.DELETE;

        // Retrieve bookings for the photographer and filter them
        List<UserFeedbacks> userFeedbacksList = userFeedbacksRepository.findByUserUserID(Long.valueOf(userID));
        List<UserFeedbacksDto> userFeedbacksDtoList = userFeedbacksList.stream().filter(filterOnStatus).map(this::castUsersFeedbacksIntoUsersFeedbacksDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(userFeedbacksDtoList));
        return commonResponse;
    }

    @Override
    public CommonResponse getUserFeedbacksDetails1(String userName) {
        CommonResponse commonResponse = new CommonResponse();


        UserDto userDto = new UserDto();
        UserFeedbacksDto  userFeedbacksDto =new UserFeedbacksDto();

        Predicate<UserFeedbacks> filterOnStatus = userFeedbacks    -> userFeedbacks.getCommonStatus() != CommonStatus.DELETE;

        // Retrieve bookings for the photographer and filter them
        List<UserFeedbacks> userFeedbacksList = userFeedbacksRepository.findByUserUserName(userName);
        List<UserFeedbacksDto> userFeedbacksDtoList = userFeedbacksList.stream().filter(filterOnStatus).map(this::castUsersFeedbacksIntoUsersFeedbacksDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(userFeedbacksDtoList));
        return commonResponse;
    }

    private List<String> UserFeedbacksValidation(UserFeedbacksDto userFeedbacksDto) {

        List<String> validationList = new ArrayList<>();
        if (CommonValidation.stringNullValidation(userFeedbacksDto.getUserDiscription()))
            validationList.add(CommonMessage.SELECT_YOUR_SHOOT_TYPE);

        return validationList;
    }

    private UserFeedbacks castUsersFeedbacksDTOIntoUsersFeedbacks(UserFeedbacksDto userFeedbacksDto) {
        UserFeedbacks userFeedbacks = new UserFeedbacks();

     userFeedbacks.setUserSubject(userFeedbacksDto.getUserSubject());
     userFeedbacks.setUserDiscription(userFeedbacksDto.getUserDiscription());
     userFeedbacks.setReplingThisUser("No");
     userFeedbacks.setCommonStatus(userFeedbacksDto.getCommonStatus());
     userFeedbacks.setUser(userService.findByUserID(userFeedbacksDto.getUserDto().getUserID()));

        return userFeedbacks;
    }
    private UserFeedbacksDto castUsersFeedbacksIntoUsersFeedbacksDto(UserFeedbacks userFeedbacks) {
UserFeedbacksDto userFeedbacksDto = new UserFeedbacksDto();
userFeedbacksDto.setUserFeedbackID(String.valueOf(userFeedbacks.getUserFeedbackID()));
userFeedbacksDto.setUserSubject(userFeedbacks.getUserSubject());
userFeedbacksDto.setUserDiscription(userFeedbacks.getUserDiscription());
userFeedbacksDto.setReplingThisUser(userFeedbacks.getReplingThisUser());
userFeedbacksDto.setCommonStatus(userFeedbacks.getCommonStatus());
userFeedbacksDto.setUserDto(userService.findByUserID(userFeedbacks.getUser().getUserID()));
        return userFeedbacksDto;
    }
}
