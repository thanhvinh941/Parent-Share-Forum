package com.se1.userservice.domain.payload;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ContactResponse {
	
	private Map<Integer, List<ContactDto>> mapContact;
}
