package com.jpl.teamx.service;

import java.io.IOException;
import java.io.InputStream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

@Service
public class ImageStorageService {
	
	
	@Autowired
	private AwsService awsService;
	private static final String BUCKET_NAME = "teamx-images";
	private static final String ERROR_UPLOAD = "https://s3.eu-west-1.amazonaws.com/teamx-images/error-during-uploading-photo.jpg";
	private static final String ERROR_SIZE = "https://s3.eu-west-1.amazonaws.com/teamx-images/5mb-exceed-limit.jpg";
	private static final long MAX_SIZE = 5000000;
	public ImageStorageService() {
		
	}
	
	
	public String storeImage(MultipartFile file, String s3ObjectKey) {
		AmazonS3 s3client = awsService.getS3client();
		try {
			InputStream is = file.getInputStream();
			if(file.getSize()>MAX_SIZE)
				return ERROR_SIZE; //8536900 = 8500kB = 8.5mB
			//store s3 file
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType("image");
			s3client.putObject(new PutObjectRequest(BUCKET_NAME, s3ObjectKey.trim(), is,metadata).withCannedAcl(CannedAccessControlList.PublicRead));
			S3Object s3Object = s3client.getObject(new GetObjectRequest(BUCKET_NAME,s3ObjectKey));
			String picUrl = s3Object.getObjectContent().getHttpRequest().getURI().toString();  
			return picUrl;
		}
		catch(IOException e) {
			e.printStackTrace();
			return ERROR_UPLOAD;
		}
	}

}
