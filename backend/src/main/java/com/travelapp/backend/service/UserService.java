package com.travelapp.backend.service;


import com.travelapp.backend.entity.Token;
import com.travelapp.backend.entity.User;
import com.travelapp.backend.entity.dtos.UserDto;
import com.travelapp.backend.enums.TokenType;
import com.travelapp.backend.enums.UserRole;
import com.travelapp.backend.model.PasswordModel;
import com.travelapp.backend.repository.RoleRepository;
import com.travelapp.backend.repository.TokenRepository;
import com.travelapp.backend.repository.UserRepository;
import com.travelapp.backend.responses.ResponseObject;
import com.travelapp.backend.responses.WrapperListObject;
import com.travelapp.backend.tools.ConstStrings;
import com.travelapp.backend.tools.Mapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.travelapp.backend.tools.ConstStrings.*;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final Mapper mapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, TokenRepository tokenRepository, @Lazy PasswordEncoder passwordEncoder, Mapper mapper) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Transactional
    public ResponseObject register(UserDto userDto) {
        if (userRepository.existsByUsernameOrEmail(userDto.getUsername(), userDto.getEmail())) {
            throw new EntityExistsException("Username or email used already");
        }
        User user = mapper.mapToUser(userDto);
        user.addRole(roleRepository.findByName(UserRole.ROLE_USER.name()).get());
        String token = UUID.randomUUID().toString();
        Token verificationToken = new Token(token, TokenType.NEW_ACCOUNT_VERIFICATION, userDto.getEmail());
        userRepository.save(user);
        tokenRepository.save(verificationToken);
        return new ResponseObject(HttpStatus.CREATED.value(), verificationToken.getToken()); //TODO send email
    }

    @Transactional
    public ResponseObject verifyRegistration(String verificationToken) {
        Token token = tokenRepository.findByToken(verificationToken);
        validateToken(token, TokenType.NEW_ACCOUNT_VERIFICATION);
        User user = userRepository.findByEmail(token.getUserEmail());
        if (user.isEnabled()) {
            throw new CredentialsExpiredException(USER_VERIFIED_ALREADY.name());
        }
        token.setTokenUsed(true);
        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.save(token);
        return new ResponseObject(HttpStatus.OK.value(), USER_VERIFIED.name());
    }

    @Transactional
    public ResponseObject changePassword(PasswordModel passwordModel, HttpServletRequest request) {
        User user = userRepository.findByEmail(passwordModel.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException(EMAIL_NOT_FOUND.name());
        }
        if (!checkIfOldPasswordIsValid(passwordModel.getPassword(), user)) {
            throw new BadCredentialsException(PASSWORD_OLD_INCORRECT.name());
        }
        user.setPassword(passwordModel.getNewPassword());
        userRepository.save(user);
        return new ResponseObject(HttpStatus.OK.value(), PASSWORD_CHANGED.name());
    }

    @Transactional
    public ResponseObject resetPassword(PasswordModel passwordModel, HttpServletRequest request) {
        User user = userRepository.findByEmail(passwordModel.getEmail());
        String url;
        if (user == null) {
            throw new EntityNotFoundException(USER_NOT_FOUND.name());
        } else {
            String token = UUID.randomUUID().toString();
            Token resetPasswordToken = new Token(token, TokenType.FORGOT_PASSWORD, user.getEmail());
            url = generateChangePasswordTokenUrl(generateUrl(request), resetPasswordToken);
            tokenRepository.save(resetPasswordToken);
            log.info("Url: {}", url);
            log.info("Token: {}", token);
        }
        return new ResponseObject(HttpStatus.OK.value(), TOKEN_SENT.name());//TODO sendEmailWithResetPasswordToken(user,url);
    }

    @Transactional
    public ResponseObject savePassword(String token, PasswordModel passwordModel) {
        Token changePasswordToken = tokenRepository.findByToken(token);
        validateToken(changePasswordToken, TokenType.FORGOT_PASSWORD);
        User user = userRepository.findByEmail(changePasswordToken.getUserEmail());
        changePasswordToken.setTokenUsed(true);
        user.setPassword(passwordEncoder.encode(passwordModel.getNewPassword()));
        userRepository.save(user);
        tokenRepository.save(changePasswordToken);
        return new ResponseObject(HttpStatus.OK.value(), PASSWORD_CHANGED.name());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        if (username.contains("@")) {
            user = userRepository.findByEmail(username);
            log.info("User is loaded by email");
        } else {
            user = userRepository.findByUsername(username);
            log.info("User is loaded by username");
        }
        if (user != null) {
            if (!user.isEnabled()) {
                log.info("User disabled");
                throw new BadCredentialsException("User disabled");
            }
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        } else {
            log.info("User is empty");
            throw new BadCredentialsException(ConstStrings.BAD_CREDENTIALS.name());
        }
    }


    private String generateVerificationTokenUrl(String applicationUrl, Token token) {
        return applicationUrl +
                "/verifyRegistration?token=" +
                token.getToken();
    }

    private String generateUrl(HttpServletRequest request) {
        return "http://"
                + request.getServerName() + ":" +
                request.getServerPort() +
                request.getContextPath();
    }

    private String generateChangePasswordTokenUrl(String generateUrl, Token token) {
        return generateUrl
                + "/savePassword?token="
                + token.getToken();
    }

    private void validateToken(Token token, TokenType tokenType) {
        if (token == null) {
            throw new EntityNotFoundException(TOKEN_INVALID.name());
        }
        if (token.getExpirationDate().getTime() < new Date().getTime()) {
            throw new CredentialsExpiredException(TOKEN_EXPIRED.name());
        } else if (token.isTokenUsed()) {
            throw new CredentialsExpiredException(TOKEN_USED.name());
        }
        if (!token.getTokenType().equals(tokenType)) {
            throw new CredentialsExpiredException(TOKEN_INVALID.name());
        }
    }


    private boolean checkIfOldPasswordIsValid(String oldPassword, User user) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public WrapperListObject getUsers() {
        List<String> users=userRepository.getUsernames();
        return new WrapperListObject(HttpStatus.OK.value(), users );
    }
}