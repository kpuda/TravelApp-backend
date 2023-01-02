package com.travelapp.backend.entity.dtos;

import com.travelapp.backend.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsletterDto {

    private Long id;
    private String language;
    private String name;
    private long phoneNumber;
    private String description;
    private UserRole role;

    public NewsletterDto(String language, String name, long phoneNumber, String description, UserRole role) {
        this.language = language;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.role = role;
    }
}
