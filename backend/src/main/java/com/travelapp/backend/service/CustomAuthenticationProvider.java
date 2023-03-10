package com.travelapp.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.travelapp.backend.tools.ConstStrings.BAD_CREDENTIALS;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = userService.loadUserByUsername(authentication.getName());
        if (userDetails != null && passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials().toString(), userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException(BAD_CREDENTIALS.name());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
