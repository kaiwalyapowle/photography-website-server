package com.saeebhurke.SaeeBhurke.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	String getSmIpAddress();

	void storeFile(MultipartFile file, String panelName);

	void storeFile(MultipartFile file, String panelName, String galleryName);
	
	Resource loadFile(String filename, String panelname);
	
	Resource loadFile(String filename, String panelname, String galleryname);
	
	void deleteAll();
	
	 void init();

}
