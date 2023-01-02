package com.travelapp.backend.controllers;

import com.travelapp.backend.dto.AuthenticationRequest;
import com.travelapp.backend.responses.AuthenticationObject;
import com.travelapp.backend.responses.ResponseObject;
import com.travelapp.backend.responses.TokenResponse;
import com.travelapp.backend.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/authorization")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationObject authenticateUser(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        return authenticationService.authenticate(request, response);
    }

    @PostMapping("/refresh") //TODO
    public AuthenticationObject refreshToken(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        return authenticationService.authenticate(request, response);
    }
}
