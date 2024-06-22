package com.example.Ceylone.Snippers.Back.End.controller;

import com.example.Ceylone.Snippers.Back.End.dto.SiteRollDto;
import com.example.Ceylone.Snippers.Back.End.services.SiteRollsService;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/systemRolls")
public class SiteRollsController {

    private  final SiteRollsService systemRollsService;

@Autowired
    public SiteRollsController(SiteRollsService systemRollsService) {
        this.systemRollsService = systemRollsService;
    }

    @GetMapping("/getAllSiteRolls")
    public CommonResponse getAllSiteRolls() {
        return systemRollsService.getAllSiteRolls();
    }
    @PostMapping("/saveSiteRolls")
    public CommonResponse saveSystemRolls(@RequestBody SiteRollDto siteRollDto){
        return systemRollsService.saveSiteRolls(siteRollDto);
    }
    @PutMapping("/updateSiteRolls")
    public  CommonResponse updateSiteRolls(@RequestBody SiteRollDto siteRollDto){
        return systemRollsService.updateSiteRolls(siteRollDto);
    }
    @GetMapping("/getSiteRollID/{siteRollID}")
    public CommonResponse getSiteRollID (@PathVariable String siteRollID){
        return  systemRollsService.getSiteRollID(siteRollID);
    }
}
