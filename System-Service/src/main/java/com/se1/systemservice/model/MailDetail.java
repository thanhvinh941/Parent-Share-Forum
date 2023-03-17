package com.se1.systemservice.model;

import lombok.Data;

@Data
public class MailDetail {
	 private String recipient;
	 private String msgBody;
	 private String subject;
	 private String attachment;
}
