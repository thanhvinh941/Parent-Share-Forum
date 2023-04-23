package com.se1.systemservice.config;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.se1.systemservice.domain.restclient.UserServiceRestTemplateClient;

import lombok.Data;

@Configuration
@Data
public class WebSocketSessionListener {
	@Autowired
	private UserServiceRestTemplateClient restTemplateClient;
	
	private Map<String, String> connentions = new HashMap<>();
	
	@EventListener
	public void connectionEstablished(SessionConnectedEvent sce) throws NumberFormatException, MalformedURLException {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(sce.getMessage());
		GenericMessage<?> generic = (GenericMessage<?>) accessor
				.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
		SimpMessageHeaderAccessor nativeAccessor = SimpMessageHeaderAccessor.wrap(generic);
		String userIdValue = nativeAccessor.getNativeHeader("userId").get(0);
		String simpSessionId = nativeAccessor.getSessionId();
		connentions.put(simpSessionId, userIdValue);
		restTemplateClient.updateStatus(Long.valueOf(userIdValue), 1);

	}

	@EventListener
	public void webSockectDisconnect(SessionDisconnectEvent sde) throws NumberFormatException, MalformedURLException {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(sde.getMessage());
		String simpSessionId = accessor.getSessionId();
		String userIdValue = connentions.getOrDefault(simpSessionId, null);

		restTemplateClient.updateStatus(Long.valueOf(userIdValue), 0);
		connentions.remove(simpSessionId);
	}
}
