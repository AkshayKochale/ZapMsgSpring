package com.zap.notification.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.zap.notification.MyWebSocketHandler;

@Controller
public class NotificationController {

    private final MyWebSocketHandler myWebSocketHandler;

    @Autowired
    public NotificationController(MyWebSocketHandler myWebSocketHandler) {
        this.myWebSocketHandler = myWebSocketHandler;
    }

    @GetMapping("/test/send")
    public ResponseEntity<String> testSendMessage() {
        try {
            myWebSocketHandler.broadcastMessage("This is from api");
            return ResponseEntity.ok("Message sent");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to send message");
        }
    }
    
    public void broadcastMessage(String msg) throws Exception 
    {
    	myWebSocketHandler.broadcastMessage(msg);
    }
}

