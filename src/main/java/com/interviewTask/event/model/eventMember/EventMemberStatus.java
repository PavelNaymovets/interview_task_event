package com.interviewTask.event.model.eventMember;

import java.util.stream.Stream;

public enum EventMemberStatus {
    MEMBER("участник"),
    NOT_MEMBER("не участник"),
    POSITIVE("положительный"),
    NEGATIVE("отрицательный");

    private final String status;

    EventMemberStatus(String status) {
        this.status = status;
    }

    public static EventMemberStatus check(final String status) {
        return Stream.of(EventMemberStatus.values())
                .filter(targetEnum -> targetEnum.status.equals(status))
                .findFirst()
                .orElse(null);
    }

    public String getStatus() {
        return status;
    }
}
