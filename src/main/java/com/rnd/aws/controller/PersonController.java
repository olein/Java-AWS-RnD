package com.rnd.aws.controller;

import com.rnd.aws.model.Person;
import com.rnd.aws.model.ServiceResponse;
import com.rnd.aws.model.StatusCode;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/*
Fahim created at 6/18/2022
*/
@RestController
@CrossOrigin
@RequestMapping("/person")
public class PersonController {

  static List<String> validImageFileFormat = Arrays.asList("png", "jpg", "jpeg", "gif");

  @PostMapping
  public ResponseEntity<ServiceResponse> createPerson(@RequestBody Person person) {
    ServiceResponse serviceResponse = new ServiceResponse();

    //     if we do not want any html tag
//    person.setDescription(Jsoup.clean(person.getDescription(), Safelist.none()));
    //     if we only allow basic html tags
//    person.setDescription(Jsoup.clean(person.getDescription(), Safelist.basic()));
    //     allow selected tags and attributes
    person.setDescription(
        Jsoup.clean(person.getDescription(), Safelist.relaxed().addAttributes("p", "b")));

    serviceResponse.setBody(person);
    serviceResponse.setStatus(HttpStatus.OK);
    serviceResponse.setStatusCode(StatusCode.SUCCESS);

    return ResponseEntity.ok(serviceResponse);
  }

  @PostMapping("/fileUpload")
  public ResponseEntity<ServiceResponse> fileUpload(
      @RequestPart(value = "file") MultipartFile photo) {
    ServiceResponse serviceResponse = new ServiceResponse();

    String fileType = FilenameUtils.getExtension(photo.getOriginalFilename());
    if (!validImageFileFormat.contains(fileType)) {
      throw new ValidationException("File format is not valid");
    }
    String fileName = UUID.randomUUID().toString() + "." + fileType;

    serviceResponse.setBody(fileName);
    serviceResponse.setStatus(HttpStatus.OK);
    serviceResponse.setStatusCode(StatusCode.SUCCESS);

    return ResponseEntity.ok(serviceResponse);
  }

  // it will only allow letters and digits
  @GetMapping("/{id:[a-zA-Z0-9]*}")
  public ResponseEntity<ServiceResponse> getPerson(@PathVariable String id) {
    ServiceResponse serviceResponse = new ServiceResponse();
    serviceResponse.setBody(id);
    serviceResponse.setStatus(HttpStatus.OK);
    serviceResponse.setStatusCode(StatusCode.SUCCESS);

    return ResponseEntity.ok(serviceResponse);
  }
}
