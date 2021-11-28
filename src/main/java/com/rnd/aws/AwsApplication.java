package com.rnd.aws;

import com.rnd.aws.ses.SesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class AwsApplication implements CommandLineRunner {

  @Autowired SesService sesService;

  public static void main(String[] args) {
    SpringApplication.run(AwsApplication.class, args);
  }

  @Override
  public void run(String... args) {
    sesService.sendEmail();
    sesService.createTemplate();
    sesService.updateTemplate();
    sesService.sendTemplatedEmail();
    sesService.deleteTemplate();
  }
}
