package com.example.Ceylone.Snippers.Back.End.controller;

import com.example.Ceylone.Snippers.Back.End.dto.PhotographerDto;
import com.example.Ceylone.Snippers.Back.End.dto.PhotographersProfilesDto;
import com.example.Ceylone.Snippers.Back.End.dto.UserDto;
import com.example.Ceylone.Snippers.Back.End.services.CloudinaryService;
import com.example.Ceylone.Snippers.Back.End.services.PhotographerProfilesServices;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/photographerProfiles")
@CrossOrigin
public class PhotographersProfilesController {

    private final PhotographerProfilesServices  photographerProfilesServices;
    private final CloudinaryService cloudinaryService;



@Autowired
    public PhotographersProfilesController(PhotographerProfilesServices photographerProfilesServices, CloudinaryService cloudinaryService) {
        this.photographerProfilesServices = photographerProfilesServices;
    this.cloudinaryService = cloudinaryService;
}
    @GetMapping("/getAllProfilesActive")
    public CommonResponse getAllProfilesActive() {
        return photographerProfilesServices.getAllUProfilesActives();
    }
    @GetMapping("/getAllProfilesInactive")
    public CommonResponse getAllProfilesInactive() {
        return photographerProfilesServices.getAllProfilesInactive();
    }
    @GetMapping("/getAllProfilesDelete")
    public CommonResponse getAllProfilesDelete() {
        return photographerProfilesServices.getAllProfilesDelete();
    }

    @PostMapping("/saveProfile")
    public CommonResponse savePhotographer(@RequestBody PhotographersProfilesDto photographersProfilesDto) {
       return photographerProfilesServices.saveProfile(photographersProfilesDto);

    }

    @PostMapping("/saveImgs")
    public ResponseEntity<?> saveImgs(@RequestPart("image1") MultipartFile image1,
                                   @RequestPart("image2") MultipartFile image2, @RequestParam("profileID") String profileID) throws IOException {
       return cloudinaryService.saveImgs(image1, image2, profileID);

    }
    @PutMapping("/updateProfiles")
    public  CommonResponse updateProfiles(@RequestBody PhotographersProfilesDto photographersProfilesDto){
        return photographerProfilesServices.updateProfiles(photographersProfilesDto);
    }
    @GetMapping("/getProfiles/{profileID}")
    public CommonResponse getProfileByID (@PathVariable String profileID){
        return  photographerProfilesServices.getProfileByID(profileID);
    }
    @GetMapping("/getProfiles/{catogoryID}")
    public CommonResponse getProfileBycatagoryID (@PathVariable String catogoryID){
        return  photographerProfilesServices.getProfileBycatagoryID(catogoryID);
    }
    @PutMapping("/Profiles/deleteInactive/{profileID}")
    public  CommonResponse updateProfilesDeleteInactive(@PathVariable String profileID){
        return photographerProfilesServices.updateProfilesDeleteInactive(profileID);
    }
    @PutMapping("/updateProfiles/recoveryProfileActive/{profileID}")
    public  CommonResponse updateProfilesActive(@PathVariable String profileID){
        return photographerProfilesServices.updateProfilesActive(profileID);
    }
    @PutMapping("/updateProfiles/permanentDelete/{profileID}")
    public  CommonResponse updateProfilesDelete(@PathVariable String profileID){
        return photographerProfilesServices.updateProfilesDelete(profileID);
    }








}
