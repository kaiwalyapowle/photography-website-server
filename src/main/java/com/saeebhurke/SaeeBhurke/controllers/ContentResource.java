package com.saeebhurke.SaeeBhurke.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.saeebhurke.SaeeBhurke.filestorage.StorageService;

@CrossOrigin
@RestController
public class ContentResource {

	@Autowired
	private StorageService storageService;
	/*- Panel is the tab on UI eg. slideshow, bio, nature and travel, commercial
	 * photography etc.
	 */

	/*- Directories are created inside 'photos' folder by name of the panel name
	 * eg. slideshow folder will contain all slideshow files Store files in their
	 * directories. 
	 */

	/*- Directory name is the key and list of files is the value
	 */
	Map<String, List<String>> panelFileListMap = new HashMap<String, List<String>>();
	// Map<String, List<String>> galleryFileListMap = new HashMap<String,
	// List<String>>();

	@PostMapping("/uploadFile")
	public ResponseEntity<String> handleFileUpload(@RequestParam("paneName") String panelName,
			@RequestParam("file") MultipartFile file) {
		// @RequestParam("file") here 'file' is the name of the request parameter to
		// bind to which will go in the MultipartFile object, 'file'.
		String message = "";
		try {
			storageService.storeFile(file, panelName);
			if (panelFileListMap.containsKey(panelName)) {
				panelFileListMap.get(panelName).add(file.getOriginalFilename());
			} else {
				List<String> fileNamesList = new ArrayList<String>();
				fileNamesList.add(file.getOriginalFilename());
				panelFileListMap.put(panelName, fileNamesList);
			}
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.ok().body(message);
		} catch (Exception e) {
			System.out.println(e);
			message = "Error occured while uploading file !";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@PostMapping("/uploadFileTree")
	public ResponseEntity<String> handleFileUpload(@RequestParam("paneName") String panelName,
			@RequestParam("galleryName") String galleryName, @RequestParam("file") MultipartFile file) {
		// @RequestParam("file") here 'file' is the name of the request parameter to
		// bind to which will go in the MultipartFile object, 'file'.
		String message = "";
		try {
			storageService.storeFile(file, panelName, galleryName);
			if (panelFileListMap.containsKey(galleryName)) {
				panelFileListMap.get(galleryName).add(file.getOriginalFilename());
			} else {
				List<String> fileNamesList = new ArrayList<String>();
				fileNamesList.add(file.getOriginalFilename());
				panelFileListMap.put(galleryName, fileNamesList);
			}
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.ok().body(message);
		} catch (Exception e) {
			System.out.println(e);
			message = "Error occured while uploading file !";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	// @RequestMapping("/files/{filename:.+")
	@GetMapping("/files/{filename:.+}/{panelName}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename,
			@PathVariable("panelName") String panelname) {

		Resource file = storageService.loadFile(filename, panelname);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);

	}

	// @RequestMapping("/files/{filename:.+")
	@GetMapping("/files/{filename:.+}/{panelName}/{galleryName}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename,
			@PathVariable("panelName") String panelname, @PathVariable("galleryName") String galleryname) {

		Resource file = storageService.loadFile(filename, panelname, galleryname);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);

	}

	@RequestMapping("/getAllFiles")
	public ResponseEntity<List<String>> getAllFiles(@RequestParam String panelName) {

		List<String> fileNamesList = panelFileListMap.get(panelName);
		if (fileNamesList != null) {
			List<String> allFiles = fileNamesList.stream()
					.map(fileName -> MvcUriComponentsBuilder
							.fromMethodName(ContentResource.class, "getFile", fileName, panelName)
							.host(storageService.getSmIpAddress()).build().toString())
					.collect(Collectors.toList());
			return ResponseEntity.ok().body(allFiles);
		} else {
			throw new RuntimeException("No images are uploaded in category = " + panelName);
		}
	}

	@RequestMapping("/getAllGalleryFiles")
	public ResponseEntity<List<String>> getAllFiles(@RequestParam String panelName, @RequestParam String galleryName) {
		List<String> fileNamesList = panelFileListMap.get(galleryName);
		if (fileNamesList != null) {
			List<String> allFiles = fileNamesList.stream()
					.map(fileName -> MvcUriComponentsBuilder
							.fromMethodName(ContentResource.class, "getFile", fileName, panelName, galleryName)
							.host(storageService.getSmIpAddress()).build().toString())
					.collect(Collectors.toList());
			return ResponseEntity.ok().body(allFiles);
		} else {
			throw new RuntimeException("No images are uploaded in category = " + panelName);
		}
	}

}
