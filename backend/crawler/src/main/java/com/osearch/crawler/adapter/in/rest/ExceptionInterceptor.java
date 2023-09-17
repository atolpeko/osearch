package com.osearch.crawler.adapter.in.rest;

import com.osearch.crawler.application.usecase.exception.CrawlerAlreadyRunningException;
import com.osearch.crawler.application.usecase.exception.CrawlerNotRunningException;
import com.osearch.crawler.application.usecase.exception.ServiceException;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.servlet.http.HttpServletRequest;

import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Log4j2
@RestControllerAdvice
public class ExceptionInterceptor {

    @Data
    @Builder
    public static class JsonErrorMessage {
        private final long timestamp;
        private final int status;
        private final String error;
        private final String path;
    }

    @ExceptionHandler(CrawlerAlreadyRunningException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonErrorMessage handleCrawlerAlreadyRunningException(
        CrawlerAlreadyRunningException e,
        HttpServletRequest request
    ) {
        return handleException(e.getMessage(), request, HttpStatus.BAD_REQUEST);
    }

    private JsonErrorMessage handleException(String msg, HttpServletRequest request, HttpStatus status) {
        var path = request.getServletPath();
        log.error("Request for {} error: {}", path, msg);
        var timestamp = LocalDateTime.now()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli();
        return JsonErrorMessage.builder()
            .status(status.value())
            .error(msg)
            .path(path)
            .timestamp(timestamp)
            .build();
    }

    @ExceptionHandler(CrawlerNotRunningException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonErrorMessage handleCrawlerNotRunningException(
        CrawlerNotRunningException e,
        HttpServletRequest request
    ) {
        return handleException(e.getMessage(), request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonErrorMessage handleServiceException(
        ServiceException e,
        HttpServletRequest request
    ) {
        return handleException(e.getMessage(), request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class,
        HttpMediaTypeNotSupportedException.class,
        UnsatisfiedServletRequestParameterException.class,
        MissingServletRequestParameterException.class,
        MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonErrorMessage handleBadRequestException(Exception e, HttpServletRequest request) {
        return handleException(e.getMessage(), request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public JsonErrorMessage handleNotSupportedException(
        HttpRequestMethodNotSupportedException e,
        HttpServletRequest request
    ) {
        return handleException(e.getMessage(), request, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonErrorMessage handleValidationException(
        MethodArgumentNotValidException e,
        HttpServletRequest request
    ) {
        var builder = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach(error -> {
            var errorMsg = error.getDefaultMessage();
            builder.append(errorMsg).append(", ");
        });

        builder.delete(builder.lastIndexOf(","), builder.length());
        return handleException(builder.toString(), request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonErrorMessage handleUnknownException(Exception e, HttpServletRequest request) {
        return handleException(e.getMessage(), request, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
