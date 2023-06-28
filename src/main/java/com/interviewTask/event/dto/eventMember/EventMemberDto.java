package com.interviewTask.event.dto.eventMember;

import com.interviewTask.event.dto.event.EventDto;
import com.interviewTask.event.dto.user.UserDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventMemberDto {
    private Long id;
    private EventDto event;
    private UserDto user;
    private String pcrTest;
    private String status;
}
