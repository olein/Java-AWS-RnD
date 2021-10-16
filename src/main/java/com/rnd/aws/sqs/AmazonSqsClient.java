package com.rnd.aws.sqs;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AmazonSqsClient {

  private AmazonSQS client;

  @PostConstruct
  private void initializeAmazonSqsClient() {
    this.client =
        AmazonSQSClientBuilder.standard()
            .withCredentials(getAwsCredentialProvider())
            .withRegion(Region.getRegion(Regions.AP_SOUTHEAST_1).getName())
            .build();
  }

  private AWSCredentialsProvider getAwsCredentialProvider() {
    AWSCredentials awsCredentials = new BasicAWSCredentials("access-key", "secret-key");
    return new AWSStaticCredentialsProvider(awsCredentials);
  }

  public AmazonSQS getClient() {
    return client;
  }
}
