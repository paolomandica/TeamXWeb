package com.jpl.teamx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.jpl.teamx.storage.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class TeamxApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamxApplication.class, args);
	}

}
