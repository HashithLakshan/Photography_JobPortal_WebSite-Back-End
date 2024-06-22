package com.example.Ceylone.Snippers.Back.End.controller;

import com.example.Ceylone.Snippers.Back.End.dto.UserDto;
import com.example.Ceylone.Snippers.Back.End.services.CloudinaryService;
import com.example.Ceylone.Snippers.Back.End.services.UserService;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("api/user")
@CrossOrigin
class UserController {


    private final UserService userService;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/getAllUser")
    public CommonResponse getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/saveUser")
    public CommonResponse saveUser(@RequestBody UserDto userDto) {
      return userService.saveUser(userDto);
    }

    @PutMapping("/updateUser")
    public CommonResponse updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @GetMapping("/getUser/{userID}")
    public CommonResponse getUserByID(@PathVariable String userID) {
        return userService.getUserByID(userID);
    }

    @GetMapping("/getUserName/{userName}")
    public CommonResponse getUserByName(@PathVariable String userName) {
        return userService.getUserByName(userName);
    }

    @PutMapping("/updateUser/DeleteInactive/{userID}")
    public CommonResponse updateUserInCommonStatusInactive(@PathVariable String userID) {
        return userService.updateUserInCommonStatusInactive(userID);
    }
    @PutMapping("/updateUser/permenatDelete/{userID}")
    public CommonResponse updateUserInCommonStatusDelete(@PathVariable String userID) {
        return userService.updateUserInCommonStatusDelete(userID);
    }
    @PutMapping("/updateUser/recoveryActive/{userID}")
    public CommonResponse updateUserInCommonStatusActive(@PathVariable String userID) {
        return userService.updateUserInCommonStatusActive(userID);
    }

    @PostMapping("/uploadImages")
    public ResponseEntity<?> upload(@RequestPart("image1") MultipartFile image1, @RequestParam("userID") String userID) throws IOException {
        return cloudinaryService.upload(image1, userID);
    }
}

