package com.example.Ceylone.Snippers.Back.End.repository;

import com.example.Ceylone.Snippers.Back.End.constant.CommonStatus;
import com.example.Ceylone.Snippers.Back.End.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface BookingRepository extends JpaRepository <Bookings,Long> {


  List<Bookings> findByPhotographer_PhotographerIDAndDates(Long photographer_photographerID, LocalDate Dates);


  List<Bookings> findByUserUserIDAndPhotographer_PhotographerID(Long userID, Long photographerID);

  List<Bookings> findByUserUserNameAndPhotographerPhotographerID(String userName, Long photographerID);

  List<Bookings> findByPhotographer_PhotographerID(Long aLong);

    List<Object> findByBookingID(Long BookingID);
  List<Bookings> findByPhotographer_PhotographerIDAndUserUserIDOrUser_UserID(String photographerID, String userID, String userName);

}
