package com.interviewTask.event.controller.auth;

import com.interviewTask.event.auth.util.AuthUserService;
import com.interviewTask.event.auth.util.JwtTokenUtil;
import com.interviewTask.event.dto.jwt.JwtRequestDto;
import com.interviewTask.event.dto.jwt.JwtResponseDto;
import com.interviewTask.event.exception.AppError;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger authenticationLog = LoggerFactory.getLogger("auth-log");
    private final AuthUserService authUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final MessageSource messageSource;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            authenticationLog.info(e.getMessage(), e);

            return new ResponseEntity<>(
                    new AppError(HttpStatus.UNAUTHORIZED.value(),
                            messageSource.getMessage(
                                    "authentication.login.password.incorrect", null, Locale.ENGLISH)),
                    HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = authUserService.loadUserByUsername(authRequest.getLogin());
        String token = jwtTokenUtil.generateToken(userDetails);
        authenticationLog.info(
                messageSource.getMessage(
                        "authentication.token.created", null, Locale.ENGLISH) + authRequest.getLogin());

        return ResponseEntity.ok(new JwtResponseDto(token));
    }
}
