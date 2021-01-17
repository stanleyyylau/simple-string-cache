package com.example.cacheservice.web.webConfig;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleError(HttpServletRequest req, Exception e) {
        // unknown exception should log and not return to frontend
        String method = req.getMethod();
        String uri = req.getRequestURI();
        UnifyResponse response = new UnifyResponse(9999, "Unknown Error", method + " " + uri);
        return response;
    }

    @ExceptionHandler(value = HttpException.class)
    @ResponseBody
    public ResponseEntity<UnifyResponse> handleHttpException(HttpServletRequest req, HttpException e) {
        String method = req.getMethod();
        String uri = req.getRequestURI();

        UnifyResponse response = new UnifyResponse(e.getCode(), e.getMessage(), method + " " + uri);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<UnifyResponse> res = new ResponseEntity(response, headers, httpStatus);
        return res;
    }

}
