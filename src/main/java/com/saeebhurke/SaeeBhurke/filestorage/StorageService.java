package com.saeebhurke.SaeeBhurke.filestorage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;

@Service
public class StorageService {

	private final Path rootLocation = Paths.get("photos");
	private Path panel = null;
	private Path gallery = null;
	private String ipAddress = "localhost";

	public void saveSmIpAddress() {
		try {
			InetAddress ip = InetAddress.getLocalHost();
			this.ipAddress = ip.getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getSmIpAddress() {
		return ipAddress;
	}

	public void storeFile(MultipartFile file, String panelName) {
		try {
			panel = Paths.get(panelName);
			Path filePath = this.rootLocation.resolve(this.panel);
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists() || resource.isReadable()) {
				Files.copy(file.getInputStream(), filePath.resolve(file.getOriginalFilename()));
			} else {
				Files.createDirectory(filePath);
				Files.copy(file.getInputStream(), filePath.resolve(file.getOriginalFilename()));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void storeFile(MultipartFile file, String panelName, String galleryName) {
		try {
			panel = Paths.get(panelName);
			gallery = Paths.get(galleryName);

			// first creating panel folder in photos folder. eg. NT_Wildlife inside photos
			Path panelPath = this.rootLocation.resolve(this.panel);
			Resource panelResource = new UrlResource(panelPath.toUri());
			if (!panelResource.exists()) {
				Files.createDirectory(panelPath);
			}

			// then creating another folder inside panel folder eg. Fauna inside NT_Wildlife
			Path galleryPath = this.rootLocation.resolve(this.panel.resolve(this.gallery));
			Resource resource = new UrlResource(galleryPath.toUri());
			if (resource.exists() || resource.isReadable()) {
				Files.copy(file.getInputStream(), galleryPath.resolve(file.getOriginalFilename()));
			} else {
				Files.createDirectory(galleryPath);
				Files.copy(file.getInputStream(), galleryPath.resolve(file.getOriginalFilename()));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public Resource loadFile(String filename, String panelname) {
		try {
			Path panelName = Paths.get(panelname);
			Path filePath = this.rootLocation.resolve(panelName.resolve(filename));
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("The resource " + filename + " does not exist or is not readable");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Resource loadFile(String filename, String panelname, String galleryname) {
		try {
			Path panelName = Paths.get(panelname);
			Path galleryName = Paths.get(galleryname);
			Path filePath = this.rootLocation.resolve(panelName.resolve(galleryName.resolve(filename)));
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("The resource " + filename + " does not exist or is not readable");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(this.rootLocation.toFile());
	}

	public void init() {
		try {
			Files.createDirectory(this.rootLocation);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}