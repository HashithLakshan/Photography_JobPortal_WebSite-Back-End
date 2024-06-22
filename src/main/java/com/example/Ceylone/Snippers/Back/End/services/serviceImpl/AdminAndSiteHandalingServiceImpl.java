package com.example.Ceylone.Snippers.Back.End.services.serviceImpl;

import com.example.Ceylone.Snippers.Back.End.constant.CommonMessage;
import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.dto.AdminAndSiteHandalingDto;
import com.example.Ceylone.Snippers.Back.End.dto.UserDto;
import com.example.Ceylone.Snippers.Back.End.entity.AdminAndSiteHandaling;
import com.example.Ceylone.Snippers.Back.End.entity.Bookings;
import com.example.Ceylone.Snippers.Back.End.entity.User;
import com.example.Ceylone.Snippers.Back.End.repository.AdminAndSiteHandalingRepository;
import com.example.Ceylone.Snippers.Back.End.services.AdminAndSiteHandalingService;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import com.example.Ceylone.Snippers.Back.End.utill.CommonValidation;
import com.example.Ceylone.Snippers.Back.End.utill.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Service
public class AdminAndSiteHandalingServiceImpl implements AdminAndSiteHandalingService {

    private final AdminAndSiteHandalingRepository adminAndSiteHandalingRepository;

    @Autowired
    public AdminAndSiteHandalingServiceImpl(AdminAndSiteHandalingRepository adminAndSiteHandalingRepository) {
        this.adminAndSiteHandalingRepository = adminAndSiteHandalingRepository;
    }

    @Override
    public CommonResponse saveAdminHandaling(AdminAndSiteHandalingDto adminAndSiteHandalingDto) {
        CommonResponse commonResponse = new CommonResponse();
        AdminAndSiteHandaling adminAndSiteHandaling;
        try {
            List<String> validationList = this.adminSiteHandalingValidation(adminAndSiteHandalingDto);
            if (!validationList.isEmpty()) {
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }

            adminAndSiteHandaling = castAdminSiteHandalingDTOIntoAdminSiteHandaling(adminAndSiteHandalingDto);
            adminAndSiteHandaling = adminAndSiteHandalingRepository.save(adminAndSiteHandaling);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(adminAndSiteHandaling));
        } catch (Exception e) {
            LOGGER.error("/**************** Exception in AdminSiteHandlingService -> save()" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while saving the Admin things."));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getAllSiteDetail() {
        CommonResponse commonResponse = new CommonResponse();
        List<AdminAndSiteHandalingDto> adminAndSiteHandalingDtos = new ArrayList<>();
        try {
            Predicate<AdminAndSiteHandaling> filterOnStatus = adminAndSiteHandaling -> adminAndSiteHandaling.getCommonStatus() != CommonStatus.DELETE;
            adminAndSiteHandalingDtos = adminAndSiteHandalingRepository.findAll().stream().filter(filterOnStatus).map(this::castAdminIntoAdminDto).collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(adminAndSiteHandalingDtos));
        } catch (Exception e) {
            LOGGER.error("/**************** Exception in AdminService -> getAllSite details()" + e);
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updateSiteDetails(AdminAndSiteHandalingDto adminAndSiteHandalingDto) {
        CommonResponse commonResponse = new CommonResponse();

        try {
            List<String> validationList = this.adminSiteHandalingValidation(adminAndSiteHandalingDto);
            if (!validationList.isEmpty()) {
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }
            AdminAndSiteHandaling adminAndSiteHandaling = adminAndSiteHandalingRepository.findById(Long.valueOf(adminAndSiteHandalingDto.getAdminID())).get();
            if (adminAndSiteHandaling != null) {
                adminAndSiteHandaling.setAdminUserName(adminAndSiteHandalingDto.getAdminUserName());
                adminAndSiteHandaling.setPassword(adminAndSiteHandalingDto.getPassword());
                adminAndSiteHandaling.setJoinWithUsDiscription(adminAndSiteHandalingDto.getJoinWithUsDiscription());
                adminAndSiteHandaling.setHomeCoverPhotoUrl(adminAndSiteHandalingDto.getHomeCoverPhotoUrl());
                adminAndSiteHandaling.setHomeContactUsPhotoUrl(adminAndSiteHandalingDto.getHomeContactUsPhotoUrl());
                adminAndSiteHandaling.setChoose_Snipper_Cover_PhotoUrl(adminAndSiteHandalingDto.getChoose_Snipper_Cover_PhotoUrl());
                adminAndSiteHandaling.setUserLoginPhotoUrl(adminAndSiteHandalingDto.getUserLoginPhotoUrl());
                adminAndSiteHandaling.setPhotographer_Login_PhotoUrl(adminAndSiteHandalingDto.getPhotographer_Login_PhotoUrl());
                adminAndSiteHandaling.setAdminLoginPhotoUrl(adminAndSiteHandalingDto.getAdminLoginPhotoUrl());


                adminAndSiteHandalingRepository.save(adminAndSiteHandaling);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(adminAndSiteHandaling));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("Admin not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in AdminSiteHandalingService -> update()" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating Site"));
        }
        return commonResponse;
    }

    public AdminAndSiteHandalingDto castAdminIntoAdminDto(AdminAndSiteHandaling adminAndSiteHandaling) {
        AdminAndSiteHandalingDto andSiteHandalingDto = new AdminAndSiteHandalingDto();


        andSiteHandalingDto.setAdminUserName(adminAndSiteHandaling.getAdminUserName());
        andSiteHandalingDto.setPassword(adminAndSiteHandaling.getPassword());
        andSiteHandalingDto.setJoinWithUsDiscription(adminAndSiteHandaling.getJoinWithUsDiscription());
        andSiteHandalingDto.setHomeCoverPhotoUrl(adminAndSiteHandaling.getHomeCoverPhotoUrl());
        andSiteHandalingDto.setHomeContactUsPhotoUrl(adminAndSiteHandaling.getHomeContactUsPhotoUrl());
        andSiteHandalingDto.setChoose_Snipper_Cover_PhotoUrl(adminAndSiteHandaling.getChoose_Snipper_Cover_PhotoUrl());
        andSiteHandalingDto.setUserLoginPhotoUrl(adminAndSiteHandaling.getUserLoginPhotoUrl());
        andSiteHandalingDto.setPhotographer_Login_PhotoUrl(adminAndSiteHandaling.getPhotographer_Login_PhotoUrl());
        andSiteHandalingDto.setAdminLoginPhotoUrl(adminAndSiteHandaling.getAdminLoginPhotoUrl());


        return andSiteHandalingDto;

    }


    private AdminAndSiteHandaling castAdminSiteHandalingDTOIntoAdminSiteHandaling(AdminAndSiteHandalingDto adminAndSiteHandalingDto) throws IOException {
        AdminAndSiteHandaling adminAndSiteHandaling = new AdminAndSiteHandaling();
        adminAndSiteHandaling.setAdminID(Long.valueOf(adminAndSiteHandalingDto.getAdminID()));
        adminAndSiteHandaling.setAdminUserName(adminAndSiteHandalingDto.getAdminUserName());
        adminAndSiteHandaling.setPassword(adminAndSiteHandalingDto.getPassword());
        adminAndSiteHandaling.setJoinWithUsDiscription(adminAndSiteHandalingDto.getJoinWithUsDiscription());
        adminAndSiteHandaling.setHomeCoverPhotoUrl(adminAndSiteHandalingDto.getHomeCoverPhotoUrl());
        adminAndSiteHandaling.setHomeContactUsPhotoUrl(adminAndSiteHandalingDto.getHomeContactUsPhotoUrl());
        adminAndSiteHandaling.setChoose_Snipper_Cover_PhotoUrl(adminAndSiteHandalingDto.getChoose_Snipper_Cover_PhotoUrl());
        adminAndSiteHandaling.setUserLoginPhotoUrl(adminAndSiteHandalingDto.getUserLoginPhotoUrl());
        adminAndSiteHandaling.setPhotographer_Login_PhotoUrl(adminAndSiteHandalingDto.getPhotographer_Login_PhotoUrl());
        adminAndSiteHandaling.setAdminLoginPhotoUrl(adminAndSiteHandalingDto.getAdminLoginPhotoUrl());
        return adminAndSiteHandaling;
    }

    private List<String> adminSiteHandalingValidation(AdminAndSiteHandalingDto adminAndSiteHandalingDto) {

        List<String> validationList = new ArrayList<>();
        if (CommonValidation.stringNullValidation(adminAndSiteHandalingDto.getAdminID()))
            validationList.add(CommonMessage.SELECT_YOUR_SHOOT_TYPE);

        return validationList;
    }
}
