package com.se1.userservice.domain.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.se1.userservice.domain.common.SCMConstant;
import com.se1.userservice.domain.model.Verification;
import com.se1.userservice.domain.payload.request.MailRequest;
import com.se1.userservice.domain.repository.VerifycationRepository;
import com.se1.userservice.domain.restClient.SystemServiceRestTemplateClient;

@Service
public class VerifycationService {

	private final String VERIFYCATION_URL = "verify";
	private final String VERIFYCATION_EXTERNAL = "external";
	private final String VERIFYCATION_MAPPING = "accept-tokent";

	@Autowired
	private VerifycationRepository repository;

	@Value("micro-service.api-gateway")
	private String apigatewayServiceUrl;

	@Autowired
	private SystemServiceRestTemplateClient restTemplateClient;
	
	public void processCreate(Long userId, String mail, String userName) throws JsonProcessingException {

		Verification verification = new Verification();
		verification.setValidFlg(SCMConstant.VALID_FLG);
		verification.setUserId(userId);
		verification.setExpirationTime(getExpirationTime());
		verification.setToken(UUID.randomUUID().toString());

		Verification verificationSave = repository.save(verification);
		String verificationLink = String.format("%s/%s/%s/%s?token=%s", apigatewayServiceUrl, VERIFYCATION_URL,
				VERIFYCATION_EXTERNAL, VERIFYCATION_MAPPING, verificationSave.getToken());

		MailRequest mailRequest = new MailRequest();
		mailRequest.setMailTemplate("sign-up");
		mailRequest.setTo(mail);
		mailRequest.setData(Map.of("verificationLink", verificationLink, "userName" , "userName"));
		
		restTemplateClient.sendMail(mailRequest);
	}

	private Date getExpirationTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR_OF_DAY, 3);
		return calendar.getTime();
	}

}
