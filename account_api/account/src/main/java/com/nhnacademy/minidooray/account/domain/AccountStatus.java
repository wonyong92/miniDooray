package com.nhnacademy.minidooray.account.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AccountStatus {
    REGISTERED("REGISTERED"),
    WITHDRAWN("WITHDRAWN"),
    DORMANT("DORMANT");

    private final String value;

    AccountStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public boolean equalsName(String value) {
        return this.getValue().equals(value);
    }
}