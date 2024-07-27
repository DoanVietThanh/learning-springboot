package com.ecommerce.firstspring.exceptions;


import com.ecommerce.firstspring.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    Map<String, String> response = new HashMap<>();
    e.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String message = error.getDefaultMessage();
      response.put(fieldName, message);
    });
    return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponse> myResourceNotFoundException(ResourceNotFoundException e) {
    String message = e.getMessage();
    ApiResponse apiResponse = new ApiResponse(message, false);
    return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiResponse> myApiException(ApiException e) {
    String message = e.getMessage();
    ApiResponse apiResponse = new ApiResponse(message, false);
    return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
  }
}
