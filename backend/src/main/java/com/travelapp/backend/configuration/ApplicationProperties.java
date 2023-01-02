package com.travelapp.backend.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApplicationProperties {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;
    @Value("${jwt.secret.issuer}")
    private String jwtSecretIssuer;
}
