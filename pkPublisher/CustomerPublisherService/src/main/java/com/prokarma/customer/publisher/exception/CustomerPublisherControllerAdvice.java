package com.prokarma.customer.publisher.exception;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.prokarma.customer.publisher.domain.ErrorResponse;

@ControllerAdvice
public class CustomerPublisherControllerAdvice {

  @ExceptionHandler(ServletRequestBindingException.class)
  public final ResponseEntity<ErrorResponse> handleException(ServletRequestBindingException ex,
      HttpServletRequest request) {

    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus("Error");
    errorResponse.setMessage("Input Headers are missing: " + ex.getMessage());
    errorResponse.setErrorType(ServletRequestBindingException.class.getSimpleName());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(AuthenticationException.class)
  public final ResponseEntity<ErrorResponse> handleException(AuthenticationException ex,
      HttpServletRequest request) {

    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus("Error");
    errorResponse.setMessage("TokenError: " + ex.getMessage());
    errorResponse.setErrorType(AuthenticationException.class.getSimpleName());
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public final ResponseEntity<ErrorResponse> handleException(NoHandlerFoundException ex,
      HttpServletRequest request) {

    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus("Error");
    errorResponse.setMessage("General Error: " + ex.getMessage());
    errorResponse.setErrorType(NoHandlerFoundException.class.getSimpleName());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public final ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException ex,
      HttpServletRequest request) {
    Map<String, TreeSet<String>> fieldvalidationError = new TreeMap<>();
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    for (FieldError fieldError : fieldErrors) {

      if (fieldvalidationError.containsKey(fieldError.getField())) {
        TreeSet<String> error = fieldvalidationError.get(fieldError.getField());
        error.add(fieldError.getDefaultMessage());
        fieldvalidationError.put(fieldError.getField(), error);

      } else {

        TreeSet<String> error = new TreeSet<>();
        error.add(fieldError.getDefaultMessage());
        fieldvalidationError.put(fieldError.getField(), error);
      }
    }
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus("Error");
    errorResponse.setMessage("input request validation failed." + fieldvalidationError);
    errorResponse.setErrorType(MethodArgumentNotValidException.class.getSimpleName());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
