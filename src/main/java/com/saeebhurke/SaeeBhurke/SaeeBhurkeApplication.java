package com.saeebhurke.SaeeBhurke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.saeebhurke.SaeeBhurke.filestorage.StorageService;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class SaeeBhurkeApplication implements CommandLineRunner {

	// No idea why @Resource is used here
	@Resource
	StorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(SaeeBhurkeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		storageService.deleteAll();
		storageService.init();
//		storageService.saveSmIpAddress();
	}

}
