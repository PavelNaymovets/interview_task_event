package com.interviewTask.event.dto.jwt;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequestDto {
    private String login;
    private String password;
}
