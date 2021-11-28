package com.rnd.aws.ses;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/*
Fahim created at 10/30/2021
*/
@Service
public class AmazonSesClient {

  @Bean
  public AmazonSimpleEmailService getAmazonSimpleEmailService() {
    return AmazonSimpleEmailServiceClientBuilder.standard()
        .withCredentials(getAwsCredentialProvider())
        .withRegion("ap-southeast-1")
        .build();
  }

  private AWSCredentialsProvider getAwsCredentialProvider() {
    AWSCredentials awsCredentials =
        new BasicAWSCredentials("access-key", "secret-key");
    return new AWSStaticCredentialsProvider(awsCredentials);
  }
}
