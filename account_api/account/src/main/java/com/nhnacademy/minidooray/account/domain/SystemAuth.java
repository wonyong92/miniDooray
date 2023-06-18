package com.nhnacademy.minidooray.account.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SystemAuth {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    SystemAuth(String value) {
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