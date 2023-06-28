package com.interviewTask.event.service.event.converter;

import com.interviewTask.event.dto.event.EventDto;
import com.interviewTask.event.model.event.Event;
import com.interviewTask.event.util.converter.Converter;

public interface EventConverter extends Converter<Event, EventDto> {
    @Override
    Event convertToEntity(EventDto eventDto);

    @Override
    EventDto convertToDto(Event event);
}
