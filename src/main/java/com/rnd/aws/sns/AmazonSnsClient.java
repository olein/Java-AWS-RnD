package com.rnd.aws.sns;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/*
Fahim created at 10/30/2021
*/
@Service
public class AmazonSnsClient {
  private AmazonSNS client;

  @PostConstruct
  private void initializeAmazonSnsClient() {
    this.client =
        AmazonSNSClientBuilder.standard()
            .withCredentials(getAwsCredentialProvider())
            .withRegion(Region.getRegion(Regions.AP_SOUTHEAST_1).getName())
            .build();
  }

  private AWSCredentialsProvider getAwsCredentialProvider() {
    AWSCredentials awsCredentials =
        new BasicAWSCredentials("access-key", "secret-key");
    return new AWSStaticCredentialsProvider(awsCredentials);
  }

  public AmazonSNS getClient() {
    return client;
  }
}
