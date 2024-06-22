package com.example.Ceylone.Snippers.Back.End.repository;

import com.example.Ceylone.Snippers.Back.End.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UserRepository  extends JpaRepository<User,Long> {

    User findByUserID(Long userID);


    User getByUserName(String userName);

    @Query(value = "SELECT * FROM user_registraion where user_name =?1", nativeQuery = true)
    Optional<User> getByUserName2(String Id);

    @Query(value = "SELECT MAX(user_id) AS latest_id FROM user_registraion", nativeQuery = true)
    String lastID();
    @Query(value = "SELECT * FROM user_registraion where id = ?1", nativeQuery = true)
    Optional<User> checkIsUserPresent(String Id);


    User findByUserNameAndPassword(String userName, String passwod);
}
