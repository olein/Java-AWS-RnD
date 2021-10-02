package com.rnd.aws.rds.controller;

import com.rnd.aws.model.ServiceResponse;
import com.rnd.aws.rds.dto.PersonRequest;
import com.rnd.aws.rds.service.PersonService;
import com.rnd.aws.util.ErrorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*
Fahim created at 4/5/2021
*/
@RestController
@RequestMapping("/api/person")
public class PersonController {

  @Autowired PersonService personService;

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<ServiceResponse> savePerson(
      @Valid @RequestBody PersonRequest request, BindingResult bindingResult) {
    ServiceResponse response;

    if (bindingResult.hasErrors()) {
      response = ErrorUtil.requestErrorHandler(bindingResult);
      return ResponseEntity.ok(response);
    }

    response = personService.savePerson(request);

    return ResponseEntity.ok(response);
  }

  @RequestMapping(path = "/{pageNo}/{pageSize}", method = RequestMethod.GET)
  public ResponseEntity<ServiceResponse> getPersons(
      @PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) {
    ServiceResponse response;

    response = personService.getPersons(pageNo, pageSize);

    return ResponseEntity.ok(response);
  }

  @RequestMapping(path = "/{personId}", method = RequestMethod.DELETE)
  public ResponseEntity<ServiceResponse> deletePersons(@PathVariable("personId") Long personId) {
    ServiceResponse response;

    response = personService.deletePerson(personId);

    return ResponseEntity.ok(response);
  }
}
