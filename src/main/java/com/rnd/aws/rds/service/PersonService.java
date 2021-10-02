package com.rnd.aws.rds.service;

import com.rnd.aws.model.ServiceResponse;
import com.rnd.aws.rds.dto.PersonRequest;

public interface PersonService {

  ServiceResponse savePerson(PersonRequest request);

  ServiceResponse getPersons(int pageNo, int pageSize);

  ServiceResponse deletePerson(Long personId);
}
