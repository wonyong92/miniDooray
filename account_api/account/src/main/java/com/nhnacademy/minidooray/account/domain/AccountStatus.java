package com.nhnacademy.minidooray.account.domain;

public enum AccountStatus {
    REGISTERED,
    WITHDRAWN,
    DORMANT;

    public String getName() {
        return this.name();
    }

    public boolean equalsName(String name) {
        return this.name().equals(name);
    }
}