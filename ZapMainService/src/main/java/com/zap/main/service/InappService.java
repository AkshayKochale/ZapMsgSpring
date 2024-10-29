package com.zap.main.service;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zap.main.dao.ZapClient;
import com.zap.main.dao.ZapUser;
import com.zap.main.pojo.GlobalInputPojo;
import com.zap.main.repo.ZapClientRepo;
import com.zap.main.repo.ZapUserRepo;

@RestController
@RequestMapping("/inapp")
public class InappService 
{
	@Autowired
	ZapUserRepo repo;	

	@Autowired
	ZapClientRepo clientRepo;
	
	@Autowired
	CommonService commonService;
	
	
	@PostMapping("/getallactiveusers")
	public ResponseEntity<?> getallActiveUser(@RequestBody GlobalInputPojo pojo) throws Exception 
	{
		JSONObject result=new JSONObject();
		try{
			String username = commonService.fetchUsernameAndValidateToken(pojo.getToken());
			ZapUser user = repo.findByUsername(username);
			
			if(username!=null && !username.isEmpty())
			{
					List<ZapClient> findAllByUser = clientRepo.findAllByUser(user);
					JSONArray clientNameList= new JSONArray();
					
					findAllByUser.forEach(e->
					{
						if(e.isIsactive())
							clientNameList.put(e.getClientname());
					});
					
					result.put("status", "success");
					result.put("output", clientNameList);
			}
			else 
			{
				result.put("status", "failed");
				result.put("output", "Please login");
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			result.put("status", "failed");
			result.put("output", e.getMessage());
		}
		
		return ResponseEntity.ok(result.toString());
	}
	
}
