package com.jpl.teamx.service;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
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
	

	private String user;
	private String pwd;

	public AwsService() {
		setProperties();
		this.credentials = new BasicAWSCredentials(user, pwd);
		this.s3client  = AmazonS3ClientBuilder.standard()
				  .withCredentials(new AWSStaticCredentialsProvider(credentials))
				  .withRegion(Regions.EU_WEST_1)
				  .build();
	}
	
	public AmazonS3 getS3client() {
		return s3client;
	}

	private void setProperties() {
		try (InputStream input = AwsService.class.getClassLoader().getResourceAsStream("aws.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            this.user = prop.getProperty("cloud.aws.credentials.accessKey");
            this.pwd = prop.getProperty("cloud.aws.credentials.secretKey");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
}
