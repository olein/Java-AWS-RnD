package com.rnd.aws.util;

import com.rnd.aws.model.ErrorModel;
import com.rnd.aws.model.ServiceResponse;
import com.rnd.aws.model.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/*
Fahim created at 3/13/2021
*/
public class ErrorUtil {

  public static ServiceResponse requestErrorHandler(BindingResult bindingResult) {
    ServiceResponse response = new ServiceResponse();
    List<ErrorModel> errorModelList = new ArrayList();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      ErrorModel errorModel = new ErrorModel();
      errorModel.setField(fieldError.getField());
      errorModel.setMessage(fieldError.getDefaultMessage());
      errorModel.setDescription(fieldError.getCode());
      errorModelList.add(errorModel);
    }

    response.setErrorList(errorModelList);
    response.setStatus(HttpStatus.BAD_REQUEST);
    response.setStatusCode(StatusCode.ERROR);
    return response;
  }

  public static List<ErrorModel> prepareInternalServerErrorResponse() {
    ErrorModel errorModel = new ErrorModel("Internal server Error", "", "Internal server Error");
    List<ErrorModel> errorList = new ArrayList<>();
    errorList.add(errorModel);
    return errorList;
  }

  public static List<ErrorModel> prepareErrorResponse(String message) {
    ErrorModel errorModel = new ErrorModel(message);
    List<ErrorModel> errorList = new ArrayList<>();
    errorList.add(errorModel);
    return errorList;
  }

  public static ServiceResponse prepareErrorResponse(String message, HttpStatus status) {
    ServiceResponse response = new ServiceResponse();
    response.setBody(null);
    response.setErrorList(prepareErrorResponse(message));
    response.setStatus(status);
    response.setStatusCode(StatusCode.ERROR);
    return response;
  }
}
