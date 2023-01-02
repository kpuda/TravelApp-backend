package com.travelapp.backend.controllers;

import com.travelapp.backend.entity.User;
import com.travelapp.backend.entity.dtos.UserDto;
import com.travelapp.backend.model.PasswordModel;
import com.travelapp.backend.repository.UserRepository;
import com.travelapp.backend.responses.ResponseObject;
import com.travelapp.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/hello")
    public String hello() {
        System.out.println("XD");
        return "hello";
    }

    @PostMapping("/register")
    public ResponseObject register(@RequestBody UserDto userDto) {
        return userService.register(userDto);
    }

    @PostMapping("/verify")
    public ResponseObject verify(@RequestParam("token") String token) {
        return userService.verifyRegistration(token);
    }

    @PostMapping("/password")
    public ResponseObject changePassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {
        return userService.changePassword(passwordModel, request);
    }


    private final UserRepository userRepository;

    @GetMapping("/all") //TODO delete
    public List<User> getUsers() {
        List<User> all = userRepository.findAll();
        return all;
    }

}
