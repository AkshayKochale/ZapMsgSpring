package com.zap.main.service;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zap.main.dao.ZapSubscription;
import com.zap.main.dao.ZapUser;
import com.zap.main.pojo.GlobalInputPojo;
import com.zap.main.repo.ZapClientRepo;
import com.zap.main.repo.ZapSubscriptionRepo;
import com.zap.main.repo.ZapUserRepo;

@RestController("/bashboard")
public class DashboradService
{
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	ZapUserRepo repo;
	
	@Autowired
	ZapSubscriptionRepo subscriptionRepo;
	
	@Autowired
	ZapClientRepo clientRepo;
	
	@GetMapping("/data")
	public ResponseEntity<?> getData(@RequestBody GlobalInputPojo pojo) 
	{	
		JSONObject result=new JSONObject();
		try 
		{
			String username = commonService.fetchUsernameAndValidateToken(pojo.getToken());
			
			if(username==null || username.isEmpty())
				result.put("error", "Something went wrong.");
			else 
			{
				ZapUser user = repo.findByUsername(username);
				ZapSubscription subscription = subscriptionRepo.findByUser(user);
				Integer totalClientCount = clientRepo.getTotalClientCount(user.getId());
				
				result.put("username", user.getUsername());
				result.put("sub-enddate", subscription.getSubscription_end_Date());
				result.put("credits-used", subscription.getCredits_used());
				result.put("credits-total", subscription.getCredits_avaliable());
				result.put("totalclientcount", totalClientCount==null?0:totalClientCount);
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(result);
	}
	
	
	
	
	
	
	
}
