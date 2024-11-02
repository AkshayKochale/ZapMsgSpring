package com.zap.main.service;

import java.security.SecureRandom;
import java.util.Date;

import javax.transaction.Transactional;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zap.main.dao.ZapUser;
import com.zap.main.pojo.GlobalInputPojo;
import com.zap.main.repo.ZapClientRepo;
import com.zap.main.repo.ZapHistoryRepo;
import com.zap.main.repo.ZapUserRepo;

@RestController
@RequestMapping("/userservice")
public class ZapUserService 
{
	

	@Autowired
	ZapUserRepo repo;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	ZapHistoryRepo historyRepo;
	
	@Autowired
	ZapClientRepo clientRepo;
	
	 @PostMapping("/getuserdata")
	 public ResponseEntity<?> getUserData(@RequestBody GlobalInputPojo pojo) throws Exception 
	 {
		 JSONObject result=new JSONObject();
		 ZapUser user =null;
		 try 
		 {
			 String username = commonService.fetchUsernameAndValidateToken(pojo.getToken());
			 user = repo.findByUsername(username);
			 
			 if(user==null)
			 {
				 result.put("status","failed" );
				 result.put("output","Unable To Find User");
				 
				 return ResponseEntity.ok(result.toString());
			 }
			 
			 ObjectMapper mapper = new ObjectMapper();
	            String jsonString = mapper.writeValueAsString(user);
			 
			 result=new JSONObject(jsonString);
			 
			 int totalNotificationSent=historyRepo.findAllByUserAndMessagetype(user, "notification").size();
			 int totalEmailSent=historyRepo.findAllByUserAndMessagetype(user, "email").size();
			 int clientCount=clientRepo.findAllByUser(user).size();
			 result.put("notificationsent", totalNotificationSent);
			 result.put("emailsent", totalEmailSent);
			 result.put("clientcount", clientCount);
			 result.put("status","success" );
			 	
		 }
		 catch(Exception e) 
		 {
			 e.printStackTrace();
			 result.put("status","failed" );
			 result.put("output",e.getMessage());
		 }
		 
		 return ResponseEntity.ok(result.toString());
	 }
	 
	 
	 @PostMapping("/createusertoken")
	 public ResponseEntity<?> createUserToken(@RequestBody GlobalInputPojo pojo) throws Exception 
	 {
		 JSONObject result=new JSONObject();
		 ZapUser user =null;
		 try 
		 {
			 String username = commonService.fetchUsernameAndValidateToken(pojo.getToken());
			 user = repo.findByUsername(username);
			 
			Date tokengenearteddate = user.getTokengenearteddate();
			
			if (tokengenearteddate != null && new Date().getTime() - tokengenearteddate.getTime() < 3600000) 
			{
				result.put("status","failed" );
				result.put("output","You have recently created token");
			}
			else 
			{
				user.setAccountToken(generateToken());
				
				System.out.println("Token : "+user.getAccountToken());
				user.setTokengenearteddate(new Date());
				repo.save(user);
				result.put("status","success" );
				result.put("output","Token Generated Successfully");
			}
			 	
		 }
		 catch(Exception e) 
		 {
			 e.printStackTrace();
			 result.put("status","failed" );
			 result.put("output",e.getMessage());
		 }
		 
		 return ResponseEntity.ok(result.toString());
	 }
	 
	 
	  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    private static final SecureRandom RANDOM = new SecureRandom();

	    public static String generateToken() {
	        StringBuilder token = new StringBuilder();

	        for (int i = 0; i < 20; i++) {
	            token.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));

	            
	            if ((i + 1) % 4 == 0 && i < 19) {
	                token.append("-");
	            }
	        }

	        return token.toString();
	    }

}
