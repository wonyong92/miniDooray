package com.nhnacademy.minidooray.account.ex;

public class MemberDuplicatedException extends RuntimeException {

    public MemberDuplicatedException(String message) {
        super(message);
    }
}
