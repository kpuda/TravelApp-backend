package com.travelapp.backend.entity;

import com.travelapp.backend.enums.TokenType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Token {

    private static final int EXPIRATION_TIME = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationDate;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private boolean isTokenUsed = false;
    private String userEmail;

    public Token(String token, TokenType tokenType) {
        this.token = token;
        this.tokenType = tokenType;
        this.expirationDate = generateExpirationDate(EXPIRATION_TIME);
    }

    public Token(String token, TokenType tokenType, String userEmail) {
        this.token = token;
        this.expirationDate = generateExpirationDate(EXPIRATION_TIME);
        this.tokenType = tokenType;
        this.userEmail = userEmail;
    }


    private Date generateExpirationDate(int expirationDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationDate);
        return new Date(calendar.getTime().getTime());
    }
}
