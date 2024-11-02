package com.zap.main.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zap.main.dao.History;
import com.zap.main.dao.ZapUser;
import com.zap.main.pojo.GlobalInputPojo;
import com.zap.main.repo.ZapHistoryRepo;
import com.zap.main.repo.ZapUserRepo;


@RestController
@RequestMapping("/history")
public class HistoryService 
{
	
	
	@Autowired
	ZapUserRepo userRepo;
	
	@Autowired
	ZapHistoryRepo historyRepo;
	
	@Autowired
	CommonService commonService;
	
	@GetMapping("/getdetails/{historyid}")
	public ResponseEntity<?> gethistoryDetails(@PathVariable Integer historyid ) throws Exception
	{
		JSONObject result=new JSONObject();
		try 
		{
				Optional<History> historyData = historyRepo.findById(historyid);
				
			if(historyData.isPresent())	
			{	
				History history = historyData.get();
				result=new JSONObject(history.getMessage());
				result.put("status", "success");
			}
			else 
			{
				result.put("status", "failed");
				result.put("output", "Invalid Data");
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
	
	@PostMapping("/getfromdate")
	public ResponseEntity<?> getHistoryData(@RequestBody GlobalInputPojo pojo)throws Exception 
	{
		JSONObject result=new JSONObject();
		try 
		{
			String username = commonService.fetchUsernameAndValidateToken(pojo.getToken());
			ZapUser user = userRepo.findByUsername(username);
			
			if(username!=null && !username.isEmpty())
			{
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				List<History> historyData = historyRepo.findAllByUserAndSenddateGreaterThanEqual(user,format.parse(pojo.getDate()) );
				
				JSONObject aggCount = getAggCount(historyData);
				JSONObject dateWise = getDateWise(historyData);
				JSONArray tableData = getTableData(historyData);
				
				result.put("aggCount", aggCount);
				result.put("dateWise", dateWise);
				result.put("tableData", tableData);
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
	
	static JSONObject getAggCount(List<History> historyData) throws Exception 
	{
		JSONObject obj=null;
		Map<String, Long> collect = historyData.stream()
										.collect(Collectors.groupingBy(History::getMessagetype,Collectors.counting()));
		 
		obj=new JSONObject(collect);
		
		return obj;
	}
	
	static JSONObject getDateWise(List<History> historyData) throws Exception 
	{
		JSONObject obj=null;
		 TreeMap<Date,Map<String,Long>> collect = historyData.stream()
		.collect(Collectors.groupingBy(History::getSenddate,TreeMap::new,Collectors.groupingBy(History::getMessagetype,Collectors.counting())));
		
		 
		obj=new JSONObject(collect);
		
		return obj;
	}
	
	static JSONArray getTableData(List<History> historyData) throws Exception 
	{
		
			
		List<JSONObject> collect = historyData.stream().map(e->
		{
			JSONObject data=new JSONObject();
			try {
			data.put("username", e.getClient().getClientname());
			data.put("date", e.getSenddate());
			data.put("type", e.getMessagetype());
			data.put("historyid",e.getId());
			
			}catch(Exception ex) {ex.printStackTrace();}
			return data;
		}).collect(Collectors.toList());

		JSONArray obj=new JSONArray(collect);
		
		return obj;
	}
	
}
