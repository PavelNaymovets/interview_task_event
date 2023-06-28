package com.interviewTask.event.service.eventMember.converter;

import com.interviewTask.event.dto.eventMember.EventMemberDto;
import com.interviewTask.event.model.eventMember.EventMember;
import com.interviewTask.event.util.converter.Converter;

public interface EventMemberConverter extends Converter<EventMember, EventMemberDto> {
    @Override
    EventMember convertToEntity(EventMemberDto eventMemberDto);

    @Override
    EventMemberDto convertToDto(EventMember eventMember);
}
