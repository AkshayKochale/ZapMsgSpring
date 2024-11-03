package com.zap.notification.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zap.notification.MyWebSocketHandler;

@Controller
public class NotificationController {

	private final MyWebSocketHandler webSocketHandler;

    @Autowired
    public NotificationController(MyWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessageToUser(@RequestParam String userId, @RequestParam String message) {
        try {
            webSocketHandler.sendMessageToUser(userId, message);
            return ResponseEntity.ok("Message sent to user: " + userId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending message: " + e.getMessage());
        }
    }
}

