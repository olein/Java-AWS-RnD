package com.rnd.aws.model;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

public class ServiceResponse<T> implements Serializable {

  private HttpStatus status; // OK_Failure
  private StatusCode statusCode; // code
  private T body;
  private List<T> errorList;

  public ServiceResponse() {}

  public ServiceResponse(HttpStatus status, StatusCode statusCode, T body, List<T> errorList) {
    this.setStatus(status);
    this.setStatusCode(statusCode);
    this.setBody(body);
    this.errorList = errorList;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public StatusCode getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(StatusCode statusCode) {
    this.statusCode = statusCode;
  }

  public T getBody() {
    return body;
  }

  public void setBody(T body) {
    this.body = body;
  }

  public List<T> getErrorList() {
    return errorList;
  }

  public void setErrorList(List<T> errorList) {
    this.errorList = errorList;
  }
}
