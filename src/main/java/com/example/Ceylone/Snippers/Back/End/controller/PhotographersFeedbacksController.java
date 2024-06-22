package com.example.Ceylone.Snippers.Back.End.controller;

import com.example.Ceylone.Snippers.Back.End.dto.BookingsDto;
import com.example.Ceylone.Snippers.Back.End.dto.PhotographersFeedbacksDto;
import com.example.Ceylone.Snippers.Back.End.services.PhotographerService;
import com.example.Ceylone.Snippers.Back.End.services.PhotographersFeedbacksService;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/PhotographerFeedbacks")
public class PhotographersFeedbacksController {

    private  final PhotographersFeedbacksService photographersFeedbacksService;

    @Autowired
    public PhotographersFeedbacksController( PhotographersFeedbacksService photographersFeedbacksService) {
        this.photographersFeedbacksService = photographersFeedbacksService;

    }

    @GetMapping("/getAllPhotographersFeedbacksActives")
    public CommonResponse getAllFeedbacksActive() {
        return photographersFeedbacksService.getAllFeedbackActives();
    }
    @GetMapping("/getAllPhotographersFeedbacksInactives")
    public CommonResponse getAllFeedbacksInactive() {
        return photographersFeedbacksService.getAllFeedbacksInactive();
    }

    @PostMapping("/savePhotographersFeedbacks")
    public CommonResponse saveFeedbacksPhotographers(@RequestBody PhotographersFeedbacksDto photographersFeedbacksDto){
        return photographersFeedbacksService.saveFeedbacksPhotographers(photographersFeedbacksDto);
    }

    @PutMapping("/updatePhotographerFeedbacks/replingThis YES/{photographerFeedbackID}")
    public CommonResponse updatePhotographerFeedbacksReply_YES(@PathVariable String photographerFeedbackID) {
        return photographersFeedbacksService.updatePhotographerFeedbacksReply_YES(photographerFeedbackID);
    }
    @PutMapping("/updatePhotographerFeedbacks/DeletePhotographerFeedbackInactive/{photographerFeedbackID}")
    public CommonResponse updatePhotographerFeedbacksInactive(@PathVariable String photographerFeedbackID) {
        return photographersFeedbacksService.updatePhotographerFeedbacksInactive(photographerFeedbackID);
    }
    @PutMapping("/updatePhotographerFeedbacks/DeletePhotographerFeedbackPermanentDelete/{photographerFeedbackID}")
    public CommonResponse updatePhotographerFeedbacksDelete(@PathVariable String photographerFeedbackID) {
        return photographersFeedbacksService.updatePhotographerFeedbacksDelete(photographerFeedbackID);
    }
    @PutMapping("/updatePhotographerFeedbacks/PhotographerFeedbackRecoverActive/{photographerFeedbackID}")
    public CommonResponse updatePhotographerFeedbacksActive(@PathVariable String photographerFeedbackID) {
        return photographersFeedbacksService.updatePhotographerFeedbacksActive(photographerFeedbackID);
    }
    @GetMapping("/getPhotographerFeedbacks1/{photographerID}")
    public  CommonResponse getPhotographerFeedbacksDetails(@PathVariable String photographerID){
        return photographersFeedbacksService.getPhotographerFeedbacksDetails( photographerID);
    }
    @GetMapping("/getPhotographerFeedbacks/{photographerUserName}")
    public  CommonResponse getPhotographerFeedbacksDetails1(@PathVariable String photographerUserName){
        return photographersFeedbacksService.getPhotographerFeedbacksDetails1( photographerUserName);
    }
}
