package com.rnd.aws.controller;

import com.rnd.aws.model.ServiceResponse;
import com.rnd.aws.model.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
Fahim created at 3/3/2021
*/
@RestController
@RequestMapping("/api")
public class WelcomeController {

  private static final Logger LOG = LoggerFactory.getLogger(WelcomeController.class);

  List<String> values = new ArrayList<>();

  @GetMapping(value = "/index")
  public ResponseEntity<ServiceResponse> index() {

    ServiceResponse serviceResponse = new ServiceResponse();

    serviceResponse.setBody("Welcome to AWS RnD Java Project");
    serviceResponse.setStatus(HttpStatus.OK);
    serviceResponse.setStatusCode(StatusCode.SUCCESS);

    return ResponseEntity.ok(serviceResponse);
  }

  @PostMapping(value = "/")
  public ResponseEntity<ServiceResponse> addValue(@RequestBody String value) {

    this.values.add(value);
    ServiceResponse serviceResponse = new ServiceResponse();


    serviceResponse.setBody("value added to list");
    serviceResponse.setStatus(HttpStatus.OK);
    serviceResponse.setStatusCode(StatusCode.SUCCESS);

    return ResponseEntity.ok(serviceResponse);
  }

  @DeleteMapping(value = "/")
  public ResponseEntity<ServiceResponse> deleteValue(@RequestBody String value) {

    this.values.removeIf(v->v.equals(value));
    ServiceResponse serviceResponse = new ServiceResponse();


    serviceResponse.setBody("value deleted from list");
    serviceResponse.setStatus(HttpStatus.OK);
    serviceResponse.setStatusCode(StatusCode.SUCCESS);

    return ResponseEntity.ok(serviceResponse);
  }

  @GetMapping(value = "/")
  public ResponseEntity<ServiceResponse> getValue() {

    ServiceResponse serviceResponse = new ServiceResponse();

    serviceResponse.setBody(values);
    serviceResponse.setStatus(HttpStatus.OK);
    serviceResponse.setStatusCode(StatusCode.SUCCESS);

    return ResponseEntity.ok(serviceResponse);
  }
}
