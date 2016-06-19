package com.ttis.security;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;

/**
 * User: Carlos Lawton
 * Date: 9/6/12
 * Time: 12:28 PM
 */
public class RemoteResponse<T> {

    private boolean success;
    private DateTime timestamp;
    private T result;
    private RemoteError error;
    private String action;

    public RemoteResponse(){}

    public RemoteResponse(T result) {
        this.result = result;
        this.success = true;
        this.timestamp = DateTime.now();
    }

    public RemoteResponse(RemoteError apiError) {
        this.error = apiError;
        this.success = false;
        this.timestamp = DateTime.now();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public RemoteError getError() {
        return error;
    }

    public void setError(RemoteError error) {
        this.error = error;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
