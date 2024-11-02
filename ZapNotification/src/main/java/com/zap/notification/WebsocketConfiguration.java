package com.zap.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebsocketConfiguration implements WebSocketConfigurer
{
		
	 private final MyWebSocketHandler myWebSocketHandler;

	    @Autowired
	    public WebsocketConfiguration(MyWebSocketHandler myWebSocketHandler) {
	        this.myWebSocketHandler = myWebSocketHandler;
	    }

	    @Override
	    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
	        registry.addHandler(myWebSocketHandler, "/notifications").setAllowedOrigins("*");
	    }
}
