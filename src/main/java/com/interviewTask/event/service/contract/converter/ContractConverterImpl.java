package com.interviewTask.event.service.contract.converter;

import com.interviewTask.event.dto.contract.ContractDto;
import com.interviewTask.event.exception.ResourceNotFoundException;
import com.interviewTask.event.model.contract.Contract;
import com.interviewTask.event.service.user.converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ContractConverterImpl implements ContractConverter {
    private final UserConverter userConverter;
    private final MessageSource messageSource;

    @Override
    public Contract convertToEntity(ContractDto contractDto) {
        if (ObjectUtils.isEmpty(contractDto)) {
            throw new ResourceNotFoundException(
                    messageSource.getMessage("contractDto.null", null, Locale.ENGLISH));
        }

        return Contract.builder()
                .id(contractDto.getId())
                .companyName(contractDto.getCompanyName())
                .eventName(contractDto.getEventName())
                .admin(ObjectUtils.isEmpty(contractDto.getAdmin()) ? null : userConverter.convertToEntity(contractDto.getAdmin()))
                .status(contractDto.getStatus())
                .build();
    }

    @Override
    public ContractDto convertToDto(Contract contract) {
        if (ObjectUtils.isEmpty(contract)) {
            throw new ResourceNotFoundException(
                    messageSource.getMessage("contract.null", null, Locale.ENGLISH));
        }

        return ContractDto.builder()
                .id(contract.getId())
                .companyName(contract.getCompanyName())
                .eventName(contract.getEventName())
                .admin(ObjectUtils.isEmpty(contract.getAdmin()) ? null : userConverter.convertToDto(contract.getAdmin()))
                .status(contract.getStatus())
                .build();
    }
}
