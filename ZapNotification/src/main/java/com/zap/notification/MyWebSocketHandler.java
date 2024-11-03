package com.zap.notification;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

   
	 private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

	    @Override
	    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	        String userId = getUserIdFromSession(session);
	        userSessions.put(userId, session);
	        System.out.println("User connected: " + userId);
	    }

	    @Override
	    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
	        String userId = getUserIdFromSession(session);
	        userSessions.remove(userId);
	        System.out.println("User disconnected: " + userId);
	    }

	    private String getUserIdFromSession(WebSocketSession session) {
	        String query = session.getUri().getQuery();
	        return query.split("=")[1]; // Assuming the query is userId=someId
	    }

	    public void sendMessageToUser(String userId, String message) throws Exception {
	        WebSocketSession session = userSessions.get(userId);
	        if (session != null && session.isOpen()) {
	            session.sendMessage(new TextMessage(message));
	        } else {
	            System.out.println("User not connected: " + userId);
	        }
	    }
	
}
