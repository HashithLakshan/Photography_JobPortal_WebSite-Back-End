package com.example.Ceylone.Snippers.Back.End.services.serviceImpl;

import com.example.Ceylone.Snippers.Back.End.constant.CommonMessage;
import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.dto.PhotographerDto;
import com.example.Ceylone.Snippers.Back.End.dto.PhotographersFeedbacksDto;
import com.example.Ceylone.Snippers.Back.End.entity.PhotographersFeedbacks;
import com.example.Ceylone.Snippers.Back.End.repository.PhotographersFeedbacksRepository;
import com.example.Ceylone.Snippers.Back.End.services.PhotographerService;
import com.example.Ceylone.Snippers.Back.End.services.PhotographersFeedbacksService;
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
public class PhotographersFeedbacksServiceImpl implements PhotographersFeedbacksService {

    private final PhotographersFeedbacksRepository photographersFeedbacksRepository;

    private final PhotographerService photographerService;

    @Autowired
    public PhotographersFeedbacksServiceImpl(PhotographersFeedbacksRepository photographersFeedbacksRepository, PhotographerService photographerService) {
        this.photographersFeedbacksRepository = photographersFeedbacksRepository;
        this.photographerService = photographerService;

    }


    @Override
    public CommonResponse getAllFeedbackActives() {
        CommonResponse commonResponse = new CommonResponse();
        List<PhotographersFeedbacksDto> photographersFeedbacksList = new ArrayList<>();
        try {
            Predicate<PhotographersFeedbacks> filterOnStatus = photographersFeedbacks -> photographersFeedbacks.getCommonStatus() == CommonStatus.ACTIVE;
            photographersFeedbacksList= photographersFeedbacksRepository.findAll().stream().filter(filterOnStatus).map(this::castPhotographersFeedbacksIntoPhotographersFeedbacksDto).collect(Collectors.toList());

            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(photographersFeedbacksList));
        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PhotographerFeedacksService -> getAll(ActiveFeedbacksPhoto)" + e);
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getAllFeedbacksInactive() {
        CommonResponse commonResponse = new CommonResponse();
        List<PhotographersFeedbacksDto> photographersFeedbacksList = new ArrayList<>();
        try {
            Predicate<PhotographersFeedbacks> filterOnStatus = photographersFeedbacks -> photographersFeedbacks.getCommonStatus() == CommonStatus.INACTIVE;
            photographersFeedbacksList= photographersFeedbacksRepository.findAll().stream().filter(filterOnStatus).map(this::castPhotographersFeedbacksIntoPhotographersFeedbacksDto).collect(Collectors.toList());

            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(photographersFeedbacksList));
        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PhotographerFeedbacksService -> getAll(InactivePhotographerFeedbacks)" + e);
        }
        return commonResponse;
    }

    @Override
    public CommonResponse saveFeedbacksPhotographers(PhotographersFeedbacksDto photographersFeedbacksDto) {
        CommonResponse commonResponse = new CommonResponse();

        try {
            List<String> validationList = this.PhotographerFeedbacksValidation(photographersFeedbacksDto);
            if (!validationList.isEmpty()) {
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }
            PhotographersFeedbacks photographersFeedbacks = castPhtographerFeedbacksDTOIntoPhotographersFeedbacks(photographersFeedbacksDto);
            photographersFeedbacksRepository.save(photographersFeedbacks);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(photographersFeedbacks));


        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PhotographerFeedbacksService -> save(Feedbacks)" + e);
        }
        return commonResponse;


    }

    @Override
    public CommonResponse updatePhotographerFeedbacksReply_YES(String photographerFeedbackID) {

            CommonResponse commonResponse = new CommonResponse();
            try {

                PhotographersFeedbacks photographersFeedbacks= photographersFeedbacksRepository.findById(Long.valueOf(photographerFeedbackID)).get();
                if(photographersFeedbacks!=null){
                    photographersFeedbacks.setReplingThisPhotographer("Yes");
                    photographersFeedbacksRepository.save(photographersFeedbacks);
                    commonResponse.setStatus(true);
                    commonResponse.setPayload(Collections.singletonList(photographersFeedbacks));
                } else {
                    commonResponse.setErrorMessages(Collections.singletonList("This PhotographerFeedBack not found"));
                }

            } catch (Exception e) {
                LOGGER.error("/**************** Exception in UserService -> update(Inactive)" + e);
                commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating userActive"));
            }
            return commonResponse;
    }

    @Override
    public CommonResponse updatePhotographerFeedbacksInactive(String photographerFeedbackID) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            PhotographersFeedbacks photographersFeedbacks= photographersFeedbacksRepository.findById(Long.valueOf(photographerFeedbackID)).get();
            if(photographersFeedbacks!=null){
                photographersFeedbacks.setCommonStatus(CommonStatus.INACTIVE);
                photographersFeedbacksRepository.save(photographersFeedbacks);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(photographersFeedbacks));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("This PhotographerFeedBack not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserService -> update(Inactive)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating PhotographerFeedbacksInactives"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updatePhotographerFeedbacksDelete(String photographerFeedbackID) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            PhotographersFeedbacks photographersFeedbacks= photographersFeedbacksRepository.findById(Long.valueOf(photographerFeedbackID)).get();
            if(photographersFeedbacks!=null){
                photographersFeedbacks.setCommonStatus(CommonStatus.DELETE);
                photographersFeedbacksRepository.save(photographersFeedbacks);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(photographersFeedbacks));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("This PhotographerFeedBack not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserService -> update(Delete)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating PhotographerFeedbacksDelete"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updatePhotographerFeedbacksActive(String photographerFeedbackID) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            PhotographersFeedbacks photographersFeedbacks= photographersFeedbacksRepository.findById(Long.valueOf(photographerFeedbackID)).get();
            if(photographersFeedbacks!=null){
                photographersFeedbacks.setCommonStatus(CommonStatus.ACTIVE);
                photographersFeedbacksRepository.save(photographersFeedbacks);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(photographersFeedbacks));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("This PhotographerFeedBack not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserService -> update(Delete)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating PhotographerFeedbacksDelete"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getPhotographerFeedbacksDetails(String photographerID) {
        CommonResponse commonResponse = new CommonResponse();


        PhotographerDto photographerDto = new PhotographerDto();
        PhotographersFeedbacksDto  photographersFeedbacksDto =new PhotographersFeedbacksDto();

        Predicate<PhotographersFeedbacks> filterOnStatus = photographersFeedbacks   -> photographersFeedbacks.getCommonStatus() != CommonStatus.DELETE;

        // Retrieve bookings for the photographer and filter them
        List<PhotographersFeedbacks> photographersFeedbacksList = photographersFeedbacksRepository.findByPhotographer_PhotographerID(Long.valueOf(photographerID));
        List<PhotographersFeedbacksDto> photographersFeedbacksDtos = photographersFeedbacksList.stream().filter(filterOnStatus).map(this::castPhotographersFeedbacksIntoPhotographersFeedbacksDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(photographersFeedbacksDtos));
        return commonResponse;
    }

    @Override
    public CommonResponse getPhotographerFeedbacksDetails1( String photographerUserName) {
        CommonResponse commonResponse = new CommonResponse();


        PhotographerDto photographerDto = new PhotographerDto();
        PhotographersFeedbacksDto  photographersFeedbacksDto =new PhotographersFeedbacksDto();

        Predicate<PhotographersFeedbacks> filterOnStatus = photographersFeedbacks   -> photographersFeedbacks.getCommonStatus() != CommonStatus.DELETE;

        // Retrieve bookings for the photographer and filter them
        List<PhotographersFeedbacks> photographersFeedbacksList = photographersFeedbacksRepository.findByPhotographerPhotographerUserName(photographerUserName);
        List<PhotographersFeedbacksDto> photographersFeedbacksDtos = photographersFeedbacksList.stream().filter(filterOnStatus).map(this::castPhotographersFeedbacksIntoPhotographersFeedbacksDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(photographersFeedbacksDtos));
        System.out.println(photographersFeedbacksDto);
        return commonResponse;
    }





    private List<String> PhotographerFeedbacksValidation(PhotographersFeedbacksDto photographersFeedbacksDto) {

        List<String> validationList = new ArrayList<>();
        if (CommonValidation.stringNullValidation(photographersFeedbacksDto.getPhotographerSubject()))
            validationList.add(CommonMessage.SELECT_YOUR_SHOOT_TYPE);

        return validationList;
    }

    private PhotographersFeedbacks castPhtographerFeedbacksDTOIntoPhotographersFeedbacks(PhotographersFeedbacksDto photographersFeedbacksDto) {
        PhotographersFeedbacks photographersFeedbacks = new PhotographersFeedbacks();
       photographersFeedbacks.setPhotographerFeedbackID(Long.valueOf(photographersFeedbacksDto.getPhotographerFeedbackID()));
       photographersFeedbacks.setPhotographerSubject(photographersFeedbacksDto.getPhotographerSubject());
       photographersFeedbacks.setPhotographerDiscription(photographersFeedbacksDto.getPhotographerDiscription());
       photographersFeedbacks.setReplingThisPhotographer("No");
       photographersFeedbacks.setCommonStatus(photographersFeedbacksDto.getCommonStatus());
        photographersFeedbacks.setPhotographer(photographerService.findByPhotgrapherID(photographersFeedbacksDto.getPhotographerDto().getPhotographerID()));

        return photographersFeedbacks;
    }

    private PhotographersFeedbacksDto castPhotographersFeedbacksIntoPhotographersFeedbacksDto(PhotographersFeedbacks photographersFeedbacks) {
        PhotographersFeedbacksDto photographersFeedbacksDto = new PhotographersFeedbacksDto();
        photographersFeedbacksDto.setPhotographerFeedbackID(String.valueOf(photographersFeedbacks.getPhotographerFeedbackID()));
        photographersFeedbacksDto.setPhotographerSubject(photographersFeedbacks.getPhotographerSubject());
        photographersFeedbacksDto.setPhotographerDiscription(photographersFeedbacks.getPhotographerDiscription());
        photographersFeedbacksDto.setCommonStatus(photographersFeedbacks.getCommonStatus());
        photographersFeedbacksDto.setPhotographerDto(photographerService.findByID(String.valueOf(photographersFeedbacks.getPhotographer().getPhotographerID())));
        return photographersFeedbacksDto;
    }



}