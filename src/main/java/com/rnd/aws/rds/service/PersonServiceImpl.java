package com.rnd.aws.rds.service;

import com.rnd.aws.model.ServiceResponse;
import com.rnd.aws.rds.dto.PersonRequest;
import com.rnd.aws.rds.dto.PersonResponse;
import com.rnd.aws.rds.entity.Person;
import com.rnd.aws.rds.repository.PersonRepository;
import com.rnd.aws.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
Fahim created at 4/5/2021
*/
@Service
public class PersonServiceImpl implements PersonService {

  PersonRepository personRepository;
  PersonBuilderService personBuilderService;

  @Autowired
  public PersonServiceImpl(
      PersonRepository personRepository, PersonBuilderService personBuilderService) {
    this.personRepository = personRepository;
    this.personBuilderService = personBuilderService;
  }

  @Override
  public ServiceResponse savePerson(PersonRequest request) {

    Person person = new Person();
    Util.copyProperty(request, person);

    personRepository.save(person);

    PersonResponse personResponse = new PersonResponse();
    Util.copyProperty(person, personResponse);

    ServiceResponse response = Util.prepareSuccessResponse(personResponse);
    return response;
  }

  @Override
  public ServiceResponse getPersons(int pageNo, int pageSize) {
    Pageable page = PageRequest.of(pageNo, pageSize);
    Page<Person> personPage = personRepository.findAll(page);
    ServiceResponse response =
        Util.prepareSuccessResponse(personBuilderService.buildPersonPageResponse(personPage));

    return response;
  }

  @Override
  public ServiceResponse deletePerson(Long personId) {

    Optional<Person> personOptional = personRepository.findById(personId);

    if (!personOptional.isPresent()) {
      throw new IllegalArgumentException("Person not found");
    }

    personRepository.delete(personOptional.get());

    ServiceResponse response = Util.prepareSuccessResponse("Person deleted");
    return response;
  }
}
