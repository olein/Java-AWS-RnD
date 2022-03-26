package com.rnd.aws.controller;

import com.rnd.aws.model.ServiceResponse;
import com.rnd.aws.model.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

/*
Fahim created at 3/3/2021
*/
@RestController
@RequestMapping("/api")
public class WelcomeController {

  private static final Logger LOG = LoggerFactory.getLogger(WelcomeController.class);

  @GetMapping(value = "/index")
  public ResponseEntity<ServiceResponse> index() {

    ServiceResponse serviceResponse = new ServiceResponse();

    // log();
    InetAddress ip = null;
    String hostname = "";
    String response = "";
    try {
      ip = InetAddress.getLocalHost();
      hostname = ip.getHostName();
      LOG.info("Your current IP address : " + ip);
      LOG.info("Your current Hostname : " + hostname);
      response =
          String.format(
              "Welcome to AWS RnD Java Project auto deploy from Github. Server ip is %s and "
                  + "hostname %s",
              ip, hostname);
    } catch (UnknownHostException e) {
      response = e.getMessage();
    }

    serviceResponse.setBody(response);
    serviceResponse.setStatus(HttpStatus.OK);
    serviceResponse.setStatusCode(StatusCode.SUCCESS);

    return ResponseEntity.ok(serviceResponse);
  }

  private void log() {
    LOG.info("This is a log test at time " + LocalDateTime.now());
    for (int i = 0; i < 10; i++) {
      LOG.info("The number i = " + i);
    }

    try {
      throw new RuntimeException("Some runtime error");
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    }
  }
}
