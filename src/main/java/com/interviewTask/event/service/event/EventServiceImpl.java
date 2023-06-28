package com.interviewTask.event.service.event;

import com.interviewTask.event.dto.event.EventDto;
import com.interviewTask.event.exception.ResourceNotFoundException;
import com.interviewTask.event.exception.ValidationException;
import com.interviewTask.event.model.contract.Contract;
import com.interviewTask.event.model.event.Event;
import com.interviewTask.event.model.event.EventStatus;
import com.interviewTask.event.model.user.User;
import com.interviewTask.event.repository.contract.ContractRepository;
import com.interviewTask.event.repository.event.EventRepository;
import com.interviewTask.event.service.event.converter.EventConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Locale;

import static com.interviewTask.event.model.contract.ContractStatus.UNSIGNED;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private static final Logger serviceLog = LoggerFactory.getLogger("service-log");
    private final EventRepository eventRepository;
    private final EventConverter eventConverter;
    private final ContractRepository contractRepository;
    private final MessageSource messageSource;

    @Override
    public EventDto create(EventDto eventDto) {
        validate(eventDto);

        if (ObjectUtils.isEmpty(eventDto.getContract().getId())) {
            throw new ValidationException(
                    messageSource.getMessage("event.contract.don`t.have.id", null, Locale.ENGLISH));
        }

        Long contractId = eventDto.getContract().getId();

        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("contract.not.found.by.id", null, Locale.ENGLISH) + contractId));
        User admin = contract.getAdmin();
        String login = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!admin.getLogin().equalsIgnoreCase(login)) {
            throw new ValidationException(
                    messageSource.getMessage("contract.not.equals.login", null, Locale.ENGLISH) + contractId);
        }

        if (contract.getStatus().equalsIgnoreCase(UNSIGNED.getStatus())) {
            throw new ValidationException(
                    messageSource.getMessage("contract.is.unsigned", null, Locale.ENGLISH) + contractId);
        }

        Event event = eventConverter.convertToEntity(eventDto);
        event.setStatus(EventStatus.AWAITS.getStatus());
        Event eventCreated = eventRepository.save(event);
        serviceLog.info(
                messageSource.getMessage("event.created", null, Locale.ENGLISH) + event.getId());

        return eventConverter.convertToDto(eventCreated);
    }

    private void validate(EventDto eventDto) {
        if (ObjectUtils.isEmpty(eventDto)) {
            throw new ValidationException(
                    messageSource.getMessage("eventDto.null", null, Locale.ENGLISH));
        }

        if (!ObjectUtils.isEmpty(eventDto.getId())) {
            throw new ValidationException(
                    messageSource.getMessage("eventDto.field.id.autofill", null, Locale.ENGLISH));
        }

        if (!ObjectUtils.isEmpty(eventDto.getStatus())) {
            throw new ValidationException(
                    messageSource.getMessage("eventDto.field.status.autofill", null, Locale.ENGLISH));
        }

        if (ObjectUtils.isEmpty(eventDto.getName()) || eventDto.getName().isBlank()) {
            throw new ValidationException(
                    messageSource.getMessage("eventDto.field.name.not.filled", null, Locale.ENGLISH));
        }

        if (ObjectUtils.isEmpty(eventDto.getCost())) {
            throw new ValidationException(
                    messageSource.getMessage("eventDto.field.cost.not.filled", null, Locale.ENGLISH));
        }

        if (ObjectUtils.isEmpty(eventDto.getContract())) {
            throw new ValidationException(
                    messageSource.getMessage("eventDto.field.contract.not.filled", null, Locale.ENGLISH));
        } else if (ObjectUtils.isEmpty(eventDto.getContract().getId())) {
            throw new ValidationException(
                    messageSource.getMessage("eventDto.don`t.have.contract", null, Locale.ENGLISH));
        }
    }
}
