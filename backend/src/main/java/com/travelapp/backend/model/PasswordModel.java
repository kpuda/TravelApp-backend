package com.travelapp.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PasswordModel {
    private String email;
    private String password;
    private String newPassword;
}
