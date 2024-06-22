package com.example.Ceylone.Snippers.Back.End.services.serviceImpl;

import com.example.Ceylone.Snippers.Back.End.constant.CommonMessage;
import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.dto.BookingsDto;
import com.example.Ceylone.Snippers.Back.End.dto.UserDto;
import com.example.Ceylone.Snippers.Back.End.entity.Bookings;
import com.example.Ceylone.Snippers.Back.End.repository.BookingRepository;
import com.example.Ceylone.Snippers.Back.End.services.BookingService;
import com.example.Ceylone.Snippers.Back.End.services.PhotographerService;
import com.example.Ceylone.Snippers.Back.End.services.UserService;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import com.example.Ceylone.Snippers.Back.End.utill.CommonValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Service
public  class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final UserService userService;


    private final PhotographerService photographerService;
@Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, UserService userService, PhotographerService photographerService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.photographerService = photographerService;
    }




    // ------------------------------------ All Users ----------------------------------------
    @Override
    public CommonResponse getAllBookings() {
        CommonResponse commonResponse=new CommonResponse();
        List<BookingsDto> bookingsDtoList = new ArrayList<>();
        try {
            Predicate<Bookings> filterOnStatus= bookings  ->  bookings.getCommonStatus()!= CommonStatus.DELETE;
            bookingsDtoList= bookingRepository.findAll().stream().filter(filterOnStatus).map(this::castBookingIntoBookingDto).collect(Collectors.toList());
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(bookingsDtoList));
        }catch (Exception e){
            LOGGER.error("/**************** Exception in BookingService -> getAll(Bookings)"+e);
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getBookingDetails(String photogpapherID) {
    CommonResponse commonResponse = new CommonResponse();


        UserDto userDto = new UserDto();
        BookingsDto  bookingsDto =new BookingsDto();

        Predicate<Bookings> filterOnStatus = bookings1  -> bookings1.getCommonStatus() == CommonStatus.INACTIVE;

        // Retrieve bookings for the photographer and filter them
        List<Bookings> bookingsList = bookingRepository.findByPhotographer_PhotographerID(Long.valueOf(photogpapherID));
        List<BookingsDto> bookingsDtos = bookingsList.stream().filter(filterOnStatus).map(this::castBookingIntoBookingDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(bookingsDtos));
        System.out.println(bookingsDtos);
        return commonResponse;
    }




    @Override
    public CommonResponse getBookingDetailsUsingPhotographer(String photographerId) {
        CommonResponse commonResponse = new CommonResponse();
        BookingsDto bookingsDtos2 = new BookingsDto();

        List<BookingsDto> bookingsDtoList = new ArrayList<>();
        List<BookingsDto> bookingsDtoList2 = new ArrayList<>();
        try {
            Predicate<Bookings> filterOnStatus= bookings  ->  bookings.getCommonStatus()== CommonStatus.ACTIVE;
            bookingsDtoList= bookingRepository.findAll().stream().filter(filterOnStatus).map(this::castBookingIntoBookingDto).collect(Collectors.toList());
            for (BookingsDto bookings:bookingsDtoList){
                BookingsDto bookingsDtos = new BookingsDto();
                bookingsDtos.setBookingID(String.valueOf(bookings.getBookingID()));
                bookingsDtos.setLocation(bookings.getLocation());
                bookingsDtos.setDates(bookings.getDates());
                bookingsDtos.setShootType(bookings.getShootType());
                bookingsDtos.setShootTimeDuration(bookings.getShootTimeDuration());
                bookingsDtos.setTellUsMoreDiscription(bookings.getTellUsMoreDiscription());
                bookingsDtos.setCommonStatus(bookings.getCommonStatus());
                bookingsDtos.setUserDto(bookings.getUserDto());
                bookingsDtoList2.add(bookingsDtos);
            }
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(bookingsDtoList2));
        }catch (Exception e){
            LOGGER.error("/**************** Exception in UserService -> getAll()"+e);
        }
       return commonResponse;
    }

    @Override
    public CommonResponse getBookingUserBookingDetailss(String userID, String photographerID) {
        CommonResponse commonResponse = new CommonResponse();


        UserDto userDto = new UserDto();
        BookingsDto  bookingsDto =new BookingsDto();

        Predicate<Bookings> filterOnStatus = bookings1  -> bookings1.getCommonStatus() != CommonStatus.DELETE;

        // Retrieve bookings for the photographer and filter them
        List<Bookings> bookingsList = bookingRepository.findByUserUserIDAndPhotographer_PhotographerID((Long.valueOf(userID)), Long.valueOf(photographerID));
        List<BookingsDto> bookingsDtos = bookingsList.stream().filter(filterOnStatus).map(this::castBookingIntoBookingDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(bookingsDtos));
        System.out.println(bookingsDtos);
        return commonResponse;
    }

    @Override
    public CommonResponse getDetailsDeleteOfBookingUsingPhotographerId(String photographerId) {
        CommonResponse commonResponse = new CommonResponse();





        Predicate<Bookings> filterOnStatus = bookings1  -> bookings1.getCommonStatus() == CommonStatus.DELETE;

        // Retrieve bookings for the photographer and filter them
        List<Bookings> bookingsList = bookingRepository.findByPhotographer_PhotographerID(Long.valueOf(photographerId));
        List<BookingsDto> bookingsDtos = bookingsList.stream().filter(filterOnStatus).map(this::castBookingIntoBookingDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(bookingsDtos));
        System.out.println(bookingsDtos);
        return commonResponse;
    }

    @Override
    public CommonResponse updateStatusDeleteBookings(String bookingID) {
        CommonResponse commonResponse = new CommonResponse();
        Bookings bookings;
        try {
            bookings= bookingRepository.findById(Long.valueOf(bookingID)).get();
            if (bookings != null) {
                bookings.setCommonStatus(CommonStatus.DELETE);
                bookingRepository.save(bookings);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(bookings));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("Booking not found"));
            }

        } catch (Exception e) {
            LOGGER.error("/**************** Exception in BookingService -> update()" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating user"));
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getBookingUserBookingDetails(String userName, String photographerID) {

        CommonResponse commonResponse = new CommonResponse();


        UserDto userDto = new UserDto();
        BookingsDto  bookingsDto =new BookingsDto();

        Predicate<Bookings> filterOnStatus = bookings1  -> bookings1.getCommonStatus() != CommonStatus.DELETE;

        // Retrieve bookings for the photographer and filter them
        List<Bookings> bookingsList = bookingRepository.findByUserUserNameAndPhotographerPhotographerID(userName, Long.valueOf(photographerID));
        List<BookingsDto> bookingsDtos = bookingsList.stream().filter(filterOnStatus).map(this::castBookingIntoBookingDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(bookingsDtos));
        return commonResponse;


    }

    @Override
    public CommonResponse getBookingAvailables(String photographerID, LocalDate dates) {
        CommonResponse commonResponse = new CommonResponse();



        BookingsDto  bookingsDto =new BookingsDto();

        Predicate<Bookings> filterOnStatus = bookings1  -> bookings1.getCommonStatus() == CommonStatus.ACTIVE;

        // Retrieve bookings for the photographer and filter them
        List<Bookings> bookingsList = bookingRepository.findByPhotographer_PhotographerIDAndDates(Long.valueOf(photographerID),dates);
        List<BookingsDto> bookingsDtos = bookingsList.stream().filter(filterOnStatus).map(this::castBookingIntoBookingDto).collect(Collectors.toList());


        commonResponse.setStatus(true);
        commonResponse.setPayload(Collections.singletonList(bookingsDtos));
        return commonResponse;
    }


    //--------------------------Save User ----------------------------------------------------
    @Override
    public CommonResponse saveBookings(BookingsDto bookingsDto) {
        CommonResponse commonResponse = new CommonResponse();

        try {
//            List<String> validationList = this.BookingsValidation(bookingsDto);
//            if (!validationList.isEmpty()) {
//                commonResponse.setErrorMessages(validationList);
//                return commonResponse;
//            }
           Bookings bookings = castBookingsDTOIntoBookings(bookingsDto);
            bookingRepository.save(bookings);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(bookings));


        } catch (Exception e) {
            LOGGER.error("/**************** Exception in UserService -> save()" + e);
        }
        return commonResponse;
    }

    public BookingsDto castBookingIntoBookingDto(Bookings bookings) {
        BookingsDto bookingsDto = new BookingsDto();
        bookingsDto.setBookingID(String.valueOf(bookings.getBookingID()));
        bookingsDto.setLocation(bookings.getLocation());
        bookingsDto.setDates(bookings.getDates());
        bookingsDto.setShootType(bookings.getShootType());
        bookingsDto.setShootTimeDuration(bookings.getShootTimeDuration());
        bookingsDto.setTellUsMoreDiscription(bookings.getTellUsMoreDiscription());
        bookingsDto.setCommonStatus(bookings.getCommonStatus());
        bookingsDto.setUserDto(userService.findById(bookings.getUser().getUserID()));
        bookingsDto.setPhotographerDto(photographerService.findByID(String.valueOf(bookings.getPhotographer().getPhotographerID())));

        return bookingsDto;

    }
    private List<String> BookingsValidation(BookingsDto bookingsDto) {

        List<String> validationList = new ArrayList<>();
        if (CommonValidation.stringNullValidation(bookingsDto.getShootType()))
            validationList.add(CommonMessage.SELECT_YOUR_SHOOT_TYPE);
        if (CommonValidation.stringNullValidation(bookingsDto.getLocation()))
            validationList.add(CommonMessage.EMPTY_LOCATION);
        if (CommonValidation.isValidLength(bookingsDto.getLocation()))
            validationList.add(CommonMessage.EMPTY_LOCATION);
        if (CommonValidation.stringNullValidation(String.valueOf(bookingsDto.getDates())))
            validationList.add(CommonMessage.EMPTY_DATE);
        if (CommonValidation.stringNullValidation(bookingsDto.getShootTimeDuration()))
            validationList.add(CommonMessage.SELECT_YOUR_SHOOT_TIME_DURATION);
        if (CommonValidation.isValidLengthDiscriptions(bookingsDto.getTellUsMoreDiscription()))
            validationList.add(CommonMessage.THREEHUNDRUND_CHARACHTERS_ONLY);
        return validationList;
    }

    private Bookings castBookingsDTOIntoBookings(BookingsDto bookingsDto) {
        Bookings bookings = new Bookings();
        bookings.setBookingID(Long.valueOf(bookingsDto.getBookingID()));
        bookings.setLocation(bookingsDto.getLocation());
        bookings.setShootType(bookingsDto.getShootType());
        bookings.setShootTimeDuration(bookingsDto.getShootTimeDuration());
        bookings.setDates(bookingsDto.getDates());
        bookings.setTellUsMoreDiscription(bookingsDto.getTellUsMoreDiscription());
        bookings.setCommonStatus(bookingsDto.getCommonStatus());
        bookings.setUser(userService.findByUserID(bookingsDto.getUserDto().getUserID()));
        bookings.setPhotographer(photographerService.findByPhotgrapherID(bookingsDto.getPhotographerDto().getPhotographerID()));

        return bookings;
    }
//--------------------------------Update Booking ---------------------------------
    @Override
    public CommonResponse updateBookings(BookingsDto bookingsDto) {
        CommonResponse commonResponse = new CommonResponse();
        Bookings bookings;
        try {
            List<String> validationList = this.BookingsValidation(bookingsDto);
            if (!validationList.isEmpty()) {
                commonResponse.setErrorMessages(validationList);
                return commonResponse;
            }

            System.out.println("bookingsDto.getPhotographerDto().getPhotographerID():: "+bookingsDto.getPhotographerDto().getPhotographerID());
            Bookings exsitsbookings = bookingRepository.findById(Long.valueOf(bookingsDto.getBookingID())).get();
            if (exsitsbookings != null) {
                exsitsbookings.setLocation(bookingsDto.getLocation());
                exsitsbookings.setShootType(bookingsDto.getShootType());
                exsitsbookings.setShootTimeDuration(bookingsDto.getShootTimeDuration());
                exsitsbookings.setDates(bookingsDto.getDates());
                exsitsbookings.setTellUsMoreDiscription(bookingsDto.getTellUsMoreDiscription());
                exsitsbookings.setCommonStatus(bookingsDto.getCommonStatus());
                exsitsbookings.setUser(userService.findByUserID(bookingsDto.getUserDto().getUserID()));
                exsitsbookings.setPhotographer(photographerService.findByPhotgrapherID(bookingsDto.getPhotographerDto().getPhotographerID()));

                bookingRepository.save(exsitsbookings);
                commonResponse.setStatus(true);
                commonResponse.setPayload(Collections.singletonList(exsitsbookings));
            } else {
                commonResponse.setErrorMessages(Collections.singletonList("User not found"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("/**************** Exception in BookingService -> update(booking)" + e);
            commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating bookings"));
        }
        return commonResponse;
    }
//--------------------------------- Serach booking Id ------------------------------
    @Override
    public CommonResponse getBookingByID(String bookingID) {
        CommonResponse commonResponse=new CommonResponse();
        Bookings bookings;
        BookingsDto bookingsDto =new BookingsDto();
        try {
            bookings = bookingRepository.findById(Long.valueOf(bookingID)).get();
            bookingsDto.setBookingID(String.valueOf(bookings.getBookingID()));
            bookingsDto.setLocation(bookings.getLocation());
            bookingsDto.setDates(bookings.getDates());
            bookingsDto.setShootType(bookings.getShootType());
            bookingsDto.setShootTimeDuration(bookings.getShootTimeDuration());
            bookingsDto.setTellUsMoreDiscription(bookings.getTellUsMoreDiscription());
            bookingsDto.setCommonStatus(bookings.getCommonStatus());

           bookingsDto.setUserDto(userService.findByUserID(bookings.getUser().getUserID()));
           String photographerID = String.valueOf(bookings.getPhotographer().getPhotographerID());
            bookingsDto.setPhotographerDto(photographerService.findByID(photographerID));

            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(bookingsDto));
        }
        catch (Exception e){
            LOGGER.error("/**************** Exception in UserService -> getUserById()"+e);
        }
        return commonResponse;
    }
//    -------------------------------------Ststus Update--------------------
@Override
public CommonResponse updateStatusConfirmBookings(String bookingID) {
    CommonResponse commonResponse = new CommonResponse();
    Bookings bookings;
    try {
        bookings= bookingRepository.findById(Long.valueOf(bookingID)).get();
        if (bookings != null) {
            bookings.setCommonStatus(CommonStatus.INACTIVE);
            bookingRepository.save(bookings);
            commonResponse.setStatus(true);
            commonResponse.setPayload(Collections.singletonList(bookings));
        } else {
            commonResponse.setErrorMessages(Collections.singletonList("User not found"));
        }

    } catch (Exception e) {
        LOGGER.error("/**************** Exception in BookingService -> update()" + e);
        commonResponse.setErrorMessages(Collections.singletonList("An error occurred while updating user"));
    }
    return commonResponse;
}

    @Override
    public Bookings findByBookingID(String bookingID) {
           return bookingRepository.getById(Long.valueOf(bookingID));

    }


    }


