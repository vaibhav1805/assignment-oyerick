package com.oyerickshaw.tracker.exceptions;

public class ServiceException extends RuntimeException {

    private Integer errorCode;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

}
