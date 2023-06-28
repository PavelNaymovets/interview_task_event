package com.interviewTask.event.controller.contract;

import com.interviewTask.event.auth.util.IsManager;
import com.interviewTask.event.dto.contract.ContractDto;
import com.interviewTask.event.service.contract.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contract")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ContractDto liveRequest(@RequestBody ContractDto contractDto) {
        return contractService.liveRequest(contractDto);
    }

    @IsManager
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ContractDto signOn(@PathVariable Long code) {
        return contractService.signOn(code);
    }
}
