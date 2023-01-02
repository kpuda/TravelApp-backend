package com.travelapp.backend.service;

import com.travelapp.backend.dto.AuthenticationRequest;
import com.travelapp.backend.responses.AuthenticationObject;
import com.travelapp.backend.responses.ResponseObject;
import com.travelapp.backend.tools.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthenticationObject authenticate(AuthenticationRequest request, HttpServletResponse response) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())); //TODO custom auth manager?
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtUtils.setHttpHeaders(authenticate, response);
        Object credentials = authenticate.getCredentials();
        return new AuthenticationObject(HttpStatus.OK.value(), token, "xd");
    }
}
