package com.travelapp.backend.responses;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponseObject {

    private int statusCode;
    private String message;
    private String date;
}
