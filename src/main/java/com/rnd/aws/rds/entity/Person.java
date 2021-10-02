package com.rnd.aws.rds.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
Fahim created at 4/5/2021
*/
@Entity
@Table(name = "person")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  private String firstName;

  private String lastName;

  private String country;

  private int age;

  @Column(name = "create_date")
  private LocalDateTime createDate;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "update_date")
  private LocalDateTime updateDate;

  @Column(name = "updated_by")
  private String updatedBy;

  @PrePersist
  public void beforeSave() {
    if (this.createDate == null) {
      this.createDate = LocalDateTime.now();
    }
  }

  @PreUpdate
  public void beforeEdit() {
    this.updateDate = LocalDateTime.now();
  }
}
