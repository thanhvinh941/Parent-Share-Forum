package com.se1.userservice.payload;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ContactResponse {
	
	private Map<Integer, List<ContactDto>> mapContact;
}
