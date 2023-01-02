package com.travelapp.backend.model;

import lombok.Getter;

@Getter
public class NewsletterModel {

    private long id;
    private String language;
    private String name;
    private long phoneNumber;
    private String description;

    public NewsletterModel(String language, String name, long phoneNumber, String description) {
        this.language = language;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }
}
