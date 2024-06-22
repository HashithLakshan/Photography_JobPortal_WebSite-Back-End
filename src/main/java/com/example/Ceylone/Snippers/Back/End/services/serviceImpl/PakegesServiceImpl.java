package com.example.Ceylone.Snippers.Back.End.services.serviceImpl;

import com.example.Ceylone.Snippers.Back.End.constant.CommonMessage;
import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.dto.BookingsDto;
import com.example.Ceylone.Snippers.Back.End.dto.PakegesDto;
import com.example.Ceylone.Snippers.Back.End.dto.PhotographerDto;
import com.example.Ceylone.Snippers.Back.End.dto.UserDto;
import com.example.Ceylone.Snippers.Back.End.entity.Bookings;
import com.example.Ceylone.Snippers.Back.End.entity.Pakeges;
import com.example.Ceylone.Snippers.Back.End.entity.PhotographersProfiles;
import com.example.Ceylone.Snippers.Back.End.entity.User;
import com.example.Ceylone.Snippers.Back.End.repository.PakegesRepository;
import com.example.Ceylone.Snippers.Back.End.services.CatogoryService;
import com.example.Ceylone.Snippers.Back.End.services.PakegesService;
import com.example.Ceylone.Snippers.Back.End.services.PhotographerProfilesServices;
import com.example.Ceylone.Snippers.Back.End.services.PhotographerService;
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
public class PakegesServiceImpl implements PakegesService {

    private final PakegesRepository pakegesRepository;

    private final PhotographerProfilesServices photographerProfilesServices;
    private final CatogoryService catogoryService;

@Autowired
    public PakegesServiceImpl(PakegesRepository pakegesRepository, PhotographerProfilesServices photographerProfilesServices, CatogoryService catogoryService) {
        this.pakegesRepository = pakegesRepository;
    this.photographerProfilesServices = photographerProfilesServices;
    this.catogoryService = catogoryService;
}
    @Override
    public CommonResponse getAllPakeges() {
        CommonResponse commonResponse=new CommonResponse();
        List<PakegesDto> pakegesDto= new ArrayList<>();
        try {
            Predicate<Pakeges> filterOnStatus= pakeges ->pakeges.getCommonStatus()!= CommonStatus.DELETE;
            pakegesDto   =pakegesRepository.findAll().stream().filter(filterOnStatus).map(this::castpakegesIntoPakegesDto).collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(pakegesDto));
        }catch (Exception e){
            LOGGER.error("/**************** Exception in PakegesService -> getAll()"+e);
        }
        return commonResponse;
    }
    private PakegesDto castpakegesIntoPakegesDto(Pakeges pakeges ) {
        PakegesDto pakegesDto=new PakegesDto();
        pakegesDto.setPakegeName(pakeges.getPakegeName());
        pakegesDto.setCommonStatus(pakeges.getCommonStatus());
        pakegesDto.setPakegePrice(pakeges.getPakegePrice());
        pakegesDto.setPakegeCode(String.valueOf(pakeges.getPakegeCode()));
        pakegesDto.setParagraph1(pakeges.getParagraph1());
        pakegesDto.setParagraph2(pakeges.getParagraph2());
        pakegesDto.setParagraph3(pakeges.getParagraph3());
        pakegesDto.setParagraph4(pakeges.getParagraph4());
        pakegesDto.setParagraph5(pakeges.getParagraph5());


return pakegesDto;


    }
    //--------------------------Save User ----------------------------------------------------
    @Override
    public CommonResponse savePakeges(PakegesDto pakegesDto) {
        CommonResponse commonResponse = new CommonResponse();
        Pakeges  pakeges;
        try{
            List<String> validationList = this.pakegesValidation(pakegesDto);
            if (!validationList.isEmpty()) {
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }

            pakeges = PakegesDTOIntoPakege(pakegesDto);
            pakeges = pakegesRepository.save(pakeges);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(pakeges));


        }catch (Exception e){
            LOGGER.error("/**************** Exception in PakegesService -> save()" + e);
        }
        return commonResponse;
    }

    private List<String> pakegesValidation(PakegesDto pakegesDto) {

        List<String> validationList = new ArrayList<>();

        if (CommonValidation.stringNullValidation(pakegesDto.getParagraph1()))
            validationList.add(CommonMessage.EMPTY_PARAGRAPH1);
        if (CommonValidation.isValidLength(pakegesDto.getParagraph2()))
            validationList.add(CommonMessage.EMPTY_PARAGRAPH2);
        if (CommonValidation.stringNullValidation(pakegesDto.getParagraph3()))
            validationList.add(CommonMessage.EMPTY_PARAGRAPH3);
        if (CommonValidation.stringNullValidation(pakegesDto.getParagraph4()))
            validationList.add(CommonMessage.EMPTY_PRARAGARAPH4);
        if(CommonValidation.isValidLengthDiscriptions(pakegesDto.getParagraph5()))
            validationList.add(CommonMessage.EMPTY_PRARAGARAPH5);
        return validationList;
    }

    private Pakeges PakegesDTOIntoPakege(PakegesDto pakegesDto) {
Pakeges pakeges = new Pakeges();
        pakeges.setPakegeCode(Long.valueOf(pakegesDto.getPakegeCode()));
        pakeges.setPakegePrice(pakegesDto.getPakegePrice());
        pakeges.setCommonStatus(pakegesDto.getCommonStatus());
        pakeges.setParagraph1(pakegesDto.getParagraph1());
        pakeges.setParagraph2(pakegesDto.getParagraph2());
        pakeges.setParagraph3(pakegesDto.getParagraph3());
        pakeges.setParagraph4(pakegesDto.getParagraph4());
        pakeges.setParagraph5(pakegesDto.getParagraph5());
        pakeges.setPakegeName(pakegesDto.getPakegeName());
        pakeges.setPhotographersProfiles(photographerProfilesServices.findByPhotographerProfileID(pakegesDto.getPhotographersProfilesDto().getProfileID()));


        return pakeges;

    }
    @Override
    public CommonResponse updatePakeges(PakegesDto pakegesDto) {
        CommonResponse commonResponse = new CommonResponse();

        try {
            List<String> validationList = this.pakegesValidation(pakegesDto);
            if (!validationList.isEmpty()) {
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }
            Pakeges pakeges= pakegesRepository.findById(Long.valueOf(pakegesDto.getPakegeCode())).get();
            pakeges.setPakegePrice(pakegesDto.getPakegePrice());
            pakeges.setPakegeCode(Long.valueOf(pakegesDto.getPakegeCode()));
            pakeges.setCommonStatus(pakegesDto.getCommonStatus());
            pakeges.setParagraph1(pakegesDto.getParagraph1());
            pakeges.setParagraph2(pakegesDto.getParagraph2());
            pakeges.setParagraph3(pakegesDto.getParagraph3());
            pakeges.setParagraph4(pakegesDto.getParagraph4());
            pakeges.setParagraph5(pakegesDto.getParagraph5());
            pakeges.setPakegeName(pakegesDto.getPakegeName());
            pakegesRepository.save(pakeges);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(pakeges));

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserService -> update()" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating user"));
        }
        return commonResponse;
    }


//    public CommonResponse getDetailsInactiveOfPakegesUsingProfileIDInactiveAll(String profileID) {
//        CommonResponse commonResponse = new CommonResponse();
//
//        PakegesDto pakegesDto =new PakegesDto();
//
//        Predicate<Pakeges> filterOnStatus = pakeges   -> pakeges.getCommonStatus() == CommonStatus.ACTIVE;
//
//        // Retrieve bookings for the photographer and filter them
//        List<Pakeges> pakegesList = pakegesRepository.findByPhotographersProfiles_ProfileID(Long.valueOf(profileID));
//        List<PakegesDto> pakegesDtos = pakegesList.stream().filter(filterOnStatus).map(this::castpakegesIntoPakegesDto).collect(Collectors.toList());
//
//
//        commonResponse.setStatus(true);
//        commonResponse.setPayload(Collections.singletonList(pakegesDtos));
//        System.out.println(pakegesDtos);
//        return commonResponse;
//    }

    @Override
    public CommonResponse getDetailsOfPakegesUsingPakegeCode(String pakegeCode) {
        CommonResponse commonResponse = new CommonResponse();

        PakegesDto pakegesDto =new PakegesDto();

        Predicate<Pakeges> filterOnStatus = pakeges   -> pakeges.getCommonStatus() != CommonStatus.DELETE;

        // Retrieve bookings for the photographer and filter them
        List<Pakeges> pakegesList = pakegesRepository.findByPakegeCode(Long.valueOf(pakegeCode));
        List<PakegesDto> pakegesDtos = pakegesList.stream().filter(filterOnStatus).map(this::castpakegesIntoPakegesDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(pakegesDtos));
        System.out.println(pakegesDtos);
        return commonResponse;
    }


    @Override
    public CommonResponse updatePakegesInCommonStatusInactive(String pakegeCode) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            Pakeges pakeges= pakegesRepository.findById(Long.valueOf(pakegeCode)).get();
            if(pakeges!=null){
                pakeges.setCommonStatus(CommonStatus.INACTIVE);
                pakegesRepository.save(pakeges);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(pakeges));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("Pakege not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PakegesService -> update()" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating Status"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updatePakegesInCommonStatusDelete(String pakegeCode) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            Pakeges pakeges= pakegesRepository.findById(Long.valueOf(pakegeCode)).get();
            if(pakeges!=null){
                pakeges.setCommonStatus(CommonStatus.DELETE);
                pakegesRepository.save(pakeges);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(pakeges));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("Pakege not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PakegesService -> update()" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating Status"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updateUserInCommonStatusActiveSet(String pakegeCode) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            Pakeges pakeges= pakegesRepository.findById(Long.valueOf(pakegeCode)).get();
            if(pakeges!=null){
                pakeges.setCommonStatus(CommonStatus.ACTIVE);
                pakegesRepository.save(pakeges);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(pakeges));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("Pakege not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PakegesService -> update()" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating Active"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getPakegesDetailsUsingProfileIDActiveAll(String profileID) {
        CommonResponse commonResponse = new CommonResponse();
        PakegesDto  pakegesDto =new PakegesDto();

        Predicate<Pakeges> filterOnStatus = pakeges   -> pakeges.getCommonStatus() == CommonStatus.ACTIVE;

        // Retrieve bookings for the photographer and filter them
        List<Pakeges> pakegesList = pakegesRepository.findByPhotographersProfilesProfileID(Long.valueOf(profileID));
        List<PakegesDto> pakegesDtos = pakegesList.stream().filter(filterOnStatus).map(this::castpakegesIntoPakegesDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(pakegesDtos));
        System.out.println(pakegesDtos);
        return commonResponse;

    }


}
