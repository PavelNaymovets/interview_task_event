package com.interviewTask.event.service.contract;

import com.interviewTask.event.dto.contract.ContractDto;
import com.interviewTask.event.exception.ResourceNotFoundException;
import com.interviewTask.event.exception.ValidationException;
import com.interviewTask.event.model.contract.Contract;
import com.interviewTask.event.model.user.User;
import com.interviewTask.event.repository.contract.ContractRepository;
import com.interviewTask.event.repository.user.UserRepository;
import com.interviewTask.event.service.contract.converter.ContractConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Locale;

import static com.interviewTask.event.model.contract.ContractStatus.SIGNED;
import static com.interviewTask.event.model.contract.ContractStatus.UNSIGNED;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {
    private static final Logger serviceLog = LoggerFactory.getLogger("service-log");
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final ContractConverter contractConverter;
    private final MessageSource messageSource;

    @Override
    public ContractDto liveRequest(ContractDto contractDto) {
        validate(contractDto);
        Contract contract = contractConverter.convertToEntity(contractDto);
        String login = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User admin = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("user.not.found.by.login", null, Locale.ENGLISH) + login));
        contract.setStatus(UNSIGNED.getStatus());
        contract.setAdmin(admin);
        Contract createdContract = contractRepository.save(contract);
        serviceLog.info(
                messageSource.getMessage("contract.created", null, Locale.ENGLISH) + createdContract.getId());

        return contractConverter.convertToDto(createdContract);
    }

    @Override
    public ContractDto signOn(Long id) {
        Contract contractUnsigned = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("contract.not.found.by.id", null, Locale.ENGLISH) + id));
        if (contractUnsigned.getStatus().equalsIgnoreCase(SIGNED.getStatus())) {
            throw new ValidationException(
                    messageSource.getMessage("contract.already.assigned", null, Locale.ENGLISH) + id);
        }

        contractUnsigned.setStatus(SIGNED.getStatus());
        Contract contractSigned = contractRepository.save(contractUnsigned);

        return contractConverter.convertToDto(contractSigned);
    }

    private void validate(ContractDto contractDto) {
        if (ObjectUtils.isEmpty(contractDto)) {
            throw new ValidationException(
                    messageSource.getMessage("contractDto.null", null, Locale.ENGLISH));
        }

        if (!ObjectUtils.isEmpty(contractDto.getId())) {
            throw new ValidationException(
                    messageSource.getMessage("contractDto.field.id.autofill", null, Locale.ENGLISH));
        }

        if (!ObjectUtils.isEmpty(contractDto.getStatus())) {
            throw new ValidationException(
                    messageSource.getMessage("contractDto.field.status.autofill", null, Locale.ENGLISH));
        }

        if (!ObjectUtils.isEmpty(contractDto.getAdmin())) {
            throw new ValidationException(
                    messageSource.getMessage("contractDto.field.admin.autofill", null, Locale.ENGLISH));
        }

        if (ObjectUtils.isEmpty(contractDto.getCompanyName()) || contractDto.getCompanyName().isBlank()) {
            throw new ValidationException(
                    messageSource.getMessage("contractDto.field.companyName.not.filled", null, Locale.ENGLISH));
        }

        if (ObjectUtils.isEmpty(contractDto.getEventName()) || contractDto.getEventName().isBlank()) {
            throw new ValidationException(
                    messageSource.getMessage("contractDto.field.eventName.not.filled", null, Locale.ENGLISH));
        }
    }
}