package com.example.Ceylone.Snippers.Back.End.services;

import com.example.Ceylone.Snippers.Back.End.dto.AdminAndSiteHandalingDto;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AdminAndSiteHandalingService {
    CommonResponse saveAdminHandaling(AdminAndSiteHandalingDto adminAndSiteHandalingDto);

    CommonResponse getAllSiteDetail();

    CommonResponse updateSiteDetails(AdminAndSiteHandalingDto adminAndSiteHandalingDto);
}
