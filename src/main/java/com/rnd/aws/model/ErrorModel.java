package com.rnd.aws.model;

import java.io.Serializable;

/*
Fahim created at 3/12/2021
*/
public class ErrorModel implements Serializable {

  private String message;
  private String field;
  private String description;

  public ErrorModel() {}

  public ErrorModel(String message) {
    this.message = message;
  }

  public ErrorModel(String message, String field, String description) {
    this.message = message;
    this.field = field;
    this.description = description;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
