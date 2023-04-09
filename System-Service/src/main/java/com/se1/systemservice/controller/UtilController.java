package com.se1.systemservice.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.se1.systemservice.domain.common.utils.CommonUtils;
import com.se1.systemservice.domain.service.CommonService;

@RestController
@RequestMapping("/system/internal")
public class UtilController {
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping(value="/upload-file", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam ("file") String file) throws IOException
    {       
		String[] imageBase64 = file.split(",");

		String imageName = CommonUtils.getFileName(imageBase64[0].split("/")[1]);
		byte[] imageByte = Base64.getDecoder().decode(imageBase64[1].trim());
		InputStream inputStream = new ByteArrayInputStream(imageByte);
		
		commonService.saveFile(imageName, inputStream);
		return imageName;
    }
}
