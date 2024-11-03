package com.zap.main.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zap.main.dao.ZapClient;
import com.zap.main.dao.ZapUser;
import com.zap.main.pojo.GlobalInputPojo;
import com.zap.main.repo.ZapClientRepo;
import com.zap.main.repo.ZapUserRepo;
import com.zap.main.util.ZapKafkaUtil;

@RestController
@RequestMapping("/emailservice")
public class EmailService 
{
	
	@Autowired
	ZapUserRepo repo;	

	@Autowired
	ZapClientRepo clientRepo;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	ZapKafkaUtil kafkaUtil;
	
	@Value("${zap.attachmentpath}")
    private String attachmentBasePath;
	
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
							clientNameList.put(e.getClientemail());
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

	
	  @PostMapping("/sendemail")
	  public ResponseEntity<?>sendEmail( @RequestPart("data") GlobalInputPojo data,@RequestParam(value = "attachments", required = false) List<MultipartFile>
	  attachments) throws Exception 
	  {
		  
		  JSONObject result=new JSONObject();
		  
		try 
		  {
			
			String username = commonService.fetchUsernameAndValidateToken(data.getToken());
			ZapUser user = repo.findByUsername(username);
			
			if(data.getEmailsubject()==null)
			{
				result.put("status", "failed");
				result.put("output", "Email Subject Is Mandatory");
				return ResponseEntity.ok(result.toString());
			}
			
			if(data.getEmailContent()==null)
			{
				result.put("status", "failed");
				result.put("output", "Email Content Is Mandatory");
				return ResponseEntity.ok(result.toString());
			}
			
			if(data.getClients()==null)
			{
				result.put("status", "failed");
				result.put("output", "Email Ids Cannot be Blank");
				return ResponseEntity.ok(result.toString());
			}
			
			  
			if(user.getEmail()==null || user.getEmail().isEmpty())
			{
				result.put("status", "failed");
				result.put("output", "Cannot Send Email - Configure Your Account Email");
				return ResponseEntity.ok(result.toString());
			}
			
			if(username!=null && !username.isEmpty())
			{
				String path ="";
				if (attachments != null &&!attachments.isEmpty()) 
				  {
					path=saveAttachments(attachments);
				  }
				else 
					{
					  System.out.println("No attachments were uploaded."); 
				  }
				
				
				for(String clientEmail: data.getClients())
				{
					JSONObject kafkaMsg=new JSONObject();
					
					kafkaMsg.put("email", clientEmail);
					kafkaMsg.put("msgtitle", data.getEmailsubject());
					kafkaMsg.put("msgcontent", data.getEmailContent());
					kafkaMsg.put("msgType", "email");
					kafkaMsg.put("path", path);
					kafkaMsg.put("clientemail", user.getEmail());
					
					kafkaUtil.sendToKafka(kafkaMsg.toString());
				}
				
				
				result.put("status", "success");
				result.put("output", "Email Sent");
				
			}	
			else 
			{
				result.put("status", "failed");
				result.put("output", "Please login");
			}
		  }catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		
		  
		  return ResponseEntity.ok(result.toString()); 
	  
	  }
	  
	  public String saveAttachments(List<MultipartFile> attachments) {
		  AtomicReference<String> path = new AtomicReference<>("");

	        if (attachments != null && !attachments.isEmpty()) {
	            attachments.forEach(file -> {
	                try {
	                    // Get original filename
	                    String filename = file.getOriginalFilename();

	                    // Generate a unique 5-digit folder name
	                    String uniqueFolderName = String.format("%05d", new Random().nextInt(100000));

	                    // Construct the full path: base path + unique folder + file name
	                    String fullPath = attachmentBasePath + "/" + uniqueFolderName + "/" + filename;
	                    path.set(fullPath);

	                    // Create directories if they do not exist
	                    File destFile = new File(fullPath);
	                    destFile.getParentFile().mkdirs();

	                    // Write file to the destination path
	                    try (InputStream inputStream = file.getInputStream();
	                         FileOutputStream outputStream = new FileOutputStream(destFile)) {
	                        byte[] buffer = new byte[1024];
	                        int bytesRead;
	                        while ((bytesRead = inputStream.read(buffer)) != -1) {
	                            outputStream.write(buffer, 0, bytesRead);
	                        }
	                    }

	                    System.out.println("File saved at: " + path);

	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            });
	        }
	 
	        return path.get();
	}
	 
	@PostMapping("/generateemail")
	public ResponseEntity<?> generateEmail( @RequestBody GlobalInputPojo data) throws Exception 
	{
		JSONObject result=new JSONObject();
		try{
			System.out.println("generat AI is called....");
			String username = commonService.fetchUsernameAndValidateToken(data.getToken());
			ZapUser user = repo.findByUsername(username);
			
			if(username!=null && !username.isEmpty())
			{
				String msg=new GoogleAIService().callGenerateContentAPI(data.getPrompt());
				result.put("status", "success");
				result.put("output", msg);
				
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

