package com.example.Ceylone.Snippers.Back.End.controller;

import com.example.Ceylone.Snippers.Back.End.dto.PhotographerDto;
import com.example.Ceylone.Snippers.Back.End.dto.UserDto;
import com.example.Ceylone.Snippers.Back.End.services.PhotographerService;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/photographer")
@CrossOrigin(origins ="*")
public class PhotographerController {

    private final PhotographerService photographerService;

@Autowired
    public PhotographerController(PhotographerService photographerService) {
        this.photographerService = photographerService;
    }
    @GetMapping("/getAllPhotographersActive")
    public CommonResponse getAllPhotographers() {
        return photographerService.getAllPhotographers();
    }
    @GetMapping("/getAllPhotographersInactive")
    public CommonResponse getAllPhotographersInactive() {
        return photographerService.getAllPhotographersInactive();
    }

    @PostMapping("/savePhotographers")
    public CommonResponse savePhotographer(@RequestBody PhotographerDto photographerDto){
        System.out.println("here!!!!");
        return  photographerService.savePhotographer(photographerDto);
    }
    @PutMapping("/updatePhotographer")
    public  CommonResponse updatePhotographer(@RequestBody PhotographerDto  photographerDto){
        return photographerService.updatePhotographer(photographerDto);
    }
    @GetMapping("/getPhotographer/{photographerID}")
    public CommonResponse getPhotographerByID (@PathVariable String photographerID ){
        return  photographerService.getPhotographerByID(photographerID);
    }

    @GetMapping("/getPhotographerName/{photographerUserName}")
    public CommonResponse getPhotographerUserName (@PathVariable String photographerUserName){
        return  photographerService.getPhotographerUserName(photographerUserName);
    }

    @PutMapping("/updatePhotographer/deleteInactive/{photographerID}")
    public  CommonResponse updateUserInCommonStatusInactive(@PathVariable String photographerID){
        return photographerService.updatePhotographerInCommonStatusInactive(photographerID);
    }
    @PutMapping("/updatePhotographer/permenatDelete/{photographerID}")
    public  CommonResponse updateUserInCommonStatusDelete(@PathVariable String photographerID){
        return photographerService.updateUserInCommonStatusDelete(photographerID);
    }
    @PutMapping("/updatePhotographer/recoveryActiveSet/{photographerID}")
    public  CommonResponse updateUserInCommonStatusActive(@PathVariable String photographerID){
        return photographerService.updateUserInCommonStatusActive(photographerID);
    }



}
