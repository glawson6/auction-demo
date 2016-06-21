package com.ttis.security;

/**
 * Utility enum to make it easy to keep up with all the error codes
 */
public interface RemoteErrorCodes {
    public static final String INVALID_API_TOKEN = "INVALID_API_TOKEN";
    public static final String MISSING_API_TOKEN = "MISSING_API_TOKEN";
    public static final String MISSING_UID_HEADER = "MISSING_UID_HEADER";
    public static final String EXPIRED_API_TOKEN = "EXPIRED_API_TOKEN";
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String API_NOT_IMPLEMENTED = "API_NOT_IMPLEMENTED";
    public static final String MISSING_REQUIRED_PARAMETER = "MISSING_REQUIRED_PARAMETER";
    public static final String MISSING_REQUIRED_HEADER = "MISSING_REQUIRED_HEADER";
    public static final String INVALID_USER_CREDENTIALS = "INVALID_USER_CREDENTIALS";
    public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
    public static final String UNKNOWN_API_CALL = "UNKNOWN_API_CALL";
    public static final String DEPRECATED_API_CALL = "DEPRECATED_API_CALL";
    public static final String ACCOUNT_IS_DISABLED = "ACCOUNT_IS_DISABLED";
    public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    public static final String INSUFFICIENT_RIGHTS = "INSUFFICIENT_RIGHTS";
}
