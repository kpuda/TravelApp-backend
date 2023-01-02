package com.travelapp.backend.tools;

public enum ConstStrings {

    USER_ADDED_SUCCESSFULLY("User added successfully"),
    USERNAME_EMAIL_ALREADY_TAKEN("Username or email already taken"),
    BAD_CREDENTIALS("Bad credentials for given username or user doesn't exists"),
    TOKEN_INVALID("TOKEN_INVALID"),
    TOKEN_EXPIRED("TOKEN_EXPIRED"),
    TOKEN_USED("TOKEN_USED"),
    USER_VERIFIED_ALREADY("USER_VERIFIED_ALREADY"),
    USER_VERIFIED("USER_VERIFIED"),
    PASSWORD_CHANGED("PASSWORD_CHANGED"),
    PASSWORD_OLD_INCORRECT("PASSWORD_OLD_INCORRECT"),
    EMAIL_NOT_FOUND("EMAIL_NOT_FOUND"),
    TOKEN_NOT_FOUND("TOKEN_NOT_FOUND"),
    TOKEN_SENT("TOKEN_SENT"),
    USER_NOT_FOUND("USER_NOT_FOUND"),
    NEWSLETTER_ADD("NEWSLETTER_ADD");
    private final String name;

    ConstStrings(String name) {
        this.name = name;
    }


}
