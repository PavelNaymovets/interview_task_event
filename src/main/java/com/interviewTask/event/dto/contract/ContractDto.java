package com.interviewTask.event.dto.contract;

import com.interviewTask.event.dto.user.UserDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractDto {
    private Long id;
    private String companyName;
    private String eventName;
    private UserDto admin;
    private String status;
}
