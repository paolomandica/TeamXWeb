package com.jpl.teamx.service;



import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AwsService {
	private AWSCredentials credentials;
	
	private AmazonS3 s3client;
	
	@Value("${cloud.aws.credentials.accessKey}")
	private String user;
	
	@Value("${cloud.aws.credentials.secretKey}")
	private String pwd;

	public AwsService() {
		System.out.println("user" + user + "/npwd" + pwd);
		this.credentials = new BasicAWSCredentials("", "+14SC");
		this.s3client  = AmazonS3ClientBuilder.standard()
				  .withCredentials(new AWSStaticCredentialsProvider(credentials))
				  .withRegion(Regions.EU_WEST_1)
				  .build();
	}
	
	public AmazonS3 getS3client() {
		return s3client;
	}

	public String uploadImage(String string) {
		s3client.putObject(new PutObjectRequest("teamx-images", "some-path/some-key.jpg", new File("somePath/someKey.jpg")).withCannedAcl(CannedAccessControlList.PublicRead));
		String urlImage = s3client.getUrl("teamx-images", "some-path/some-key.jpg").toString();
		return urlImage;
	}
}
