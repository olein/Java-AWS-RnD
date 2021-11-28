package com.rnd.aws.ses;

/*
Fahim created at 11/27/2021
*/
public class TransactionalEmailData {
  private final String firstName;
  private final String pin;

  public TransactionalEmailData(String firstName, String pin) {
    this.firstName = firstName;
    this.pin = pin;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getPin() {
    return pin;
  }
}
