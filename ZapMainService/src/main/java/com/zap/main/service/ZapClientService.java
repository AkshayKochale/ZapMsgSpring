package com.zap.main.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zap.main.dao.ZapClient;
import com.zap.main.dao.ZapUser;
import com.zap.main.pojo.GlobalInputPojo;
import com.zap.main.pojo.ZapClientPojo;
import com.zap.main.repo.ZapClientRepo;
import com.zap.main.repo.ZapUserRepo;

@RestController("/clientService")
public class ZapClientService 
{

	@Autowired
	ZapUserRepo repo;	

	@Autowired
	ZapClientRepo clientRepo;
	
	@Autowired
	CommonService commonService;
	
	
	@GetMapping("/getallclient")
	public ResponseEntity<List<ZapClient>> getAllClient(@RequestBody GlobalInputPojo pojo)
	{
		List<ZapClient> result=new ArrayList<>();
		try 
		{
			String username = commonService.fetchUsernameAndValidateToken(pojo.getToken());
			ZapUser user = repo.findByUsername(username);
			
			if(username!=null && !username.isEmpty())
			{
				result=clientRepo.findAllByUser(user);
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return ResponseEntity.ok(result);
	}
	
	
	@PostMapping("/addclient")
	public ResponseEntity<?> addClient(@RequestBody ZapClientPojo pojo)
	{
		JSONObject result=new JSONObject();
		try 
		{
			String username = commonService.fetchUsernameAndValidateToken(pojo.getToken());
			ZapUser user = repo.findByUsername(username);
			
			if(username!=null && !username.isEmpty())
			{
				ZapClient client=new ZapClient();
				client.setClientname(pojo.getClientname());
				client.setClientemail(pojo.getClientemail());
				client.setCreated(new Date());
				client.setClientphone(pojo.getClientphone());
				client.setIsactive(true);
				client.setUser(user);
				client.setClienttype(pojo.getClientType());
				
				clientRepo.save(client);
				result.put("output", "Sucessfully added");
				
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/updateclient")
	public ResponseEntity<?> updateClient(@RequestBody ZapClientPojo pojo)
	{
		JSONObject result=new JSONObject();
		try 
		{
				
				String username = commonService.fetchUsernameAndValidateToken(pojo.getToken());
				ZapUser user = repo.findByUsername(username);
				ZapClient client=clientRepo.findById(pojo.getClientid()).orElse(null);
				
				if(client==null)
				{
					result.put("output", "Invalid client");
				}else 
				{
					client.setClientname(pojo.getClientname());
					client.setClientemail(pojo.getClientemail());
					client.setCreated(new Date());
					client.setClientphone(pojo.getClientphone());
					client.setIsactive(true);
					client.setUser(user);
					client.setClienttype(pojo.getClientType());
					
					clientRepo.save(client);
					result.put("output", "Sucessfully updated");
				}
				
			
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return ResponseEntity.ok(result);
	}
	
	
	@PostMapping("/getclient/{clientid}")
	public ResponseEntity<ZapClient> getClient(@PathVariable Integer clientid)
	{
		ZapClient client=null;
		try 
		{
			Optional<ZapClient> clientOptional = clientRepo.findById(clientid);
			
			if(clientOptional.isPresent())
				client = clientOptional.get();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return ResponseEntity.ok(client);
	}
	
	
	@DeleteMapping("/deleteclient")
	public ResponseEntity<?>  deleteClient(@RequestBody GlobalInputPojo pojo)
	{
		JSONObject result=new JSONObject();
		try 
		{
			String username = commonService.fetchUsernameAndValidateToken(pojo.getToken());
			ZapUser user = repo.findByUsername(username);
			
			if(user!=null )
			{
				Optional<ZapClient> clientOptional = clientRepo.findById(pojo.getClientid());
				
				if(clientOptional.isPresent())
				{
					ZapClient zapClient = clientOptional.get();
					String zapClientName=zapClient.getClientname();
					clientRepo.delete(zapClient);
					
					result.put("output", zapClientName+" Client removed ");
				}
				else {
						result.put("output", "Invalid Client");
				}
				
			}
			
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/toggleactive/{clientid}")
	public void toggelActiveStatus(@PathVariable Integer clientid) 
	{
		ZapClient client=null;
		try 
		{
			Optional<ZapClient> clientOptional = clientRepo.findById(clientid);
			
			if(clientOptional.isPresent())
			{
				client = clientOptional.get();
				client.setIsactive(!client.isIsactive());
				clientRepo.save(client);
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
}
