package com.rnd.aws.s3.service;

import com.rnd.aws.model.ServiceResponse;
import com.rnd.aws.s3.config.AmazonS3Client;
import com.rnd.aws.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*
Fahim created at 6/5/2021
*/
@Service
public class S3ServiceImpl implements S3Service {

  String bucketName = "fahim-s3-rnd";

  @Autowired AmazonS3Client amazonS3Client;

  @Override
  public ServiceResponse uploadFile(MultipartFile file) throws IOException {

    amazonS3Client.uploadFile(bucketName, file);

    return Util.prepareSuccessResponse("File Uploaded successfully");
  }

  @Override
  public ServiceResponse getDownloadUrl(String fileName) {
    String url = amazonS3Client.getPreSignedDownloadUrl(bucketName, fileName);
    return Util.prepareSuccessResponse(url);
  }

  @Override
  public ServiceResponse deleteFile(String fileName) {

    amazonS3Client.deleteFile(bucketName, fileName);
    return Util.prepareSuccessResponse("File Deleted successfully");
  }
}
