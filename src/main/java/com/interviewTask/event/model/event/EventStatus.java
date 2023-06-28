package com.interviewTask.event.model.event;

import java.util.stream.Stream;

public enum EventStatus {
    DONE("проведен"),
    AWAITS("не проведен");

    private final String status;

    EventStatus(String status) {
        this.status = status;
    }

    public static EventStatus check(final String status) {
        return Stream.of(EventStatus.values())
                .filter(targetEnum -> targetEnum.status.equals(status))
                .findFirst()
                .orElse(null);
    }

    public String getStatus() {
        return status;
    }
}
