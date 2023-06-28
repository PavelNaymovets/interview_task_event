package com.interviewTask.event.model.user;

import java.util.stream.Stream;

public enum UserStatus {
    ACTIVE("активный"),
    REMOTE("удаленный");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

    public static UserStatus check(final String status) {
        return Stream.of(UserStatus.values())
                .filter(targetEnum -> targetEnum.status.equals(status))
                .findFirst()
                .orElse(null);
    }

    public String getStatus() {
        return status;
    }
}
