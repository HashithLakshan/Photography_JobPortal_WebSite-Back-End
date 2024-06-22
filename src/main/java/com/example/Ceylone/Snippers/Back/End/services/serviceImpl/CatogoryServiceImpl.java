package com.example.Ceylone.Snippers.Back.End.services.serviceImpl;

import com.example.Ceylone.Snippers.Back.End.constant.CommonMessage;
import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.dto.CatogoryDto;
import com.example.Ceylone.Snippers.Back.End.entity.Catogory;
import com.example.Ceylone.Snippers.Back.End.repository.CatogoryRepository;
import com.example.Ceylone.Snippers.Back.End.services.CatogoryService;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import com.example.Ceylone.Snippers.Back.End.utill.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Service
public class CatogoryServiceImpl implements CatogoryService {

    private final CatogoryRepository catogoryRepository;


@Autowired
    public CatogoryServiceImpl(CatogoryRepository catogoryRepository) {
    this.catogoryRepository = catogoryRepository;

}
//     --------------------Save Catogory ----------------------





    private Catogory castCategoryDTOIntoCategory(CatogoryDto catogoryDto,MultipartFile file) throws IOException {
Catogory catogory = new Catogory();
        catogory.setCatogoryID(Long.valueOf(catogoryDto.getCatogoryID()));
        catogory.setCatogoryName(catogoryDto.getCatogoryName());
        catogory.setCatogoryDiscription(catogoryDto.getCatogoryDiscription());
        catogory.setCommonStatus(catogoryDto.getCommonStatus());
        catogory.setCatagoryphotoLink(catogoryDto.getCatagoryPhotolink());
        return catogory;
    }
    // -------------------------------------Update User ---------------------------------------
    @Override
    public CommonResponse updateCtogory(CatogoryDto catogoryDto) {
        CommonResponse commonResponse= new CommonResponse();
        try {
            List<String>validationList=this.catogaryValidation(catogoryDto);
            if (!validationList.isEmpty()) {
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }
            Catogory catogory= catogoryRepository.getById(Long.valueOf(catogoryDto.getCatogoryID()));
            if( catogory != null){
                catogory.setCatogoryName(catogoryDto.getCatogoryName());
                catogory.setCatogoryDiscription(catogoryDto.getCatogoryDiscription());
                catogory.setCommonStatus(catogoryDto.getCommonStatus());
                catogoryRepository.save(catogory);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(catogory));
             }
           else  {
                commonResponse.setErrorMessages(Collections.singletonList("Catagery not found"));
            }

        }catch (Exception e){
            LOGGER.error("/**************** Exception in CatageryService -> update()"+e);
        }
        return commonResponse;
    }



    // ------------------------------------ All Users ----------------------------------------
    @Override
    public CommonResponse getAllCtogory() {
        CommonResponse commonResponse=new CommonResponse();
        List<CatogoryDto> catagoryDtoList = new ArrayList<>();
        try {
            Predicate<Catogory> filterOnStatus= catogory ->  catogory.getCommonStatus()!= CommonStatus.DELETE;
            catagoryDtoList= catogoryRepository.findAll().stream().filter(filterOnStatus).map(this::castCatogoryIntoCtogoryDto).collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(catagoryDtoList));
        }catch (Exception e){
            LOGGER.error("/**************** Exception in UserService -> getAll()"+e);
        }
        return commonResponse;
    }
    @Override
    public CatogoryDto castCatogoryIntoCtogoryDto(Catogory catogory) {
        CatogoryDto catogoryDto=new CatogoryDto();
        catogoryDto.setCatogoryID(String.valueOf(catogory.getCatogoryID()));
        catogoryDto.setCatogoryName(catogory.getCatogoryName());
        catogoryDto.setCommonStatus(catogory.getCommonStatus());
catogoryDto.setCatagoryPhotolink(catogory.getCatagoryphotoLink());

        return catogoryDto;

    }
    private List<String> catogaryValidation(CatogoryDto catogoryDto) {

        List<String> validationList = new ArrayList<>();
        if (catogoryDto == null)
            validationList.add(CommonMessage.EMPTY_PAKEGE_NAME);


//        if (CommonValidation.stringNullValidation(catogoryDto.getCatogoryName()))
//            validationList.add(CommonMessage.EMPTY_CATOGARY_PHOTO);
        return validationList;
    }

    @Override
    public CommonResponse getCatagoryById(String catagoryID) {
    CommonResponse commonResponse = new  CommonResponse();
    Catogory catogory;
        try {
            catogory =catogoryRepository.findById(Long.valueOf(catagoryID)).get();
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(catogory));
        }
        catch (Exception e){
            LOGGER.error("/**************** Exception in UserService -> getUserById()"+e);
        }
        return commonResponse;

    }

    @Override
    public CommonResponse saveCatagory(CatogoryDto catogoryDto, MultipartFile file) {
        CommonResponse commonResponse = new CommonResponse();
        Catogory catogory;
        try {
            List<String> validationList = this.catogaryValidation(catogoryDto);
            if (!validationList.isEmpty()) {
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }

            catogory = castCategoryDTOIntoCategory(catogoryDto, file);
            catogory = catogoryRepository.save(catogory);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(catogory));
        } catch (Exception e) {
            LOGGER.error("/**************** Exception in CatagoriesService -> save()" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while saving the Catagory."));
        }
        return commonResponse;
    }

    @Override
    public Catogory findByID(String catogoryID) {
        return catogoryRepository.findById(Long.valueOf(catogoryID)).get();
    }


}
