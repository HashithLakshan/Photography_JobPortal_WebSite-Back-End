package com.example.Ceylone.Snippers.Back.End.services.serviceImpl;

import com.example.Ceylone.Snippers.Back.End.constant.CommonMessage;
import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.dto.UserDto;
import com.example.Ceylone.Snippers.Back.End.entity.User;
import com.example.Ceylone.Snippers.Back.End.repository.UserRepository;
import com.example.Ceylone.Snippers.Back.End.services.SiteRollsService;
import com.example.Ceylone.Snippers.Back.End.services.UserService;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import com.example.Ceylone.Snippers.Back.End.utill.CommonValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Service
public  class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final SiteRollsService siteRollsService;

@Autowired
    public UserServiceImpl(UserRepository userRepository, SiteRollsService siteRollsService) {
        this.userRepository = userRepository;
        this.siteRollsService = siteRollsService;
    }



   // ------------------------------------ All Users ----------------------------------------
    @Override
    public CommonResponse getAllUser() {
        CommonResponse commonResponse=new CommonResponse();
        List<UserDto> userDtoList= new ArrayList<>();
        try {
            Predicate<User> filterOnStatus= userEntity -> userEntity.getCommonStatus()== CommonStatus.ACTIVE;
            userDtoList=userRepository.findAll().stream().filter(filterOnStatus).map(this::castUserIntoUSerDto).collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(userDtoList));
        }catch (Exception e){
            LOGGER.error("/**************** Exception in UserService -> getAll()"+e);
        }
        return commonResponse;
    }
    public UserDto castUserIntoUSerDto(User user) {
        UserDto userDto=new UserDto();


        userDto.setUserName(user.getUserName());
        userDto.setUserID(String.valueOf(user.getUserID()));
        userDto.setPassword(user.getPassword());
        userDto.setUserFirstName(user.getUserFirstName());
        userDto.setUserLstName(user.getUserLstName());
        userDto.setGender(user.getGender());
        userDto.setUserEmail(user.getUserEmail());
        userDto.setUserContactNumber(user.getUserContactNumber());
        userDto.setCommonStatus(user.getCommonStatus());
        userDto.setUserProfileImgUrl(user.getUserProfileImgUrl());

        userDto.setSiteRollDto(siteRollsService.castSiteRollsIntoSiteRollsDto(user.getSiteRoll()));


        return userDto;

    }

    @Override
    public User findByUserID(String userID) {
        return userRepository.findById(Long.valueOf(userID)).get();

    }

    @Override
    public UserDto findById(Long userID) {
        User user = userRepository.findById(userID).get();
        UserDto userDto =new UserDto();
        userDto.setUserName(user.getUserName());
        userDto.setUserID(String.valueOf(user.getUserID()));
        userDto.setPassword(user.getPassword());
        userDto.setUserFirstName(user.getUserFirstName());
        userDto.setUserLstName(user.getUserLstName());
        userDto.setGender(user.getGender());
        userDto.setUserEmail(user.getUserEmail());
        userDto.setUserContactNumber(user.getUserContactNumber());
        userDto.setCommonStatus(user.getCommonStatus());
        userDto.setUserProfileImgUrl(user.getUserProfileImgUrl());
        return userDto;
    }

    @Override
    public UserDto findByUserID(Long userID) {
        User user = userRepository.findByUserID(userID);
        UserDto userDto =new UserDto();
        userDto.setUserName(user.getUserName());
        userDto.setUserID(String.valueOf(user.getUserID()));
        userDto.setPassword(user.getPassword());
        userDto.setUserFirstName(user.getUserFirstName());
        userDto.setUserLstName(user.getUserLstName());
        userDto.setGender(user.getGender());
        userDto.setUserEmail(user.getUserEmail());
        userDto.setUserContactNumber(user.getUserContactNumber());
        userDto.setCommonStatus(user.getCommonStatus());
        userDto.setUserProfileImgUrl(user.getUserProfileImgUrl());
        return userDto;
    }

    @Override
    public CommonResponse getUserByName(String userName) {
        CommonResponse commonResponse=new CommonResponse();
        User user;
        UserDto userDto = new UserDto();

        try {
            user = userRepository.getByUserName(userName);
            userDto = castUserIntoUSerDto(user);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(userDto));

        }
        catch (Exception e){
            LOGGER.error("/**************** Exception in UserService -> getUserById()"+e);
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updateUserInCommonStatusInactive(String userID) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            User exitsUser= userRepository.findById(Long.valueOf(userID)).get();
            if(exitsUser!=null){
                exitsUser.setCommonStatus(CommonStatus.INACTIVE);
                userRepository.save(exitsUser);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(exitsUser));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("User not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserService -> update(Inactive)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating userInactive"));
        }
        return commonResponse;

    }

    @Override
    public CommonResponse updateUserInCommonStatusDelete(String userID) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            User exitsUser= userRepository.findById(Long.valueOf(userID)).get();
            if(exitsUser!=null){
                exitsUser.setCommonStatus(CommonStatus.DELETE);
                userRepository.save(exitsUser);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(exitsUser));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("User not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserService -> update(Inactive)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating userDelete"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updateUserInCommonStatusActive(String userID) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            User exitsUser= userRepository.findById(Long.valueOf(userID)).get();
            if(exitsUser!=null){
                exitsUser.setCommonStatus(CommonStatus.ACTIVE);
                userRepository.save(exitsUser);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(exitsUser));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("User not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserService -> update(Inactive)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating userActive"));
        }
        return commonResponse;
    }


    //--------------------------Save User ----------------------------------------------------
@Override
    public CommonResponse saveUser(UserDto userDto) {
    CommonResponse commonResponse = new CommonResponse();
    User user;
    try {
//        List<String> validationList = this.userValidation(userDto);
//        if (!validationList.isEmpty()) {
//            commonResponse.setErrorMessages(validationList);
//            return commonResponse;
//        }

        user = castUserDTOIntoUser(userDto);
        user = userRepository.save(user);
        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(user));
    } catch (Exception e) {
        LOGGER.error("/**************** Exception in UserService -> save()" + e);
        commonResponse.setErrorMessages(Collections.singletonList("An error occurred while saving the user."));
    }
    return commonResponse;
    }

    private User castUserDTOIntoUser(UserDto userDto) throws IOException {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setUserFirstName(userDto.getUserFirstName());
        user.setUserLstName(userDto.getUserLstName());
        user.setGender(userDto.getGender());
        user.setUserID(Long.valueOf(userDto.getUserID()));
        user.setUserEmail(userDto.getUserEmail());
        user.setUserContactNumber(userDto.getUserContactNumber());
        user.setCommonStatus(userDto.getCommonStatus());
        user.setUserProfileImgUrl(userDto.getUserProfileImgUrl());
        user.setSiteRoll(siteRollsService.findBySiteRollID(userDto.getSiteRollDto().getRollID()));



        return user;
    }


    // -------------------------------------Update User ---------------------------------------
@Override
public CommonResponse updateUser(UserDto userDto) {
    CommonResponse commonResponse = new CommonResponse();
    try {
//        List<String> validationList = this.userValidation(userDto);
//        if (!validationList.isEmpty()) {
//            commonResponse.setErrorMessages(validationList);
//            return commonResponse;
//        }

        User exitsUser= userRepository.findById(Long.valueOf(userDto.getUserID())).get();
        if(exitsUser!=null){
            exitsUser.setUserName(userDto.getUserName());
            exitsUser.setUserFirstName(userDto.getUserFirstName());
            exitsUser.setUserLstName(userDto.getUserLstName());
            exitsUser.setGender(userDto.getGender());
            exitsUser.setUserEmail(userDto.getUserEmail());
            exitsUser.setUserContactNumber(userDto.getUserContactNumber());
            exitsUser.setCommonStatus(userDto.getCommonStatus());


            exitsUser.setSiteRoll(siteRollsService.findBySiteRollID(userDto.getSiteRollDto().getRollID()));


            userRepository.save(exitsUser);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(exitsUser));
        } else {
            commonResponse.setErrorMessages(Collections.singletonList("User not found"));
        }

    } catch (Exception e) {
        LOGGER.error("/**************** Exception in UserService -> update()" + e);
        commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating user"));
    }
    return commonResponse;
}
//------------------------------------------- User ID get All details --------------------------------


    @Override
    public CommonResponse getUserByID(String userID) {
        CommonResponse commonResponse=new CommonResponse();
        User user;
        UserDto userDto = new UserDto();

        try {
            user =userRepository.findById(Long.valueOf(userID)).get();
            userDto = castUserIntoUSerDto(user);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(userDto));

        }
        catch (Exception e){
            LOGGER.error("/**************** Exception in UserService -> getUserById()"+e);
        }
        return commonResponse;
    }


    //----------------------------------Vlidation -------------------------------------------
    private List<String> userValidation(UserDto userDto) {
        List<String> validationList = new ArrayList<>();
        if (CommonValidation.stringNullValidation(userDto.getUserName()))
            validationList.add(CommonMessage.EMPTY_USERNAME);
        if(CommonValidation.stringNullValidation(userDto.getPassword()))
            validationList.add(CommonMessage.EMPTY_PASSWORD);
        if(CommonValidation.stringNullValidation(userDto.getUserFirstName()))
            validationList.add(CommonMessage.EMPTY_FIRST_NAME);
        if(CommonValidation.stringNullValidation(userDto.getUserLstName()))
            validationList.add(CommonMessage.EMPTY_USER_LAST_NAME);
        if(CommonValidation.stringNullValidation(userDto.getGender()))
            validationList.add(CommonMessage.EMPTY_USER_GENDER);
        if(CommonValidation.stringNullValidation(userDto.getUserEmail()))
            validationList.add(CommonMessage.EMPTY_EMAIL_ADDRESS);
        if(CommonValidation.stringNullValidation(userDto.getUserContactNumber()))
            validationList.add(CommonMessage.EMPTY_USER_PHONE_NUMBER);

        return validationList;
    }



}
