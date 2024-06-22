package com.example.Ceylone.Snippers.Back.End.dto;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PakegesDto {
    private String pakegeCode;
    private int pakegePrice;
    private String pakegeName;
    private String paragraph1;
    private String paragraph2;
    private String paragraph3;
    private String paragraph4;
    private String paragraph5;
    private PhotographersProfilesDto photographersProfilesDto;
    private CommonStatus commonStatus;
}
