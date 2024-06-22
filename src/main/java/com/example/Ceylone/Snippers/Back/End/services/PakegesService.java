package com.example.Ceylone.Snippers.Back.End.services;

import com.example.Ceylone.Snippers.Back.End.dto.PakegesDto;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;

public interface PakegesService {


    CommonResponse getAllPakeges();

    //--------------------------Save User ----------------------------------------------------
    CommonResponse savePakeges(PakegesDto pakegesDto);

    CommonResponse updatePakeges(PakegesDto pakegesDto);


    CommonResponse getPakegesDetailsUsingProfileIDActiveAll(String profileID);

//    CommonResponse getDetailsInactiveOfPakegesUsingProfileIDInactiveAll(String photographerID);

    CommonResponse getDetailsOfPakegesUsingPakegeCode(String pakegeCode);



    CommonResponse updatePakegesInCommonStatusInactive(String pakegeCode);

    CommonResponse updatePakegesInCommonStatusDelete(String pakegeCode);

    CommonResponse updateUserInCommonStatusActiveSet(String pakegeCode);
}
