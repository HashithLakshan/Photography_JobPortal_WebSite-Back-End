package com.example.Ceylone.Snippers.Back.End.controller;

import com.example.Ceylone.Snippers.Back.End.dto.CatogoryDto;
import com.example.Ceylone.Snippers.Back.End.dto.PakegesDto;
import com.example.Ceylone.Snippers.Back.End.dto.UserDto;
import com.example.Ceylone.Snippers.Back.End.services.PakegesService;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pakeges")
@CrossOrigin
public class PakegesController {

    private final PakegesService pakegesService;

@Autowired
    public PakegesController(PakegesService pakegesService) {
        this.pakegesService = pakegesService;
    }

    @GetMapping("/getAllPakeges")
    public CommonResponse getAllPakeges() {
        return pakegesService.getAllPakeges();
    }
    @PostMapping("/savePakeges")
    public CommonResponse savePakeges(@RequestBody PakegesDto pakegesDto){
        return pakegesService.savePakeges(pakegesDto);
    }
    @PutMapping("/updatePakeges")
    public CommonResponse updatePakeges(@RequestBody PakegesDto pakegesDto){
        return pakegesService.updatePakeges(pakegesDto);
    }


    @GetMapping("/getPakegesByPhotographerActivePakeges/{profileID}")
    private CommonResponse getDetailsOfPakegesActiveUsingProfileID(@PathVariable String profileID){
        return pakegesService.getPakegesDetailsUsingProfileIDActiveAll(profileID);
    }
//    @GetMapping("/getPakegesByPakegesINACTIVE/{photographerID}")
//    private CommonResponse getDetailsInactiveOfPakegesUsingProfileID(@PathVariable String photographerID){
//        return pakegesService.getDetailsInactiveOfPakegesUsingProfileIDInactiveAll(photographerID);
//    }
    @GetMapping("/getPakegesByPakegesID/{pakegeCode}")
    private CommonResponse getDetailsOfPakegesUsingPakegeCode(@PathVariable String pakegeCode){
        return pakegesService.getDetailsOfPakegesUsingPakegeCode(pakegeCode);
    }


    @PutMapping("/updateUser/InactiveDelete/{pakegeCode}")
    public CommonResponse updateUserInCommonStatusInactive(@PathVariable String pakegeCode) {
        return pakegesService.updatePakegesInCommonStatusInactive(pakegeCode);
    }
    @PutMapping("/updateUser/permenetDelete/{pakegeCode}")
    public CommonResponse updateUserInCommonStatusDelete(@PathVariable String pakegeCode) {
        return pakegesService.updatePakegesInCommonStatusDelete(pakegeCode);
    }
    @PutMapping("/updateUser/recoveryActive/{pakegeCode}")
    public CommonResponse updateUserInCommonStatusActiveSet(@PathVariable String pakegeCode) {
        return pakegesService.updateUserInCommonStatusActiveSet(pakegeCode);
    }


}
