package com.ttis.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.UUID;


public class RemoteError {

    private String code;
    private String message;
    private String guid;
    private String stackTrace;

    public RemoteError() {
    }

    public RemoteError(Throwable throwable) {

        this.guid = UUID.randomUUID().toString();
        this.code = "UNKKNOWN_ERROR";
        this.message = throwable.getMessage();
    }

    public RemoteError(String code, String message) {

        this.guid = UUID.randomUUID().toString();
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}
