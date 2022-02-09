package com.rnd.aws.controller;

import com.rnd.aws.model.ServiceResponse;
import com.rnd.aws.model.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
Fahim created at 2/5/2022
*/
@RestController
@RequestMapping("/api")
public class ParameterStoreController {

  private static final Logger LOG = LoggerFactory.getLogger(ParameterStoreController.class);

  @Value("${db.url}")
  String url;

  @Value("${db.password}")
  private String password;

  @GetMapping(value = "/parameterStore")
  public ResponseEntity<ServiceResponse> index() {

    ServiceResponse serviceResponse = new ServiceResponse();

    LOG.info("URL : " + url);
    LOG.info("Password : " + password);

    serviceResponse.setBody(
        "Welcome to AWS RnD Java Project. Testing parameter store."
            + "URL "
            + url
            + ", Password "
            + password);
    serviceResponse.setStatus(HttpStatus.OK);
    serviceResponse.setStatusCode(StatusCode.SUCCESS);

    return ResponseEntity.ok(serviceResponse);
  }
}
