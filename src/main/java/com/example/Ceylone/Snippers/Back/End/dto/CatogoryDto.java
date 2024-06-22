package com.example.Ceylone.Snippers.Back.End.dto;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CatogoryDto {


    private String catogoryID;
    private String catogoryName;
    private String catogoryDiscription;
    private CommonStatus commonStatus;
    private String catagoryPhotolink;



}
