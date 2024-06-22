package com.example.Ceylone.Snippers.Back.End.services.serviceImpl;

import com.example.Ceylone.Snippers.Back.End.constant.CommonMessage;
import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.dto.PhotographerDto;
import com.example.Ceylone.Snippers.Back.End.entity.Photographer;
import com.example.Ceylone.Snippers.Back.End.repository.PhotographerRepository;
import com.example.Ceylone.Snippers.Back.End.services.PhotographerService;
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
public class PhotographerServiceImpl implements PhotographerService {

    private final PhotographerRepository photographerRepository;

    private final SiteRollsService siteRollsService;

    @Autowired
    public PhotographerServiceImpl(PhotographerRepository photographerRepository, SiteRollsService siteRollsService) {
        this.photographerRepository = photographerRepository;
        this.siteRollsService = siteRollsService;
    }


    @Override
    public CommonResponse getAllPhotographers() {
        CommonResponse commonResponse = new CommonResponse();
        List<PhotographerDto> photographerDtoList = new ArrayList<>();
        try {
            Predicate<Photographer> filterOnStatus = photographerEntity -> photographerEntity.getPhotographerStatus() == CommonStatus.ACTIVE;
            photographerDtoList = photographerRepository.findAll().stream().filter(filterOnStatus).map(this::castPhotographerIntoPhotographerDto).collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(photographerDtoList));
        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PhotographerService -> getAllActive()" + e);
        }
        return commonResponse;
    }

    public PhotographerDto castPhotographerIntoPhotographerDto(Photographer photographer) {
        PhotographerDto photographerDto = new PhotographerDto();

        photographerDto.setPhotographerID(String.valueOf(photographer.getPhotographerID()));
        photographerDto.setPhotographerUserName(photographer.getPhotographerUserName());
        photographerDto.setPhographerPassword(photographer.getPhographerPassword());
        photographerDto.setPhotographerFirstName(photographer.getPhotographerFirstName());
        photographerDto.setPhotohrapherLastName(photographer.getPhotohrapherLastName());
        photographerDto.setPhotographerNIC(photographer.getPhotographerNIC());
        photographerDto.setPhotographerGender(photographer.getPhotographerGender());
        photographerDto.setPhotographerAddress(photographer.getPhotographerAddress());
        photographerDto.setPhotographerEmail(photographer.getPhotographerAddress());
        photographerDto.setPhotogapherContactNumber(photographer.getPhotogapherContactNumber());
        photographerDto.setCommonStatus(photographer.getPhotographerStatus());
//        photographerDto.setSiteRollDto(siteRollsService.castSiteRollsIntoSiteRollsDto(photographer.getSiteRoll()));

        return photographerDto;

    }

    @Override
    public Photographer findByPhotgrapherID(String photographerID) {
        return photographerRepository.findByPhotographerID(Long.valueOf(photographerID));
    }

    @Override
    public PhotographerDto findByID(String photographerID) {
        Photographer photographer = photographerRepository.findByPhotographerID(Long.valueOf(photographerID));
        PhotographerDto photographerDto = new PhotographerDto();
        photographerDto = castPhotographerIntoPhotographerDto(photographer);
        return photographerDto;
    }

    @Override
    public CommonResponse getPhotographerUserName(String photographerUserName) {
        CommonResponse commonResponse = new CommonResponse();

        try {
            PhotographerDto photographerDto = new PhotographerDto();
            Photographer photographer = photographerRepository.getByPhotographerUserName(photographerUserName);
            photographerDto = castPhotographerIntoPhotographerDto(photographer);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(photographerDto));
        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PhotographerService -> getPhotographerByUserName()" + e);
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updatePhotographerInCommonStatusInactive(String photographerID) {
        CommonResponse commonResponse = new CommonResponse();

        try {

            Photographer photographer = photographerRepository.findById(Long.valueOf(photographerID)).get();
            if (photographer != null) {
                photographer.setPhotographerStatus(CommonStatus.INACTIVE);
                photographerRepository.save(photographer);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(photographer));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("User not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserService -> update(Inactive)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating user"));
        }
        return commonResponse;

    }

    @Override
    public CommonResponse getAllPhotographersInactive() {
        CommonResponse commonResponse = new CommonResponse();
        List<PhotographerDto> photographerDtoList = new ArrayList<>();
        try {
            Predicate<Photographer> filterOnStatus = photographerEntity -> photographerEntity.getPhotographerStatus() == CommonStatus.INACTIVE;
            photographerDtoList = photographerRepository.findAll().stream().filter(filterOnStatus).map(this::castPhotographerIntoPhotographerDto).collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(photographerDtoList));
        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PhotographerService -> getAll(Inactive)" + e);
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updateUserInCommonStatusDelete(String photographerID) {

        CommonResponse commonResponse = new CommonResponse();

        try {

            Photographer photographer = photographerRepository.findById(Long.valueOf(photographerID)).get();
            if (photographer != null) {
                photographer.setPhotographerStatus(CommonStatus.DELETE);
                photographerRepository.save(photographer);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(photographer));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("User not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PhotographerService -> update(Delete)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating user"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse updateUserInCommonStatusActive(String photographerID) {
        CommonResponse commonResponse = new CommonResponse();

        try {

            Photographer photographer = photographerRepository.findById(Long.valueOf(photographerID)).get();
            if (photographer != null) {
                photographer.setPhotographerStatus(CommonStatus.ACTIVE);
                photographerRepository.save(photographer);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(photographer));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("User not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PhotographerService -> update(Active)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating Photographer"));
        }
        return commonResponse;
    }


    //--------------------------Save User ----------------------------------------------------
    @Override
    public CommonResponse savePhotographer(PhotographerDto photographerDto) {
        CommonResponse commonResponse = new CommonResponse();
        Photographer photographer;
//        photographerDto.setSiteRollDto();
        try {
//            List<String> validationList = this.PhotographerValidation(photographerDto);
//            if (!validationList.isEmpty()) {
//                commonResponse.setErrorMessages(validationList);
//                return commonResponse;
//            }
            photographer = castPhotographerDTOIntoPhotographer(photographerDto);
            photographer = photographerRepository.save(photographer);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(photographer));


        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PhotographerService -> save()" + e);
        }
        return commonResponse;
    }

    private Photographer castPhotographerDTOIntoPhotographer(PhotographerDto photographerDto) {
        Photographer photographer = new Photographer();


        photographer.setSiteRoll(siteRollsService.findBySiteRollID(photographerDto.getSiteRollDto().getRollID()));
        photographer.setPhotographerUserName(photographerDto.getPhotographerUserName());
        photographer.setPhographerPassword(photographerDto.getPhographerPassword());
        photographer.setPhotographerFirstName(photographerDto.getPhotographerFirstName());
        photographer.setPhotohrapherLastName(photographerDto.getPhotohrapherLastName());
        photographer.setPhotographerNIC(photographerDto.getPhotographerNIC());
        photographer.setPhotographerGender(photographerDto.getPhotographerGender());
        photographer.setPhotographerAddress(photographerDto.getPhotographerAddress());
        photographer.setPhotographerEmail(photographerDto.getPhotographerAddress());
        photographer.setPhotogapherContactNumber(photographerDto.getPhotogapherContactNumber());
        photographer.setPhotographerStatus(photographerDto.getCommonStatus());

        return photographer;
    }


    //     -------------------------------------Update User ---------------------------------------
    @Override
    public CommonResponse updatePhotographer(PhotographerDto photographerDto) {
        CommonResponse commonResponse = new CommonResponse();
        try {
            List<String> validationList = this.PhotographerValidation(photographerDto);
            if (!validationList.isEmpty()) {
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }

            Photographer photographer = photographerRepository.findById(Long.valueOf(photographerDto.getPhotographerID())).get();
            if (photographer != null) {

                photographer.setPhotographerUserName(photographerDto.getPhotographerUserName());
                photographer.setPhographerPassword(photographerDto.getPhographerPassword());
                photographer.setPhotographerFirstName(photographerDto.getPhotographerFirstName());
                photographer.setPhotohrapherLastName(photographerDto.getPhotohrapherLastName());
                photographer.setPhotographerNIC(photographerDto.getPhotographerNIC());
                photographer.setPhotographerGender(photographerDto.getPhotographerGender());
                photographer.setPhotographerAddress(photographerDto.getPhotographerAddress());
                photographer.setPhotographerEmail(photographerDto.getPhotographerAddress());
                photographer.setPhotogapherContactNumber(photographerDto.getPhotogapherContactNumber());
                photographer.setPhotographerStatus(photographerDto.getCommonStatus());
                photographer.setSiteRoll(siteRollsService.findBySiteRollID(photographerDto.getSiteRollDto().getRollID()));

                photographerRepository.save(photographer);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(photographer));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList(" Photographer not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in PhotographerService -> update()" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating Photographer"));
        }
        return commonResponse;
    }
//------------------------------------------- User ID get All details --------------------------------


    @Override
    public CommonResponse getPhotographerByID(String photographerID) {
        CommonResponse commonResponse = new CommonResponse();

        try {
            PhotographerDto photographerDto = new PhotographerDto();
            Photographer photographer = photographerRepository.findById(Long.valueOf(photographerID)).get();
            photographerDto = castPhotographerIntoPhotographerDto(photographer);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(photographerDto));
        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserService -> getPhotographerById()" + e);
        }
        return commonResponse;
    }


    //----------------------------------Vlidation -------------------------------------------
    private List<String> PhotographerValidation(PhotographerDto photographerDto) {
        List<String> validationList = new ArrayList<>();
        if (CommonValidation.stringNullValidation(photographerDto.getPhotographerEmail()))
            validationList.add(CommonMessage.EMPTY_EMAIL_ADDRESS);
        if (CommonValidation.stringNullValidation(photographerDto.getPhotographerID()))
            validationList.add(CommonMessage.EMPTY_PASSWORD);
        if (CommonValidation.stringNullValidation(photographerDto.getPhotographerUserName()))
            validationList.add(CommonMessage.EMPTY_USERNAME);
        if (CommonValidation.stringNullValidation(photographerDto.getPhographerPassword()))
            validationList.add(CommonMessage.EMPTY_PASSWORD);
        if (CommonValidation.stringNullValidation(photographerDto.getPhotographerFirstName()))
            validationList.add(CommonMessage.EMPTY_FIRST_NAME);
        if (CommonValidation.stringNullValidation(photographerDto.getPhotohrapherLastName()))
            validationList.add(CommonMessage.EMPTY_LAST_NAME);
        if (CommonValidation.stringNullValidation(photographerDto.getPhotographerNIC()))
            validationList.add(CommonMessage.EMPTY_NIC);
        if (CommonValidation.stringNullValidation(photographerDto.getPhotographerGender()))
            validationList.add(CommonMessage.EMPTY_GENDER);
        if (CommonValidation.stringNullValidation(photographerDto.getPhotographerAddress()))
            validationList.add(CommonMessage.EMPTY_ADDRESS);
        if (CommonValidation.stringNullValidation(photographerDto.getPhotographerEmail()))
            validationList.add(CommonMessage.EMPTY_EMAIL_ADDRESS);
        if (CommonValidation.stringNullValidation(photographerDto.getPhotogapherContactNumber()))
            validationList.add(CommonMessage.EMPTY_CONTACT_NUMBER);
        return validationList;
    }


}
