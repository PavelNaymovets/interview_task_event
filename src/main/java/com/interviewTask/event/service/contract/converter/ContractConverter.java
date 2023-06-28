package com.interviewTask.event.service.contract.converter;

import com.interviewTask.event.dto.contract.ContractDto;
import com.interviewTask.event.model.contract.Contract;
import com.interviewTask.event.util.converter.Converter;

public interface ContractConverter extends Converter<Contract, ContractDto> {
    @Override
    Contract convertToEntity(ContractDto contractDto);

    @Override
    ContractDto convertToDto(Contract contract);
}
