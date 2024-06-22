package com.example.Ceylone.Snippers.Back.End.services;

import com.example.Ceylone.Snippers.Back.End.dto.SiteRollDto;
import com.example.Ceylone.Snippers.Back.End.entity.SiteRoll;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;

public interface SiteRollsService {

    CommonResponse getAllSiteRolls();
//
    CommonResponse saveSiteRolls(SiteRollDto siteRollDto);
//
    CommonResponse updateSiteRolls(SiteRollDto siteRollDto);
//
    CommonResponse getSiteRollID(String siteRollID);
    
    SiteRollDto castSiteRollsIntoSiteRollsDto(SiteRoll siteRoll);

    SiteRoll findBySiteRollID(String rollID);


    SiteRollDto findByRollID(Long rollID);
}
