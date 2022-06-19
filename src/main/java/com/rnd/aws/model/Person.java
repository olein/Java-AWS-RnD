package com.rnd.aws.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rnd.aws.configuration.ModelValidator;

import javax.validation.constraints.*;
import java.time.LocalDate;

/*
Fahim created at 6/18/2022
*/
public class Person {
  @NotBlank(message = "User can not have empty First name")
  @NotNull(message = "User must have First name")
  @Pattern(regexp = "[a-zA-Z]*", message = "Name can only have letters")
  private String firstName;

  @NotBlank(message = "User can not have empty Last name")
  @NotNull(message = "User must have Last name")
  @Pattern(regexp = "[a-zA-Z]*", message = "Name can only have letters")
  private String lastName;

  @Pattern(regexp = "[0-9]*", message = "Phone number can only have numbers")
  private String phoneNumber;

  @Min(value = 10, message = "User can not be less than 10 years old")
  @Max(value = 100, message = "User can not be more than 100 years old")
  @Positive
  private int age;

  @Digits(integer = 5, fraction = 2, message = "Invalid salary")
  private double salary;

  @Email(
      message = "Email is not valid",
      regexp =
          "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
  @NotEmpty(message = "Email cannot be empty")
  private String email;

  @FutureOrPresent(message = "Joining date can not be in the past")
  private LocalDate joinDate;

  @Past(message = "Date of birth should be in the past")
  private LocalDate dateOfBirth;

  private String description;

  @JsonCreator
  public Person(
      String firstName,
      String lastName,
      String phoneNumber,
      int age,
      double salary,
      String email,
      LocalDate joinDate,
      LocalDate dateOfBirth,
      String description) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.age = age;
    this.salary = salary;
    this.email = email;
    this.joinDate = joinDate;
    this.dateOfBirth = dateOfBirth;
    this.description = description;

    ModelValidator.validate(this);
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public int getAge() {
    return age;
  }

  public double getSalary() {
    return salary;
  }

  public String getEmail() {
    return email;
  }

  public LocalDate getJoinDate() {
    return joinDate;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
