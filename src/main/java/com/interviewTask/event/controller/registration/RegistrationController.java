package com.interviewTask.event.controller.registration;

import com.interviewTask.event.auth.util.AuthUserService;
import com.interviewTask.event.auth.util.JwtTokenUtil;
import com.interviewTask.event.dto.jwt.JwtResponseDto;
import com.interviewTask.event.dto.user.UserDto;
import com.interviewTask.event.exception.AppError;
import com.interviewTask.event.repository.user.UserRepository;
import com.interviewTask.event.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private static final Logger authenticationLog = LoggerFactory.getLogger("auth-log");
    private final AuthUserService authUserService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final MessageSource messageSource;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        if (userRepository.findByLogin(userDto.getLogin()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    messageSource.getMessage(
                            "authentication.login.exist", null, Locale.ENGLISH)),
                    HttpStatus.BAD_REQUEST);
        }

        userService.create(userDto);
        UserDetails userDetails = authUserService.loadUserByUsername(userDto.getLogin());
        String token = jwtTokenUtil.generateToken(userDetails);
        authenticationLog.info(messageSource.getMessage(
                "user.successfully.registered", null, Locale.ENGLISH) + userDto.getLogin());

        return ResponseEntity.ok(new JwtResponseDto(token));
    }
}
