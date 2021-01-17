package com.example.cacheservice.web.webConfig;

public class CreateSuccess extends HttpException {
    public CreateSuccess(int code, String message){
        this.httpStatusCode = 201;
        this.code = code;
        this.message = message;
    }
}
