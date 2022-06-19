package com.rnd.aws.configuration;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.rnd.aws.model.ErrorModel;
import com.rnd.aws.model.ServiceResponse;
import com.rnd.aws.model.StatusCode;
import com.rnd.aws.util.ErrorUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
Fahim created at 3/27/2021
*/
@ControllerAdvice
@RestController
public class ErrorHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<Object> handleInternalServerError(Exception ex, WebRequest request) {
    ServiceResponse serviceResponse =
        new ServiceResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            StatusCode.ERROR,
            null,
            ErrorUtil.prepareInternalServerErrorResponse());
    return ResponseEntity.internalServerError().body(serviceResponse);
  }

  @ExceptionHandler(value = {IllegalArgumentException.class})
  protected ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
    ServiceResponse serviceResponse =
        new ServiceResponse(
            HttpStatus.BAD_REQUEST,
            StatusCode.ERROR,
            null,
            ErrorUtil.prepareErrorResponse(ex.getMessage()));
    return ResponseEntity.badRequest().body(serviceResponse);
  }

  @ExceptionHandler(value = {ValidationException.class})
  protected ResponseEntity<Object> handleValidationRequest(Exception ex, WebRequest request) {
    ServiceResponse serviceResponse =
            new ServiceResponse(
                    HttpStatus.BAD_REQUEST,
                    StatusCode.ERROR,
                    null,
                    ErrorUtil.prepareErrorResponse(ex.getMessage()));
    return ResponseEntity.badRequest().body(serviceResponse);
  }

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException exc) {
    ServiceResponse serviceResponse =
        new ServiceResponse(
            HttpStatus.BAD_REQUEST,
            StatusCode.ERROR,
            null,
            ErrorUtil.prepareErrorResponse("File too large!"));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(serviceResponse);
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    Optional<ConstraintViolationException> optionalCve = findValidationExceptionOnConstructor(ex);
    if (optionalCve.isPresent()) {
      return handleValidationErrorOnConstructor(optionalCve.get());
    }

    String error = "Invalid request";

    ServiceResponse serviceResponse =
        new ServiceResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            StatusCode.ERROR,
            null,
            List.of(new ErrorModel(error)));

    return ResponseEntity.badRequest().body(serviceResponse);
  }

  private Optional<ConstraintViolationException> findValidationExceptionOnConstructor(
      HttpMessageNotReadableException e) {
    Throwable cause = e.getCause();
    if (cause instanceof ValueInstantiationException) {
      Throwable subCause = cause.getCause();
      if (subCause instanceof ConstraintViolationException) {
        return Optional.of((ConstraintViolationException) subCause);
      }
    }
    return Optional.empty();
  }

  private ResponseEntity<Object> handleValidationErrorOnConstructor(
      ConstraintViolationException cve) {
    List<ErrorModel> errors = new ArrayList<>();
    for (ConstraintViolation<?> constraintViolation : cve.getConstraintViolations()) {
      ErrorModel errorModel =
          new ErrorModel(
              constraintViolation.getMessage(),
              constraintViolation.getPropertyPath().toString(),
              "");
      errors.add(errorModel);
    }

    return ResponseEntity.badRequest().body(errors);
  }
}
