package com.rnd.aws.sns;

import com.amazonaws.services.sns.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
Fahim created at 10/30/2021
*/
@Service
public class SnsService {

  @Autowired AmazonSnsClient amazonSnsClient;

  public String createSNSTopic(String topicName) {

    CreateTopicResult result;
    try {
      CreateTopicRequest request = new CreateTopicRequest().withName(topicName);

      result = amazonSnsClient.getClient().createTopic(request);
      return result.getTopicArn();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    return "";
  }

  public void listSNSTopics() {

    try {
      ListTopicsRequest request = new ListTopicsRequest();

      ListTopicsResult result = amazonSnsClient.getClient().listTopics(request);
      System.out.println("Topics are " + result.getTopics());

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  public void subEmail(String topicArn, String email) {

    try {
      SubscribeRequest request =
          new SubscribeRequest()
              .withProtocol("email")
              .withEndpoint(email)
              .withReturnSubscriptionArn(true)
              .withTopicArn(topicArn);

      SubscribeResult result = amazonSnsClient.getClient().subscribe(request);
      System.out.println("Subscription ARN: " + result.getSubscriptionArn());

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  public void pubTopic(String message, String topicArn) {

    try {
      PublishRequest request = new PublishRequest().withMessage(message).withTopicArn(topicArn);

      PublishResult result = amazonSnsClient.getClient().publish(request);
      System.out.println(
          result.getMessageId()
              + " Message sent. Status is "
              + result.getSdkHttpMetadata().getHttpStatusCode());

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
