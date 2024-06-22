package com.example.Ceylone.Snippers.Back.End.dto;

import com.example.Ceylone.Snippers.Back.End.constant.CommonMessage;
import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingsDto {
    private String bookingID;

    private String ShootType;
    private String shootTimeDuration;
    private String location;
    private LocalDate dates;
    private String tellUsMoreDiscription;

    private UserDto userDto;
    private PhotographerDto photographerDto;

    private CommonStatus commonStatus;
}
