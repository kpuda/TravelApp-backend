package com.travelapp.backend.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationObject {
    private int statusCode;
    private String accessToken;
    private String userId;
}
