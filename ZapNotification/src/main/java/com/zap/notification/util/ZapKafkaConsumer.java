package com.zap.notification.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zap.notification.pojo.MessagePojo;
import com.zap.notification.repo.ZapClientRepo;
import com.zap.notification.service.NotificationController;

@Component
public class ZapKafkaConsumer 
{
	
	@Autowired
	ZapClientRepo clientRepo;
	
	 @Autowired
	 NotificationController notificationController;

    
	
	
//	@Value("${kafka.topicname}")
//	private String topicName;

	@KafkaListener(topics = "${kafka.topicname}")
	public void consume(String data) 
	{
		try 
		{
			ObjectMapper objM=new ObjectMapper();
			MessagePojo messagePojo = objM.readValue(data, MessagePojo.class);
//			System.out.println(data);
			notificationController.broadcastMessage(data);
		
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
