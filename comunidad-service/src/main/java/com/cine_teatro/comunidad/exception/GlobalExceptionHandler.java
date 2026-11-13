package com.cine_teatro.comunidad.exception;
import org.springframework.web.bind.annotation.*;import org.springframework.http.*;import java.util.*;
@RestControllerAdvice public class GlobalExceptionHandler {
@ExceptionHandler(ApiException.class) public ResponseEntity<Map<String,String>> api(ApiException e){return ResponseEntity.status(e.status).body(Map.of("error",e.getMessage()));}
@ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class) public ResponseEntity<Map<String,String>> validation(Exception e){return ResponseEntity.badRequest().body(Map.of("error","Datos invalidos"));}
@ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class) public ResponseEntity<Map<String,String>> json(Exception e){return ResponseEntity.badRequest().body(Map.of("error","JSON o enum invalido"));}
}
