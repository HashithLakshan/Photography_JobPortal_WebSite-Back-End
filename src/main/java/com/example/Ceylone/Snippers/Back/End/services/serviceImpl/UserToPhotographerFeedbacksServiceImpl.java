package com.example.Ceylone.Snippers.Back.End.services.serviceImpl;

import com.example.Ceylone.Snippers.Back.End.constant.CommonMessage;
import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.dto.BookingsDto;
import com.example.Ceylone.Snippers.Back.End.dto.PhotographerDto;
import com.example.Ceylone.Snippers.Back.End.dto.UserDto;
import com.example.Ceylone.Snippers.Back.End.dto.UserToPhotographerFeedbacksDto;
import com.example.Ceylone.Snippers.Back.End.entity.Bookings;
import com.example.Ceylone.Snippers.Back.End.entity.UserToPhotographerFeedbacks;
import com.example.Ceylone.Snippers.Back.End.repository.UserToPhotographerFeedbacksRepository;
import com.example.Ceylone.Snippers.Back.End.services.PhotographerService;
import com.example.Ceylone.Snippers.Back.End.services.UserService;
import com.example.Ceylone.Snippers.Back.End.services.UserToPhotographerFeedbacksService;
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
public class UserToPhotographerFeedbacksServiceImpl implements UserToPhotographerFeedbacksService {

    private final UserToPhotographerFeedbacksRepository userToPhotographerFeedbacksRepository;

    private final PhotographerService photographerService;

    private final UserService userService;

    @Autowired
    public UserToPhotographerFeedbacksServiceImpl(UserToPhotographerFeedbacksRepository userToPhotographerFeedbacksRepository, PhotographerService photographerService, UserService userService) {
        this.userToPhotographerFeedbacksRepository = userToPhotographerFeedbacksRepository;
        this.photographerService = photographerService;
        this.userService = userService;
    }


    @Override
    public CommonResponse getAllPhotographerFeedbacksActive(String photographerID) {
        CommonResponse commonResponse = new CommonResponse();


        PhotographerDto photographerDto = new PhotographerDto();
        UserToPhotographerFeedbacksDto userToPhotographerFeedbacksDto = new UserToPhotographerFeedbacksDto();

        Predicate<UserToPhotographerFeedbacks> filterOnStatus = userToPhotographerFeedbacks -> userToPhotographerFeedbacks.getCommonStatus() == CommonStatus.ACTIVE;

        // Retrieve bookings for the photographer and filter them
        List<UserToPhotographerFeedbacks> userToPhotographerFeedbacksList = userToPhotographerFeedbacksRepository.findByPhotographer_PhotographerID(Long.valueOf(photographerID));
        List<UserToPhotographerFeedbacksDto> userToPhotographerFeedbacksDtos = userToPhotographerFeedbacksList.stream().filter(filterOnStatus).map(this::castUserToPhotographerFeedbacksIntoUserToPhotographerFeedbacksDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(userToPhotographerFeedbacksDtos));
        return commonResponse;
    }

    @Override
    public CommonResponse saveFeedback(UserToPhotographerFeedbacksDto userToPhotographerFeedbacksDto) {
        CommonResponse commonResponse = new CommonResponse();

        try {
            List<String> validationList = this.FeedbacksValidation(userToPhotographerFeedbacksDto);
            if (!validationList.isEmpty()) {
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }
            UserToPhotographerFeedbacks userToPhotographerFeedbacks = castUserToPhotographerFeedbacksDTOIntoUserToPhotographerFeedbacks(userToPhotographerFeedbacksDto);
            userToPhotographerFeedbacksRepository.save(userToPhotographerFeedbacks);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(userToPhotographerFeedbacks));


        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserService -> save()" + e);
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getAllPhotographerFeedbacksInactive(String photographerID) {
CommonResponse commonResponse =new CommonResponse();

        PhotographerDto photographerDto = new PhotographerDto();
        UserToPhotographerFeedbacksDto userToPhotographerFeedbacksDto = new UserToPhotographerFeedbacksDto();

        Predicate<UserToPhotographerFeedbacks> filterOnStatus = userToPhotographerFeedbacks -> userToPhotographerFeedbacks.getCommonStatus() == CommonStatus.INACTIVE;

        // Retrieve bookings for the photographer and filter them
        List<UserToPhotographerFeedbacks> userToPhotographerFeedbacksList = userToPhotographerFeedbacksRepository.findByPhotographer_PhotographerID(Long.valueOf(photographerID));
        List<UserToPhotographerFeedbacksDto> userToPhotographerFeedbacksDtos = userToPhotographerFeedbacksList.stream().filter(filterOnStatus).map(this::castUserToPhotographerFeedbacksIntoUserToPhotographerFeedbacksDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(userToPhotographerFeedbacksDtos));
        return commonResponse;
    }

    @Override
    public CommonResponse updateInactive(String uToPFeedbackID) {
        CommonResponse commonResponse = new CommonResponse();
        UserToPhotographerFeedbacks userToPhotographerFeedbacks;
        try {
            userToPhotographerFeedbacks= userToPhotographerFeedbacksRepository.findById(Long.valueOf(uToPFeedbackID)).get();
            if (userToPhotographerFeedbacks != null) {
                userToPhotographerFeedbacks.setCommonStatus(CommonStatus.INACTIVE);
                userToPhotographerFeedbacksRepository.save(userToPhotographerFeedbacks);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(userToPhotographerFeedbacks));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("Feedback not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserToPhotogapherService -> update(Inactive)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating feedbacks"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updateActive(String uToPFeedbackID) {
        CommonResponse commonResponse = new CommonResponse();
        UserToPhotographerFeedbacks userToPhotographerFeedbacks;
        try {
            userToPhotographerFeedbacks= userToPhotographerFeedbacksRepository.findById(Long.valueOf(uToPFeedbackID)).get();
            if (userToPhotographerFeedbacks != null) {
                userToPhotographerFeedbacks.setCommonStatus(CommonStatus.ACTIVE);
                userToPhotographerFeedbacksRepository.save(userToPhotographerFeedbacks);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(userToPhotographerFeedbacks));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("Feedback not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserToPhotogapherService -> update(active)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating feedbacks"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updateDelete(String uToPFeedbackID) {
        CommonResponse commonResponse = new CommonResponse();
        UserToPhotographerFeedbacks userToPhotographerFeedbacks;
        try {
            userToPhotographerFeedbacks= userToPhotographerFeedbacksRepository.findById(Long.valueOf(uToPFeedbackID)).get();
            if (userToPhotographerFeedbacks != null) {
                userToPhotographerFeedbacks.setCommonStatus(CommonStatus.DELETE);
                userToPhotographerFeedbacksRepository.save(userToPhotographerFeedbacks);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(userToPhotographerFeedbacks));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("Feedback not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserToPhotogapherService -> update(Delete)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating feedbacks"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updateYes(String uToPFeedbackID) {
        CommonResponse commonResponse = new CommonResponse();
        UserToPhotographerFeedbacks userToPhotographerFeedbacks;
        try {
            userToPhotographerFeedbacks= userToPhotographerFeedbacksRepository.findById(Long.valueOf(uToPFeedbackID)).get();
            if (userToPhotographerFeedbacks != null) {
                userToPhotographerFeedbacks.setReplingUserToPhotographer("Yes");
                userToPhotographerFeedbacksRepository.save(userToPhotographerFeedbacks);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(userToPhotographerFeedbacks));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("Feedback not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserToPhotogapherService -> update(Yes)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating feedbacks"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getFeedabacksSearchName(String userName, String photographerID) {
        CommonResponse commonResponse = new CommonResponse();


        UserToPhotographerFeedbacksDto  userToPhotographerFeedbacksDto =new UserToPhotographerFeedbacksDto();

        Predicate<UserToPhotographerFeedbacks> filterOnStatus = userToPhotographerFeedbacks   -> userToPhotographerFeedbacks.getCommonStatus() != CommonStatus.DELETE;

        // Retrieve bookings for the photographer and filter them
        List<UserToPhotographerFeedbacks> userToPhotographerFeedbacksList = userToPhotographerFeedbacksRepository.findByUserUserNameAndPhotographerPhotographerID(userName, Long.valueOf(photographerID));
        List<UserToPhotographerFeedbacksDto> userToPhotographerFeedbacksDtos = userToPhotographerFeedbacksList.stream().filter(filterOnStatus).map(this::castUserToPhotographerFeedbacksIntoUserToPhotographerFeedbacksDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(userToPhotographerFeedbacksDtos));
        return commonResponse;
    }

    @Override
    public CommonResponse getFeedabacksSearchID(String userID, String photographerID) {

        CommonResponse commonResponse = new CommonResponse();


        UserToPhotographerFeedbacksDto  userToPhotographerFeedbacksDto =new UserToPhotographerFeedbacksDto();

        Predicate<UserToPhotographerFeedbacks> filterOnStatus = userToPhotographerFeedbacks   -> userToPhotographerFeedbacks.getCommonStatus() != CommonStatus.DELETE;

        // Retrieve bookings for the photographer and filter them
        List<UserToPhotographerFeedbacks> userToPhotographerFeedbacksList = userToPhotographerFeedbacksRepository.findByUserUserIDAndPhotographerPhotographerID(Long.valueOf(userID), Long.valueOf(photographerID));
        List<UserToPhotographerFeedbacksDto> userToPhotographerFeedbacksDtos = userToPhotographerFeedbacksList.stream().filter(filterOnStatus).map(this::castUserToPhotographerFeedbacksIntoUserToPhotographerFeedbacksDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(userToPhotographerFeedbacksDtos));
        return commonResponse;
    }

    private List<String> FeedbacksValidation(UserToPhotographerFeedbacksDto userToPhotographerFeedbacksDto) {

        List<String> validationList = new ArrayList<>();
        if (CommonValidation.stringNullValidation(userToPhotographerFeedbacksDto.getUToPSubject()))
            validationList.add(CommonMessage.SELECT_YOUR_SHOOT_TYPE);
        return validationList;
    }

    private UserToPhotographerFeedbacks castUserToPhotographerFeedbacksDTOIntoUserToPhotographerFeedbacks(UserToPhotographerFeedbacksDto userToPhotographerFeedbacksDto) {
       UserToPhotographerFeedbacks userToPhotographerFeedbacks = new UserToPhotographerFeedbacks();
       userToPhotographerFeedbacks.setUToPSubject(userToPhotographerFeedbacksDto.getUToPSubject());
       userToPhotographerFeedbacks.setUToPDiscription(userToPhotographerFeedbacksDto.getUToPDiscription());
       userToPhotographerFeedbacks.setCommonStatus(userToPhotographerFeedbacksDto.getCommonStatus());
       userToPhotographerFeedbacks.setReplingUserToPhotographer("No");
        userToPhotographerFeedbacks.setUser(userService.findByUserID(userToPhotographerFeedbacksDto.getUserDto().getUserID()));
        userToPhotographerFeedbacks.setPhotographer(photographerService.findByPhotgrapherID(userToPhotographerFeedbacksDto.getPhotographerDto().getPhotographerID()));
        return userToPhotographerFeedbacks;
    }

    public UserToPhotographerFeedbacksDto castUserToPhotographerFeedbacksIntoUserToPhotographerFeedbacksDto(UserToPhotographerFeedbacks userToPhotographerFeedbacks) {
        UserToPhotographerFeedbacksDto userToPhotographerFeedbacksDto = new UserToPhotographerFeedbacksDto();
userToPhotographerFeedbacksDto.setUToPFeedbackID(String.valueOf(userToPhotographerFeedbacks.getUToP_FeedbackID()));
userToPhotographerFeedbacksDto.setUToPSubject(userToPhotographerFeedbacks.getUToPSubject());
userToPhotographerFeedbacksDto.setUToPDiscription(userToPhotographerFeedbacks.getUToPDiscription());
userToPhotographerFeedbacksDto.setCommonStatus(userToPhotographerFeedbacks.getCommonStatus());
userToPhotographerFeedbacksDto.setReplingUserToPhotographer(userToPhotographerFeedbacks.getReplingUserToPhotographer());
        userToPhotographerFeedbacksDto.setUserDto(userService.findById(userToPhotographerFeedbacks.getUser().getUserID()));
        userToPhotographerFeedbacksDto.setPhotographerDto(photographerService.findByID(String.valueOf(userToPhotographerFeedbacks.getPhotographer().getPhotographerID())));

        return userToPhotographerFeedbacksDto;
    }
}