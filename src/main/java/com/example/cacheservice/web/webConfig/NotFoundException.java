package com.example.cacheservice.web.webConfig;

public class NotFoundException extends HttpException{
    public NotFoundException(int code) {
        this.httpStatusCode = 404;
        this.code = code;
        this.message = "Not Found";
    }
}
