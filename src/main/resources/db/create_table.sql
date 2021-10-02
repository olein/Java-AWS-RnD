create database `aws-demo`;

use `aws-demo`;

create table person (
  id bigint not null auto_increment,
  age integer not null,
  country varchar(255),
  create_date datetime,
  created_by varchar(255),
  first_name varchar(255),
  last_name varchar(255),
  update_date datetime,
  updated_by varchar(255),
  primary key (id)
) engine=InnoDB;