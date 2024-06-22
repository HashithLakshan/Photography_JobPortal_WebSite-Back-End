package com.example.Ceylone.Snippers.Back.End.controller;

import com.example.Ceylone.Snippers.Back.End.dto.PhotographersFeedbacksDto;
import com.example.Ceylone.Snippers.Back.End.dto.UserFeedbacksDto;
import com.example.Ceylone.Snippers.Back.End.services.UserFeedbacksService;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/userFeedbacks")
@CrossOrigin
public class UserFeedbacksController {

    private  final UserFeedbacksService userFeedbacksService;

    @Autowired
    public UserFeedbacksController(UserFeedbacksService userFeedbacksService) {
        this.userFeedbacksService = userFeedbacksService;
    }
    @GetMapping("/getAllUsersFeedbacksActives")
    public CommonResponse getAllFeedbacksActive() {
        return userFeedbacksService.getAllFeedbackActives();
    }
    @GetMapping("/getAllUsersFeedbacksInactives")
    public CommonResponse getAllFeedbacksInactive() {
        return userFeedbacksService.getAllFeedbacksInactive();
    }

    @PostMapping("/saveUsersFeedbacks")
    public CommonResponse saveFeedbacksUsers(@RequestBody UserFeedbacksDto userFeedbacksDto){
        return userFeedbacksService.saveFeedbacksUsers(userFeedbacksDto);
    }

    @PutMapping("/updateUsersFeedbacks/replingThis YES/{userFeedbackID}")
    public CommonResponse updateUsersFeedbacksReply_YES(@PathVariable String userFeedbackID) {
        return userFeedbacksService.updateUsersFeedbacksReply_YES(userFeedbackID);
    }
    @PutMapping("/updateUsersFeedbacks/DeleteUsersFeedbackInactive/{userFeedbackID}")
    public CommonResponse updatePhotographerFeedbacksInactive(@PathVariable String userFeedbackID) {
        return userFeedbacksService.updateUsersFeedbacksInactive(userFeedbackID);
    }
    @PutMapping("/updateUsersFeedbacks/DeleteUsersFeedbackPermanentDelete/{userFeedbackID}")
    public CommonResponse updateUserFeedbacksDelete(@PathVariable String userFeedbackID) {
        return userFeedbacksService.updateUserFeedbacksDelete(userFeedbackID);
    }
    @PutMapping("/updateUsersFeedbacks/UserFeedbackRecoverActive/{userFeedbackID}")
    public CommonResponse updateUserFeedbacksActive(@PathVariable String userFeedbackID) {
        return userFeedbacksService.updateUsersFeedbacksActive(userFeedbackID);
    }
    @GetMapping("/getUsersFeedbacks1/{userID}")
    public  CommonResponse getUserFeedbacksDetails(@PathVariable String userID){
        return userFeedbacksService.getUserFeedbacksDetails( userID);
    }
    @GetMapping("/getUserFeedbacks/{userName}")
    public  CommonResponse getUserFeedbacksDetails1(@PathVariable String userName){
        return userFeedbacksService.getUserFeedbacksDetails1( userName);
    }



}
