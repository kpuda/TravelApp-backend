package com.travelapp.backend.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationObject {
    private int statusCode;
    private String username;
    private String accessToken;
    private String refreshToken;
    private List<String> roles = new ArrayList<>();

    public AuthenticationObject(String username, String accessToken, String refreshToken, List<String> roles) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.roles = roles;
    }
}
