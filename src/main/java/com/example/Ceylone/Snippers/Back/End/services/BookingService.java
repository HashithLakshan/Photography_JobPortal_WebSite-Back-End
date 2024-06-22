package com.example.Ceylone.Snippers.Back.End.services;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.dto.BookingsDto;
import com.example.Ceylone.Snippers.Back.End.entity.Bookings;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;

import java.time.LocalDate;

public interface BookingService{


    CommonResponse saveBookings(BookingsDto bookingsDto);

    CommonResponse updateBookings(BookingsDto bookingsDto);


    BookingsDto castBookingIntoBookingDto(Bookings bookings);


    CommonResponse getBookingByID(String bookingID);

    CommonResponse updateStatusConfirmBookings(String bookingID);


    Bookings findByBookingID(String bookingID);

    CommonResponse getAllBookings();

    CommonResponse getBookingDetails(String photogpapherId);

    

    CommonResponse getBookingDetailsUsingPhotographer(String photographerId);


    CommonResponse getBookingUserBookingDetailss(String userID, String photographerID);

    CommonResponse getDetailsDeleteOfBookingUsingPhotographerId(String photographerId);


    CommonResponse updateStatusDeleteBookings(String bookingID);

    CommonResponse getBookingUserBookingDetails(String userName, String photographerID);

    CommonResponse getBookingAvailables(String photographerID, LocalDate dates);


//    CommonResponse getBookingByPhotographerIDSearching(String photographerID, String userID, String userName);
}
