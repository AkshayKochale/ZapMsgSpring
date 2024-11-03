package com.zap.main.service;

import java.util.List;
import java.util.stream.Collectors;

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
import com.zap.main.util.ZapKafkaUtil;

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
	
	@Autowired
	ZapKafkaUtil kafkaUtil;
	
	
	
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
	
	
	@PostMapping("/sendnotification")
	public ResponseEntity<?> sendNotification(@RequestBody GlobalInputPojo pojo) throws Exception 
	{
		JSONObject result=new JSONObject();
		try{
			String username = commonService.fetchUsernameAndValidateToken(pojo.getToken());
			ZapUser user = repo.findByUsername(username);
			List<String> clients = pojo.getClients();
			
			if(clients==null || clients.isEmpty())
			{
				result.put("status", "failed");
				result.put("output", "Select user");
			}
			
			else if(username!=null && !username.isEmpty())
			{
				List<ZapClient> clientList = clientRepo.findAllByUser(user);
				List<String> clientNameList = clientList.stream().map(e->e.getClientname()).collect(Collectors.toList());
				
					for(String clientname : clients) 
					{
						if(clientNameList.contains(clientname))
						{
							JSONObject object=new JSONObject();
							object.put("msgType", "notification");
							object.put("msgtitle", pojo.getNotificationtitle());
							object.put("msgcontent", pojo.getNotificationmsg());
							object.put("clientname", clientname);
							
							kafkaUtil.sendToKafka(object.toString());
						}
					}
					result.put("status", "success");
					result.put("output", "Successfully Notified");
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

