package com.zap.main.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ZapKafkaUtil {

	@Value("${kafka.topicname}")
    private String topicName ; 

    @Autowired
    KafkaTemplate<String, String> template;
    
    
    public void sendToKafka() 
    {
    	template.send(topicName, "This Is Goku!!!");
    }
    
    
}
