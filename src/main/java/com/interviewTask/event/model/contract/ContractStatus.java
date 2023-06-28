package com.interviewTask.event.model.contract;

import java.util.stream.Stream;

public enum ContractStatus {
    SIGNED("подписан"),
    UNSIGNED("не подписан");

    private final String status;

    ContractStatus(String status) {
        this.status = status;
    }

    public static ContractStatus check(final String status) {
        return Stream.of(ContractStatus.values())
                .filter(targetEnum -> targetEnum.status.equals(status))
                .findFirst()
                .orElse(null);
    }

    public String getStatus() {
        return status;
    }
}
