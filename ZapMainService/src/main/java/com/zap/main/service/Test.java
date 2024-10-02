package com.zap.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zap.main.external.ZapAuthClient;

@RestController
public class Test 
{
	@Autowired
	ZapAuthClient zapAuthCLient;
	
	@RequestMapping("/test")
	public String test()
	{
		return "It's Working....";
	}
	
	
	@GetMapping("/call")
	public String call() 
	{
		ResponseEntity<?> autheticationStatus = zapAuthCLient.getAutheticationStatus("test");
		
		System.out.println(" data : "+autheticationStatus.getBody());
		
		return "Output :: ";
	}
}
