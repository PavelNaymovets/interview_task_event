package com.interviewTask.event.service.contract;

import com.interviewTask.event.dto.contract.ContractDto;

public interface ContractService {
    ContractDto liveRequest(ContractDto contractDto);
    ContractDto signOn(Long code);
}
