package com.se1.systemservice.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.Data;

@Configuration
@Data
public class WebSocketSessionListener {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketSessionListener.class.getName());
    private List<String> connectedClientId = new ArrayList<String>();

    @EventListener
    public void connectionEstablished(SessionConnectedEvent sce)
    {
//        MessageHeaders msgHeaders = sce.getMessage().getHeaders();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(sce.getMessage());
        GenericMessage<?> generic = (GenericMessage<?>) accessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
        SimpMessageHeaderAccessor nativeAccessor = SimpMessageHeaderAccessor.wrap(generic);
        String userIdValue = nativeAccessor.getNativeHeader("userId").get(0);
        //TODO Authen

           connectedClientId.add(userIdValue);

    }

    @EventListener
    public void webSockectDisconnect(SessionDisconnectEvent sde)
    {
    	 StompHeaderAccessor accessor = StompHeaderAccessor.wrap(sde.getMessage());
         GenericMessage<?> generic = (GenericMessage<?>) accessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
         SimpMessageHeaderAccessor nativeAccessor = SimpMessageHeaderAccessor.wrap(generic);
         String userIdValue = nativeAccessor.getNativeHeader("userId").get(0);
        
            connectedClientId.remove(userIdValue);
           
    }

    public List<String> getConnectedClientId()
    {
        return connectedClientId;
    }
    public void setConnectedClientId(List<String> connectedClientId)
    {
        this.connectedClientId = connectedClientId;
    }
}
