package com.rnd.aws.s3.config;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

/*
Fahim created at 6/5/2021
*/
@Service
public class AmazonS3Client {

  private AmazonS3 s3client;

  @PostConstruct
  private void initializeAmazon() {
    this.s3client =
        AmazonS3ClientBuilder.standard()
            .withRegion(Regions.AP_SOUTHEAST_1)
            .withCredentials(getAwsCredentialPovider())
            .build();
  }

  private AWSCredentialsProvider getAwsCredentialPovider() {
    AWSCredentials awsCredentials = new BasicAWSCredentials("access-key", "secret-key");
    return new AWSStaticCredentialsProvider(awsCredentials);
  }

  public PutObjectResult uploadFile(String bucketName, MultipartFile file) throws IOException {
    if (!s3client.doesBucketExist(bucketName)) {
      s3client.createBucket(bucketName);
    }

    String fileName = file.getOriginalFilename();
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(file.getSize());
    objectMetadata.setContentType("application/octet-stream");

    PutObjectResult result =
        s3client.putObject(
            new PutObjectRequest(bucketName, fileName, file.getInputStream(), objectMetadata));

    // Set file access to private
    s3client.setObjectAcl(bucketName, fileName, CannedAccessControlList.BucketOwnerFullControl);

    return result;
  }

  public String getPreSignedDownloadUrl(String bucketName, String fileName) {

    java.util.Date expiration = new java.util.Date();
    long expTimeMillis = expiration.getTime();
    expTimeMillis += 1000 * 60 * 60; // 1 HR expiration time
    expiration.setTime(expTimeMillis);
    GeneratePresignedUrlRequest generatePresignedUrlRequest =
        new GeneratePresignedUrlRequest(bucketName, fileName)
            .withMethod(HttpMethod.GET)
            .withExpiration(expiration);
    URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);
    return url.toString();
  }

  public String getPublicDownloadUrl(String bucketName, String fileName) {
    s3client.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
    URL url = s3client.getUrl(bucketName, fileName);
    return url.toString();
  }

  public void deleteFile(String bucketName, String fileName) {
    s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
  }
}
