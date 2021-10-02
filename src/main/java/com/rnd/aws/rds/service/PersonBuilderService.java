package com.rnd.aws.rds.service;

import com.rnd.aws.rds.dto.PersonPageResponse;
import com.rnd.aws.rds.dto.PersonResponse;
import com.rnd.aws.rds.entity.Person;
import com.rnd.aws.util.Util;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/*
Fahim created at 4/5/2021
*/
@Service
public class PersonBuilderService {

  public PersonPageResponse buildPersonPageResponse(Page<Person> userPage) {
    List<PersonResponse> personResponseList =
        Util.toDtoList(userPage.getContent(), PersonResponse.class);

    PersonPageResponse personPageResponse = new PersonPageResponse();
    personPageResponse.setPersons(personResponseList);
    personPageResponse.setTotalUsers(userPage.getTotalElements());
    personPageResponse.setTotalPage(userPage.getTotalPages());

    return personPageResponse;
  }
}
