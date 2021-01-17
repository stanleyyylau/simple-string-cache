package com.example.cacheservice.web.webConfig;

public class HttpException extends RuntimeException{
    protected Integer code;
    protected Integer httpStatusCode = 500;
    protected String message;

    public Integer getCode() {
        return code;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getMessage() {
        return message;
    }
}
