package com.example.Ceylone.Snippers.Back.End.services.serviceImpl;

import com.example.Ceylone.Snippers.Back.End.constant.CommonMessage;
import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.dto.PakegesDto;
import com.example.Ceylone.Snippers.Back.End.dto.PhotographersProfilesDto;
import com.example.Ceylone.Snippers.Back.End.entity.Pakeges;
import com.example.Ceylone.Snippers.Back.End.entity.PhotographersProfiles;
import com.example.Ceylone.Snippers.Back.End.repository.PhotographerProfilesRepository;
import com.example.Ceylone.Snippers.Back.End.services.CatogoryService;
import com.example.Ceylone.Snippers.Back.End.services.PhotographerProfilesServices;
import com.example.Ceylone.Snippers.Back.End.services.PhotographerService;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import com.example.Ceylone.Snippers.Back.End.utill.CommonValidation;
import com.example.Ceylone.Snippers.Back.End.utill.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Service
public class PhotographerProfilesServiceImpl implements PhotographerProfilesServices {

    private final PhotographerProfilesRepository photographerProfilesRepository;

    private final PhotographerService photographerService;
    private final CatogoryService catogoryService;

    @Autowired
    public PhotographerProfilesServiceImpl(PhotographerProfilesRepository photographerProfilesRepository, PhotographerService photographerService, CatogoryService catogoryService) {
        this.photographerProfilesRepository = photographerProfilesRepository;
        this.photographerService = photographerService;
        this.catogoryService = catogoryService;
    }


    @Override
    public CommonResponse getAllUProfilesActives() {
        CommonResponse commonResponse = new CommonResponse();
        List<PhotographersProfilesDto> photographersProfilesDtooList= new ArrayList<>();
        try {
            Predicate<PhotographersProfiles> filterOnStatus= photographersProfilesEntity  -> photographersProfilesEntity.getCommonStatus()== CommonStatus.ACTIVE;
            photographersProfilesDtooList=photographerProfilesRepository.findAll().stream().filter(filterOnStatus).map(this::castPhotographerProfilesIntoPhotographerProfilesDto).collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(photographersProfilesDtooList));
        }catch (Exception e){
            LOGGER.error("/**************** Exception in PhotographerProfilesService -> getAll(Actives)"+e);
        }
        return commonResponse;
    }

    public PhotographersProfilesDto castPhotographerProfilesIntoPhotographerProfilesDto(PhotographersProfiles photographersProfiles){
        PhotographersProfilesDto photographersProfilesDto = new  PhotographersProfilesDto();

        photographersProfilesDto.setProfileID(String.valueOf(photographersProfiles.getProfileID()));
        photographersProfilesDto.setProfileName(photographersProfiles.getProfileName());
        photographersProfilesDto.setProfileNikeName(photographersProfiles.getNickName());
        photographersProfilesDto.setAbout(photographersProfiles.getAbout());
        photographersProfilesDto.setOfficialEmail(photographersProfiles.getOfficialEmail());
        photographersProfilesDto.setOfficialPhoneNo(photographersProfiles.getOfficialPhoneNo());
        photographersProfilesDto.setOfficialProvince(photographersProfiles.getOfficialprovience());
        photographersProfilesDto.setProfilePhotoImageUrl(photographersProfiles.getProfilePhotoImageUrl());
        photographersProfilesDto.setCoverPhotoImageUrl(photographersProfiles.getCoverPhotoImageUrl());
        photographersProfilesDto.setPhotographerDto(photographerService.castPhotographerIntoPhotographerDto(photographersProfiles.getPhotographer()));
        photographersProfilesDto.setCatogoryDto(catogoryService.castCatogoryIntoCtogoryDto(photographersProfiles.getCatogory()));

        return photographersProfilesDto;
    }

    @Override
    public CommonResponse saveProfile(PhotographersProfilesDto photographersProfilesDto) {
        CommonResponse commonResponse = new CommonResponse();
        PhotographersProfiles photographersProfiles;
        try {
            List<String> validationList = this.ProfileValidation(photographersProfilesDto);
            if (!validationList.isEmpty()) {
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }

            photographersProfiles = castProfilesDTOIntoProfiles(photographersProfilesDto);
            photographersProfiles = photographerProfilesRepository.save(photographersProfiles);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(photographersProfiles));
        } catch (Exception e) {
            LOGGER.error("/**************** Exception in ProfileService -> save()" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while saving the Profile."));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getAllProfilesInactive() {
        CommonResponse commonResponse = new CommonResponse();
        List<PhotographersProfilesDto> photographersProfilesDtooList= new ArrayList<>();
        try {
            Predicate<PhotographersProfiles> filterOnStatus= photographersProfilesEntity  -> photographersProfilesEntity.getCommonStatus()== CommonStatus.INACTIVE;
            photographersProfilesDtooList=photographerProfilesRepository.findAll().stream().filter(filterOnStatus).map(this::castPhotographerProfilesIntoPhotographerProfilesDto).collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(photographersProfilesDtooList));
        }catch (Exception e){
            LOGGER.error("/**************** Exception in PhotographerProfilesService -> getAll(Inactives)"+e);
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updateProfilesDeleteInactive(String profileID) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            PhotographersProfiles photographersProfiles= photographerProfilesRepository.findById(Long.valueOf(profileID)).get();
            if(photographersProfiles!=null){
                photographersProfiles.setCommonStatus(CommonStatus.INACTIVE);
                photographerProfilesRepository.save(photographersProfiles);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(photographersProfiles));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("PhotographerProfile not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PhotographerProfileService -> update(Inactive)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating Inactive"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updateProfilesActive(String profileID) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            PhotographersProfiles photographersProfiles= photographerProfilesRepository.findById(Long.valueOf(profileID)).get();
            if(photographersProfiles != null){
                photographersProfiles.setCommonStatus(CommonStatus.ACTIVE);
                photographerProfilesRepository.save(photographersProfiles);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(photographersProfiles));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("PhotographerProfile not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PhotographerProfileService -> update(Active)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating Active"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updateProfilesDelete(String profileID) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            PhotographersProfiles photographersProfiles= photographerProfilesRepository.findById(Long.valueOf(profileID)).get();
            if(photographersProfiles!=null){
                photographersProfiles.setCommonStatus(CommonStatus.DELETE);
                photographerProfilesRepository.save(photographersProfiles);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(photographersProfiles));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("PhotographerProfile not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PhotographerProfileService -> update(Delete)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating Delete"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getAllProfilesDelete() {
        CommonResponse commonResponse = new CommonResponse();
        List<PhotographersProfilesDto> photographersProfilesDtooList= new ArrayList<>();
        try {
            Predicate<PhotographersProfiles> filterOnStatus= photographersProfilesEntity  -> photographersProfilesEntity.getCommonStatus()== CommonStatus.DELETE;
            photographersProfilesDtooList=photographerProfilesRepository.findAll().stream().filter(filterOnStatus).map(this::castPhotographerProfilesIntoPhotographerProfilesDto).collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(photographersProfilesDtooList));
        }catch (Exception e){
            LOGGER.error("/**************** Exception in PhotographerProfilesService -> getAll(Actives)"+e);
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getProfileBycatagoryID(String catogoryID) {
        CommonResponse commonResponse = new CommonResponse();

        PhotographersProfilesDto photographersProfilesDto =new PhotographersProfilesDto();

        Predicate<PhotographersProfiles> filterOnStatus = photographersProfiles    -> photographersProfiles.getCommonStatus() != CommonStatus.DELETE;

        // Retrieve bookings for the photographer and filter them
        List<PhotographersProfiles> photographersProfilesList = photographerProfilesRepository.findByCatogoryCatogoryID(Long.valueOf(catogoryID));
        List<PhotographersProfilesDto> photographersProfilesDtos = photographersProfilesList.stream().filter(filterOnStatus).map(this::castPhotographerProfilesIntoPhotographerProfilesDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(photographersProfilesDtos));
        System.out.println(photographersProfilesDtos);
        return commonResponse;
    }


    private PhotographersProfiles castProfilesDTOIntoProfiles(PhotographersProfilesDto photographersProfilesDto) throws IOException, SQLException {
        PhotographersProfiles photographersProfiles = new PhotographersProfiles();

        photographersProfiles.setProfileID(Long.valueOf(photographersProfilesDto.getProfileID()));
        photographersProfiles.setProfileName(photographersProfilesDto.getProfileName());
        photographersProfiles.setNickName(photographersProfilesDto.getProfileNikeName());
        photographersProfiles.setAbout(photographersProfilesDto.getAbout());
        photographersProfiles.setOfficialEmail(photographersProfilesDto.getOfficialEmail());
        photographersProfiles.setOfficialPhoneNo(photographersProfilesDto.getOfficialPhoneNo());
        photographersProfiles.setOfficialprovience(photographersProfilesDto.getOfficialProvince());
        photographersProfiles.setProfilePhotoImageUrl(photographersProfilesDto.getProfilePhotoImageUrl());
        photographersProfiles.setCoverPhotoImageUrl(photographersProfilesDto.getCoverPhotoImageUrl());
        photographersProfiles.setCommonStatus(photographersProfilesDto.getCommonStatus());
        photographersProfiles.setPhotographer(photographerService.findByPhotgrapherID(photographersProfilesDto.getPhotographerDto().getPhotographerID()));
        photographersProfiles.setCatogory(catogoryService.findByID(photographersProfilesDto.getCatogoryDto().getCatogoryID()));


        return photographersProfiles;
    }

    @Override
    public CommonResponse updateProfiles(PhotographersProfilesDto photographersProfilesDto) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<String> validationList = this.ProfileValidation(photographersProfilesDto);
            if (!validationList.isEmpty()) {
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }

            PhotographersProfiles photographersProfiles= photographerProfilesRepository.findById(Long.valueOf(photographersProfilesDto.getProfileID())).get();
            if(photographersProfiles!=null){
                photographersProfiles.setProfileName(photographersProfilesDto.getProfileName());
                photographersProfiles.setNickName(photographersProfilesDto.getProfileNikeName());
                photographersProfiles.setAbout(photographersProfilesDto.getAbout());
                photographersProfiles.setOfficialEmail(photographersProfilesDto.getOfficialEmail());
                photographersProfiles.setOfficialPhoneNo(photographersProfilesDto.getOfficialPhoneNo());
                photographersProfiles.setOfficialprovience(photographersProfilesDto.getOfficialProvince());
                photographersProfiles.setCoverPhotoImageUrl(photographersProfilesDto.getCoverPhotoImageUrl());
                photographersProfiles.setCommonStatus(photographersProfilesDto.getCommonStatus());
                photographersProfiles.setCommonStatus(photographersProfilesDto.getCommonStatus());
                photographerProfilesRepository.save(photographersProfiles);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(photographersProfiles));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("Profile not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PhotographerProfilesService -> update()" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating Profile"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getProfileByID(String profileID) {
        CommonResponse commonResponse=new CommonResponse();
        PhotographersProfiles photographersProfiles;
        try {
            photographersProfiles =photographerProfilesRepository.findById(Long.valueOf(profileID)).get();
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(photographersProfiles));
        }
        catch (Exception e){
            LOGGER.error("/**************** Exception in PhotographerProfilesService -> getProfileById()"+e);
        }
        return commonResponse;

    }

    @Override
    public PhotographersProfiles findByPhotographerProfileID(String profileID) {
      return   photographerProfilesRepository.findById(Long.valueOf(profileID)).get();
    }


    private List<String> ProfileValidation(PhotographersProfilesDto photographersProfilesDto) {
        List<String> validationList = new ArrayList<>();
        if (CommonValidation.stringNullValidation(photographersProfilesDto.getProfileID()))
            validationList.add(CommonMessage.EMPTY_PROFILE_NAME);
        if(CommonValidation.stringNullValidation(photographersProfilesDto.getProfileNikeName()))
            validationList.add(CommonMessage.EMPTY_PROFILE_NICKNAME);
        if(CommonValidation.stringNullValidation(photographersProfilesDto.getAbout()))
            validationList.add(CommonMessage.EMPTY_ABOUT);
        if(CommonValidation.stringNullValidation(photographersProfilesDto.getOfficialEmail()))
            validationList.add(CommonMessage.EMPTY_EMAIL_ADDRESS);
        if(CommonValidation.stringNullValidation(photographersProfilesDto.getOfficialPhoneNo()))
            validationList.add(CommonMessage.EMPTY_CONTACT_NUMBER);
        if(CommonValidation.stringNullValidation(photographersProfilesDto.getOfficialProvince()))
            validationList.add(CommonMessage.EMPTY_PROVIENCE);
        return validationList;
    }







}

