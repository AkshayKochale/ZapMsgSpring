package com.zap.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zap.notification.util.ZapKafkaConsumer;

@RestController
public class TestService 
{
	
	@Autowired
	ZapKafkaConsumer consumer;
	
	@GetMapping("/testNotifiction")
	public String test() 
	{
		
		return "";
	}
}
