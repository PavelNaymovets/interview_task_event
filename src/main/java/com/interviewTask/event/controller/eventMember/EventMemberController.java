package com.interviewTask.event.controller.eventMember;

import com.interviewTask.event.dto.eventMember.EventMemberDto;
import com.interviewTask.event.service.eventMember.EventMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class EventMemberController {
    private final EventMemberService eventMemberService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EventMemberDto signUp(@RequestBody EventMemberDto eventMemberDto) {
        return eventMemberService.signUp(eventMemberDto);
    }
}
