package com.interviewTask.event.service.user;

import com.interviewTask.event.dto.user.UserDto;
import com.interviewTask.event.exception.ResourceNotFoundException;
import com.interviewTask.event.exception.ValidationException;
import com.interviewTask.event.model.role.Role;
import com.interviewTask.event.model.user.User;
import com.interviewTask.event.repository.role.RoleRepository;
import com.interviewTask.event.repository.user.UserRepository;
import com.interviewTask.event.service.user.converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Locale;

import static com.interviewTask.event.model.user.UserStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger serviceLog = LoggerFactory.getLogger("service-log");
    private static final String PREFIX = "ROLE_";
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @Override
    public UserDto create(UserDto userDto) {
        validate(userDto);
        User user = userConverter.convertToEntity(userDto);
        Role role = roleRepository.findByName(PREFIX + userDto.getRole().toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("role.not.correct", null, Locale.ENGLISH)));
        user.setLogin(userDto.getLogin());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(List.of(role));
        user.setStatus(ACTIVE.getStatus());
        User createdUser = userRepository.save(user);
        serviceLog.info(
                messageSource.getMessage("user.created", null, Locale.ENGLISH) + userDto.getLogin());

        return userConverter.convertToDto(createdUser);
    }

    private void validate(UserDto userDto) {
        if (ObjectUtils.isEmpty(userDto)) {
            throw new ValidationException(
                    messageSource.getMessage("userDto.null", null, Locale.ENGLISH));
        }

        if (!ObjectUtils.isEmpty(userDto.getId())) {
            throw new ValidationException(
                    messageSource.getMessage("userDto.field.id.autofill", null, Locale.ENGLISH));
        }

        if (!ObjectUtils.isEmpty(userDto.getStatus())) {
            throw new ValidationException(
                    messageSource.getMessage("userDto.field.status.autofill", null, Locale.ENGLISH));
        }

        if (ObjectUtils.isEmpty(userDto.getLastName()) || userDto.getLastName().isBlank()) {
            throw new ValidationException(
                    messageSource.getMessage("userDto.field.lastName.not.filled", null, Locale.ENGLISH));
        }

        if (ObjectUtils.isEmpty(userDto.getFirstName()) || userDto.getFirstName().isBlank()) {
            throw new ValidationException(
                    messageSource.getMessage("userDto.field.firstName.not.filled", null, Locale.ENGLISH));
        }

        if (ObjectUtils.isEmpty(userDto.getAge())) {
            throw new ValidationException(
                    messageSource.getMessage("userDto.field.age.not.filled", null, Locale.ENGLISH));
        }

        if (ObjectUtils.isEmpty(userDto.getLogin()) || userDto.getLogin().isBlank()) {
            throw new ValidationException(
                    messageSource.getMessage("userDto.field.login.not.filled", null, Locale.ENGLISH));
        }

        if (ObjectUtils.isEmpty(userDto.getPassword()) || userDto.getPassword().isBlank()) {
            throw new ValidationException(
                    messageSource.getMessage("userDto.field.password.not.filled", null, Locale.ENGLISH));
        }
    }
}
