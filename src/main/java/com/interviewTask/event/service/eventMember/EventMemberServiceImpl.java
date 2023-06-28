package com.interviewTask.event.service.eventMember;

import com.interviewTask.event.dto.eventMember.EventMemberDto;
import com.interviewTask.event.exception.ResourceNotFoundException;
import com.interviewTask.event.exception.ValidationException;
import com.interviewTask.event.model.eventMember.EventMember;
import com.interviewTask.event.model.user.User;
import com.interviewTask.event.repository.eventMember.EventMemberRepository;
import com.interviewTask.event.repository.user.UserRepository;
import com.interviewTask.event.service.eventMember.converter.EventMemberConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Locale;

import static com.interviewTask.event.model.eventMember.EventMemberStatus.*;

@Service
@RequiredArgsConstructor
public class EventMemberServiceImpl implements EventMemberService {
    private final EventMemberRepository eventMemberRepository;
    private final EventMemberConverter eventMemberConverter;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    public EventMemberDto signUp(EventMemberDto eventMemberDto) {
        validate(eventMemberDto);
        String login = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User member = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("user.not.found.by.login", null, Locale.ENGLISH) + login));
        EventMember eventMember = eventMemberConverter.convertToEntity(eventMemberDto);
        eventMember.setUser(member);

        if (eventMemberDto.getPcrTest().equalsIgnoreCase(POSITIVE.getStatus())) {
            eventMember.setStatus(NOT_MEMBER.getStatus());
        } else {
            eventMember.setStatus(MEMBER.getStatus());
        }

        EventMember memberCreated = eventMemberRepository.save(eventMember);

        return eventMemberConverter.convertToDto(memberCreated);
    }

    private void validate(EventMemberDto eventMemberDto) {
        if (ObjectUtils.isEmpty(eventMemberDto)) {
            throw new ValidationException(
                    messageSource.getMessage("eventMemberDto.null", null, Locale.ENGLISH));
        }

        if (!ObjectUtils.isEmpty(eventMemberDto.getId())) {
            throw new ValidationException(
                    messageSource.getMessage("eventMemberDto.field.id.autofill", null, Locale.ENGLISH));
        }

        if (!ObjectUtils.isEmpty(eventMemberDto.getStatus())) {
            throw new ValidationException(
                    messageSource.getMessage("eventMemberDto.field.status.autofill", null, Locale.ENGLISH));
        }

        if (!ObjectUtils.isEmpty(eventMemberDto.getUser())) {
            throw new ValidationException(
                    messageSource.getMessage("eventMemberDto.field.user.autofill", null, Locale.ENGLISH));
        }

        if (ObjectUtils.isEmpty(eventMemberDto.getEvent())) {
            throw new ValidationException(
                    messageSource.getMessage("eventMemberDto.field.event.not.filled", null, Locale.ENGLISH));
        } else if (ObjectUtils.isEmpty(eventMemberDto.getEvent().getId())) {
            throw new ValidationException(
                    messageSource.getMessage("event.member.don`t.have.event", null, Locale.ENGLISH));
        }

        if (ObjectUtils.isEmpty(eventMemberDto.getPcrTest()) || eventMemberDto.getPcrTest().isBlank()) {
            throw new ValidationException(
                    messageSource.getMessage("event.member.don`t.have.pcr", null, Locale.ENGLISH));
        }
    }
}
