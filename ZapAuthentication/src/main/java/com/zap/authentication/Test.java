package com.zap.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zap.authentication.dao.ZapUser;
import com.zap.authentication.repo.ZapUserRepo;

@RestController
public class Test {

	@Autowired
	 ZapUserRepo repo;
	
	@RequestMapping("/test")
	public String test() 
	{
		return "Its Working private";
	}
	
	
	@RequestMapping("/public/test")
	public String publictest() 
	{	
		System.out.println("called");
		tt();
		return "Its Public URL";
	}
	
	private void tt() {
		// TODO Auto-generated method stub
		ZapUser findByUsername = repo.findByUsername("akshay");
		System.out.println(findByUsername);
	}
}
