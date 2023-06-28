package com.interviewTask.event.service.event.converter;

import com.interviewTask.event.dto.event.EventDto;
import com.interviewTask.event.exception.ResourceNotFoundException;
import com.interviewTask.event.model.event.Event;
import com.interviewTask.event.service.contract.converter.ContractConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class EventConverterImpl implements EventConverter {
    private final ContractConverter contractConverter;
    private final MessageSource messageSource;

    @Override
    public Event convertToEntity(EventDto eventDto) {
        if (ObjectUtils.isEmpty(eventDto)) {
            throw new ResourceNotFoundException(
                    messageSource.getMessage("eventDto.null", null, Locale.ENGLISH));
        }

        return Event.builder()
                .id(eventDto.getId())
                .contract(ObjectUtils.isEmpty(eventDto.getContract()) ? null : contractConverter.convertToEntity(eventDto.getContract()))
                .name(eventDto.getName())
                .cost(eventDto.getCost())
                .status(eventDto.getStatus())
                .build();
    }

    @Override
    public EventDto convertToDto(Event event) {
        if (ObjectUtils.isEmpty(event)) {
            throw new ResourceNotFoundException(
                    messageSource.getMessage("event.null", null, Locale.ENGLISH));
        }

        return EventDto.builder()
                .id(event.getId())
                .contract(ObjectUtils.isEmpty(event.getContract()) ? null : contractConverter.convertToDto(event.getContract()))
                .name(event.getName())
                .cost(event.getCost())
                .status(event.getStatus())
                .build();
    }
}
