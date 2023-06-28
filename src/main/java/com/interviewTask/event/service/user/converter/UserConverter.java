package com.interviewTask.event.service.user.converter;

import com.interviewTask.event.dto.user.UserDto;
import com.interviewTask.event.model.user.User;
import com.interviewTask.event.util.converter.Converter;

public interface UserConverter extends Converter<User, UserDto> {
    @Override
    User convertToEntity(UserDto userDto);

    @Override
    UserDto convertToDto(User user);
}
