package com.rnd.aws.controller;

import com.rnd.aws.model.ServiceResponse;
import com.rnd.aws.model.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
Fahim created at 3/3/2021
*/
@RestController
@RequestMapping("/api")
public class WelcomeController {

  @GetMapping(value = "/index")
  public ResponseEntity<ServiceResponse> index() throws Exception {
    ServiceResponse serviceResponse = new ServiceResponse();

    serviceResponse.setBody("Welcome to AWS RnD Java Project");
    serviceResponse.setStatus(HttpStatus.OK);
    serviceResponse.setStatusCode(StatusCode.SUCCESS);

    return ResponseEntity.ok(serviceResponse);
  }
}
