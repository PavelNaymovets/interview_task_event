package com.interviewTask.event.dto.user;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private Long age;
    private String login;
    private String status;
    private String password;
    private String role;
}
