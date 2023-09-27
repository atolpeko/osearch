package com.osearch.indexer.adapter.in.rest;

import com.osearch.indexer.adapter.in.rest.entity.ErrorResponse;
import com.osearch.indexer.application.usecase.exception.IndexerException;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.servlet.http.HttpServletRequest;

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

/**
 * This class is an exception interceptor for handling various
 * exceptions that may occur during the execution of the application
 * and sending appropriate REST responses.
 */
@Log4j2
@RestControllerAdvice
public class ExceptionInterceptor {

    @ExceptionHandler(IndexerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUseCaseException(
        IndexerException e,
        HttpServletRequest request
    ) {
        return handleException(e.getMessage(), request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse handleException(
        String msg,
        HttpServletRequest request,
        HttpStatus status
    ) {
        var path = request.getServletPath();
        log.error("Request for {} error: {}", path, msg);
        var timestamp = LocalDateTime.now()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli();
        return ErrorResponse.builder()
            .status(status.value())
            .error(msg)
            .path(path)
            .timestamp(timestamp)
            .build();
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class,
        HttpMediaTypeNotSupportedException.class,
        UnsatisfiedServletRequestParameterException.class,
        MissingServletRequestParameterException.class,
        MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(Exception e, HttpServletRequest request) {
        return handleException(e.getMessage(), request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleNotSupportedException(
        HttpRequestMethodNotSupportedException e,
        HttpServletRequest request
    ) {
        return handleException(e.getMessage(), request, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(
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
    public ErrorResponse handleUnknownException(Exception e, HttpServletRequest request) {
        return handleException(e.getMessage(), request, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
