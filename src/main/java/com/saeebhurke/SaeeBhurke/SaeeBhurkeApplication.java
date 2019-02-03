package com.saeebhurke.SaeeBhurke;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.saeebhurke.SaeeBhurke.services.StorageService;

@SpringBootApplication
public class SaeeBhurkeApplication {

	// No idea why @Resource is used here
	@Resource
	StorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(SaeeBhurkeApplication.class, args);
	}

}
