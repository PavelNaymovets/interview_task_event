package com.interviewTask.event.dto.event;

import com.interviewTask.event.dto.contract.ContractDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;
    private ContractDto contract;
    private String name;
    private Long cost;
    private String status;
}
