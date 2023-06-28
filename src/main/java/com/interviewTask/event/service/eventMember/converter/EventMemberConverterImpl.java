package com.interviewTask.event.service.eventMember.converter;

import com.interviewTask.event.dto.eventMember.EventMemberDto;
import com.interviewTask.event.exception.ResourceNotFoundException;
import com.interviewTask.event.model.eventMember.EventMember;
import com.interviewTask.event.service.event.converter.EventConverter;
import com.interviewTask.event.service.user.converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class EventMemberConverterImpl implements EventMemberConverter {
    private final EventConverter eventConverter;
    private final UserConverter userConverter;
    private final MessageSource messageSource;


    @Override
    public EventMember convertToEntity(EventMemberDto eventMemberDto) {
        if (ObjectUtils.isEmpty(eventMemberDto)) {
            throw new ResourceNotFoundException(
                    messageSource.getMessage("eventMemberDto.null", null, Locale.ENGLISH));
        }

        return EventMember.builder()
                .id(eventMemberDto.getId())
                .event(ObjectUtils.isEmpty(eventMemberDto.getEvent()) ? null : eventConverter.convertToEntity(eventMemberDto.getEvent()))
                .user(ObjectUtils.isEmpty(eventMemberDto.getUser()) ? null : userConverter.convertToEntity(eventMemberDto.getUser()))
                .pcrTest(eventMemberDto.getPcrTest())
                .status(eventMemberDto.getStatus())
                .build();
    }

    @Override
    public EventMemberDto convertToDto(EventMember eventMember) {
        if (ObjectUtils.isEmpty(eventMember)) {
            throw new ResourceNotFoundException(
                    messageSource.getMessage("eventMember.null", null, Locale.ENGLISH));
        }

        return EventMemberDto.builder()
                .id(eventMember.getId())
                .event(ObjectUtils.isEmpty(eventMember.getEvent()) ? null : eventConverter.convertToDto(eventMember.getEvent()))
                .user(ObjectUtils.isEmpty(eventMember.getUser()) ? null : userConverter.convertToDto(eventMember.getUser()))
                .pcrTest(eventMember.getPcrTest())
                .status(eventMember.getStatus())
                .build();
    }
}
