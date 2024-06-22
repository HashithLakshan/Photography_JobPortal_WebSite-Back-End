package com.example.Ceylone.Snippers.Back.End.controller;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.dto.BookingsDto;
import com.example.Ceylone.Snippers.Back.End.services.BookingService;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/bookings")
@CrossOrigin
public class BookingController {

    private final BookingService bookingService;
@Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/getAllBookings")
    public CommonResponse getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PostMapping("/saveBookings")
    public CommonResponse saveUser(@RequestBody BookingsDto bookingsDto){
        return bookingService.saveBookings(bookingsDto);
    }
    @PutMapping("/updateBookings")
    public  CommonResponse updateUser(@RequestBody BookingsDto bookingsDto){
        return bookingService.updateBookings(bookingsDto);
    }
    @GetMapping("/getBookings/{bookingID}")
    public CommonResponse getBookingByID (@PathVariable String bookingID){
        return  bookingService.getBookingByID(bookingID);
    }
//    @GetMapping("/getBookingsPhotographerSearchUsers/{photographerID}/{userID}/{userName}")
//    public CommonResponse getBookingByPhotographerIDSearching (@PathVariable String photographerID, String userID, String userName){
//        return  bookingService.getBookingByPhotographerIDSearching(photographerID,userID,userName);
//    }
    @PutMapping("/ConfirmBookings/{bookingID}")
    public  CommonResponse updateStatus(@PathVariable String bookingID ){
        return bookingService.updateStatusConfirmBookings(bookingID);
    }

    @PutMapping("/DeleteBookings/{bookingID}")
    public  CommonResponse updateStatus2(@PathVariable String bookingID ){
        return bookingService.updateStatusDeleteBookings(bookingID);
    }
    @GetMapping("/getBookingByPhotographerInactiveUsers/{photographerID}")
    public  CommonResponse getBookingByPhotographer(@PathVariable String photographerID){
        return bookingService.getBookingDetails(photographerID);
    }
    @GetMapping("/getBookingByPhotographerActiveUsers/{PhotographerId}")
    private CommonResponse getDetailsOfBookingUsingPhotographerId(@PathVariable String PhotographerId){
        return bookingService.getBookingDetailsUsingPhotographer(PhotographerId);
    }
    @GetMapping("/getBookingByPhotographerDeletes/{PhotographerId}")
    private CommonResponse getDetailsDeleteOfBookingUsingPhotographerId(@PathVariable String PhotographerId){
        return bookingService.getDetailsDeleteOfBookingUsingPhotographerId(PhotographerId);
    }
    @GetMapping("/getBookingByUser/{userID}/{photographerID}")
    public  CommonResponse getBookingUserBookingDetailss(@PathVariable String userID,@PathVariable String photographerID){
        return bookingService.getBookingUserBookingDetailss( userID,photographerID);
    }
    @GetMapping("/getBookingByUser/{userName}/{photographerID}")
    public  CommonResponse getBookingUserBookingDetails(@PathVariable String userName,@PathVariable String photographerID){
        return bookingService.getBookingUserBookingDetails( userName,photographerID);
    }
    @GetMapping("/getBookingAvailable/{photographerID}/{dates}")
    public  CommonResponse getBookingAvailable(@PathVariable String photographerID,@PathVariable LocalDate dates){
        return bookingService.getBookingAvailables(photographerID,dates);
    }

  
}
