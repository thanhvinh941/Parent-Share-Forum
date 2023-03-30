package com.se1.systemservice.domain.common.utils;

import java.util.UUID;

public class CommonUtils {

	public static String getFileName(String base64Type) {
		String extension;
		
		switch (base64Type) {
		case "jpeg;base64":
			extension = ".jpeg";
			break;
		case "png;base64":
			extension = ".png";
			break;
		case "pdf;base64":
			extension = ".pdf";
			break;
		case "vnd.openxmlformats-officedocument.wordprocessingml.document;base64":
			extension = ".docx";
			break;
		case "vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64":
			extension = ".docx";
			break;
		default:
			extension = ".jpg";
			break;
		}

		return UUID.randomUUID().toString() + extension;
	}
}
