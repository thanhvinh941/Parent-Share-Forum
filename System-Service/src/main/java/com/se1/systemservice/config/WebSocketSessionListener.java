package com.se1.systemservice.config;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Configuration
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
//        String princ =  (String) msgHeaders.get("user_detail");
//        StompHeaderAccessor sha = StompHeaderAccessor.wrap(sce.getMessage());
//        List<String> nativeHeaders = sha.getNativeHeader("Authorization");
//        if( nativeHeaders != null )
//        {
            connectedClientId.add(userIdValue);
//            if( logger.isDebugEnabled() )
//            {
//                logger.debug("Connessione websocket stabilita. ID Utente "+princ);
//            }
//        }
//        else
//        {
//            connectedClientId.add(princ);
//            if( logger.isDebugEnabled() )
//            {
//                logger.debug("Connessione websocket stabilita. ID Utente "+princ);
//            }
//        }
    }

    @EventListener
    public void webSockectDisconnect(SessionDisconnectEvent sde)
    {
        MessageHeaders msgHeaders = sde.getMessage().getHeaders();
        Principal princ = (Principal) msgHeaders.get("simpUser");
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(sde.getMessage());
        List<String> nativeHeaders = sha.getNativeHeader("userId");
        if( nativeHeaders != null )
        {
            String userId = nativeHeaders.get(0);
            connectedClientId.remove(userId);
            if( logger.isDebugEnabled() )
            {
                logger.debug("Disconnessione websocket. ID Utente "+userId);
            }
        }
        else
        {
            String userId = princ.getName();
            connectedClientId.remove(userId);
            if( logger.isDebugEnabled() )
            {
                logger.debug("Disconnessione websocket. ID Utente "+userId);
            }
        }
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
