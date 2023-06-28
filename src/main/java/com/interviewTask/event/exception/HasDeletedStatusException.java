package com.interviewTask.event.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HasDeletedStatusException extends RuntimeException {
    private String errorMessage;

    public HasDeletedStatusException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
