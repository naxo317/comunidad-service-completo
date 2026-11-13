package com.cine_teatro.comunidad.exception;
import org.springframework.http.HttpStatus;
public class ApiException extends RuntimeException {public final HttpStatus status;public ApiException(HttpStatus status,String message){super(message);this.status=status;}}
