package com.example.Ceylone.Snippers.Back.End.services;

import com.example.Ceylone.Snippers.Back.End.dto.CatogoryDto;
import com.example.Ceylone.Snippers.Back.End.entity.Catogory;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CatogoryService {



    CommonResponse getAllCtogory();

    CommonResponse updateCtogory(CatogoryDto catogoryDto);


    CatogoryDto castCatogoryIntoCtogoryDto(Catogory catogory);

    CommonResponse getCatagoryById(String catagoryID);

    CommonResponse saveCatagory(CatogoryDto catogoryDto, MultipartFile file);


    Catogory findByID(String catogoryID);
}
