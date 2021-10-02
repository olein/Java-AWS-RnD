package com.rnd.aws.rds.dto;

import java.util.List;

/*
Fahim created at 4/5/2021
*/
public class PersonPageResponse {
  List<PersonResponse> persons;
  long totalUsers;
  int totalPage;

  public List<PersonResponse> getPersons() {
    return persons;
  }

  public void setPersons(List<PersonResponse> persons) {
    this.persons = persons;
  }

  public long getTotalUsers() {
    return totalUsers;
  }

  public void setTotalUsers(long totalUsers) {
    this.totalUsers = totalUsers;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }
}
