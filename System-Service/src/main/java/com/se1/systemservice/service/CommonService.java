package com.se1.systemservice.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CommonService {

	private final String UPLOAD_DIR = "../local-store/";
	
	public boolean saveFile(String rathStr, String fileName, InputStream inputStream) throws IOException {
		Path uploadPath = Paths.get(UPLOAD_DIR + rathStr);
		long fileStore = 0;
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try {
			Path filePath = uploadPath.resolve(fileName.replace(" ", ""));
			fileStore = Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new IOException("Could not save file:  " + fileName,e);
		}
		return fileStore > 0;
	}
}
