package com.se1.systemservice.domain.payload;

import lombok.Data;

@Data
public class MailDetail {
	 private String recipient;
	 private String msgBody;
	 private String subject;
	 private String attachment;
}
