package com.rnd.aws;

import com.rnd.aws.sqs.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@SpringBootApplication
@Component
public class AwsApplication implements CommandLineRunner {

  @Value("${queue.order}")
  String orderQueue;

  @Autowired ProducerService producerService;

  public static void main(String[] args) {
    SpringApplication.run(AwsApplication.class, args);
  }

  @Override
  public void run(String... args) {
    sendMessage();
    sendMultiple();
  }

  public void sendMessage() {
    for (int i = 1; i <= 10; i++) {
      String data = "{\"id\":" + i + ", \"name\":\"Pizza\"}";
      CompletableFuture.runAsync(() -> producerService.send(orderQueue, data));
    }
  }

  public void sendMultiple() {
    producerService.sendMultiple();
  }
}
