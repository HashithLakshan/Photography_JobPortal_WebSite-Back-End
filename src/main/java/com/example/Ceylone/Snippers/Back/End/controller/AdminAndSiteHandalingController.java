package com.example.Ceylone.Snippers.Back.End.controller;

import com.example.Ceylone.Snippers.Back.End.dto.AdminAndSiteHandalingDto;
import com.example.Ceylone.Snippers.Back.End.dto.BookingsDto;
import com.example.Ceylone.Snippers.Back.End.dto.UserDto;
import com.example.Ceylone.Snippers.Back.End.services.AdminAndSiteHandalingService;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/admin")
public class AdminAndSiteHandalingController {

    private final AdminAndSiteHandalingService adminAndSiteHandalingService;

@Autowired
    public AdminAndSiteHandalingController(AdminAndSiteHandalingService adminAndSiteHandalingService) {
        this.adminAndSiteHandalingService = adminAndSiteHandalingService;
    }


    @PostMapping("/saveAdminHandaling")
    public CommonResponse saveAdminHandaling(@RequestBody AdminAndSiteHandalingDto adminAndSiteHandalingDto) {
        return adminAndSiteHandalingService.saveAdminHandaling(adminAndSiteHandalingDto);

    }
    @GetMapping("/getAllSiteDetails")
    public CommonResponse getAllSiteDetail() {
        return adminAndSiteHandalingService.getAllSiteDetail();
    }
    @PutMapping("/updateSiteDetails")
    public  CommonResponse updateSiteDetails(@RequestBody AdminAndSiteHandalingDto adminAndSiteHandalingDto){
        return  adminAndSiteHandalingService.updateSiteDetails(adminAndSiteHandalingDto);
    }


}
