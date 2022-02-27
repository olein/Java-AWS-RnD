package com.rnd.aws.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
Fahim created at 2/19/2022
*/
@RestController
public class HealthController {
  @GetMapping(value = "/")
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok().build();
  }
}
