package com.example.Ceylone.Snippers.Back.End.services.serviceImpl;

import com.example.Ceylone.Snippers.Back.End.constant.CommonMessage;
import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.dto.SiteRollDto;
import com.example.Ceylone.Snippers.Back.End.entity.SiteRoll;
import com.example.Ceylone.Snippers.Back.End.repository.SiteRollsRepository;
import com.example.Ceylone.Snippers.Back.End.repository.UserRepository;
import com.example.Ceylone.Snippers.Back.End.services.SiteRollsService;
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
public class SiteRollsServiceImpl implements SiteRollsService {
    final private SiteRollsRepository siteRollsRepository;
@Autowired
    public SiteRollsServiceImpl( SiteRollsRepository siteRollsRepository) {
        this.siteRollsRepository = siteRollsRepository;
    }




    @Override
    public CommonResponse getAllSiteRolls() {
        CommonResponse commonResponse=new CommonResponse();
        List<SiteRollDto> siteRollDtoList= new ArrayList<>();
        try {
            Predicate<SiteRoll> filterOnStatus= siteRollEntity -> siteRollEntity.getCommonStatus()!= CommonStatus.DELETE;
            siteRollDtoList=siteRollsRepository.findAll().stream().filter(filterOnStatus).map(this::castSiteRollsIntoSiteRollsDto).collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(siteRollDtoList));
        }catch (Exception e){
            LOGGER.error("/**************** Exception in UserService -> getAll()"+e);
        }
        return commonResponse;
    }
    public SiteRollDto castSiteRollsIntoSiteRollsDto(SiteRoll siteRoll) {
        SiteRollDto siteRollDto=new SiteRollDto();
        siteRollDto.setRollID(String.valueOf(siteRoll.getRollID()));
        siteRollDto.setRollName(siteRoll.getRollName());
        siteRollDto.setCommonStatus(siteRoll.getCommonStatus());
        return siteRollDto;

    }

    @Override
    public SiteRoll findBySiteRollID(String rollID) {
     return   siteRollsRepository.findByRollID(Long.valueOf(rollID));

    }

    @Override
    public SiteRollDto findByRollID(Long rollID) {
        SiteRollDto siteRollDto =new SiteRollDto();
        SiteRoll siteRoll = siteRollsRepository.findByRollID(rollID);
        siteRollDto.setRollName(siteRoll.getRollName());
        return siteRollDto;
    }




    //--------------------------Save User ----------------------------------------------------
    @Override
    public CommonResponse saveSiteRolls(SiteRollDto siteRollDto) {
        CommonResponse commonResponse = new CommonResponse();
        SiteRoll siteRoll;
        try{
            List<String> validationList = this.siteRollValidation(siteRollDto);
            if (!validationList.isEmpty()) {
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }
            siteRoll = castSiteRollsDTOIntoSiteRolls(siteRollDto);
            siteRoll = siteRollsRepository.save(siteRoll);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(siteRoll));


        }catch (Exception e){
            LOGGER.error("/**************** Exception in UserService -> save()" + e);
        }
        return commonResponse;
    }

    private SiteRoll castSiteRollsDTOIntoSiteRolls(SiteRollDto siteRollDto) {
        SiteRoll siteRoll = new SiteRoll();
        siteRoll.setRollID(Long.valueOf(siteRollDto.getRollID()));
        siteRoll.setRollName(siteRollDto.getRollName());
        siteRoll.setCommonStatus(siteRollDto.getCommonStatus());
        return siteRoll;
    }


    // -------------------------------------Update User ---------------------------------------
    @Override
    public CommonResponse updateSiteRolls(SiteRollDto siteRollDto) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<String> validationList = this.siteRollValidation(siteRollDto);
            if (!validationList.isEmpty()) {
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }

            SiteRoll siteRoll= siteRollsRepository.findById(Long.valueOf(siteRollDto.getRollID())).get();
            if(siteRoll!=null){
                siteRoll.setRollName(siteRollDto.getRollName());
                siteRoll.setCommonStatus(siteRollDto.getCommonStatus());
                siteRollsRepository.save(siteRoll);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(siteRoll));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList(" not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserService -> update()" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating user"));
        }
        return commonResponse;
    }
//------------------------------------------- User ID get All details --------------------------------


    @Override
    public CommonResponse getSiteRollID(String siteRollID) {
        CommonResponse commonResponse=new CommonResponse();
        SiteRoll siteRoll;
        try {
            siteRoll =siteRollsRepository.findById(Long.valueOf(siteRollID)).get();
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(siteRoll));
        }
        catch (Exception e){
            LOGGER.error("/**************** Exception in UserService -> getUserById()"+e);
        }
        return commonResponse;
    }


    //----------------------------------Vlidation -------------------------------------------
    private List<String> siteRollValidation(SiteRollDto siteRollDto) {
        List<String> validationList = new ArrayList<>();
        if (CommonValidation.stringNullValidation(siteRollDto.getRollName()))
            validationList.add(CommonMessage.EMPTY_USERNAME);

        return validationList;
    }


}
