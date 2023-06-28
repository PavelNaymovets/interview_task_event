package com.interviewTask.event.exception;

import lombok.*;

@Data
@AllArgsConstructor
public class AppError {
    private int statusCode;
    private String message;
}
