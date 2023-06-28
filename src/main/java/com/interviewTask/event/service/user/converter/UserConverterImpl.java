package com.interviewTask.event.service.user.converter;

import com.interviewTask.event.dto.user.UserDto;
import com.interviewTask.event.exception.ResourceNotFoundException;
import com.interviewTask.event.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class UserConverterImpl implements UserConverter {
    private final MessageSource messageSource;

    @Override
    public User convertToEntity(UserDto userDto) {
        if (ObjectUtils.isEmpty(userDto)) {
            throw new ResourceNotFoundException(
                    messageSource.getMessage("userDto.null", null, Locale.ENGLISH));
        }

        return User.builder()
                .id(userDto.getId())
                .lastName(userDto.getLastName())
                .firstName(userDto.getFirstName())
                .middleName(userDto.getMiddleName())
                .age(userDto.getAge())
                .status(userDto.getStatus())
                .build();
    }

    @Override
    public UserDto convertToDto(User user) {
        if (ObjectUtils.isEmpty(user)) {
            throw new ResourceNotFoundException(
                    messageSource.getMessage("user.null", null, Locale.ENGLISH));
        }

        return UserDto.builder()
                .id(user.getId())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .age(user.getAge())
                .status(user.getStatus())
                .build();
    }
}
