package com.spring.starter;

import com.spring.starter.property.FileStorageProperties;
import it.ozimov.springboot.mail.configuration.EnableEmailTools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({FileStorageProperties.class})
@EnableEmailTools
public class NdbApplication {
	public static void main(String[] args) {
		SpringApplication.run(NdbApplication.class, args);
	}
}
