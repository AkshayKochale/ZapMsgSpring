package com.zap.main.service;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.zap.main.external.ZapAuthClient;
import com.zap.main.repo.ZapUserRepo;

@Service
public class CommonService
{
		
	@Autowired
	ZapAuthClient zapAuthClient;
	
	@Autowired
	ZapUserRepo repo;
	

	public String fetchUsernameAndValidateToken(String token) throws JSONException
	{
		if(token==null || token.isEmpty())return "";
		
		ResponseEntity<?> reponse = zapAuthClient.getAutheticationStatus(token);
		
		JSONObject result = (JSONObject)reponse.getBody();
		
		if(result.has("valid"))
		{
			boolean isTokenValid = result.getBoolean("valid");
			
			if(isTokenValid)
			{
				return result.getString("username");
			}
		}
		
		return "";
	}
	
	
}
