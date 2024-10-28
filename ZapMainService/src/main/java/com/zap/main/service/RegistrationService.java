package com.zap.main.service;

import java.util.regex.Pattern;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zap.main.dao.ZapUser;
import com.zap.main.pojo.ZapUserPojo;
import com.zap.main.repo.ZapUserRepo;

@RestController
@RequestMapping("/registration")
public class RegistrationService
{
		@Autowired
		ZapUserRepo repo;
	
		final static String specialChars = "!\"#$%&'()*+,-./:;<=>?[\\]^`{|}~@_";
		
	 @GetMapping("/validate_username/{username}")
	 public ResponseEntity<?> validateUsername(@PathVariable String username) 
	 {
		 JSONObject result= new JSONObject();
		 try 
		 {
			 if(username!=null && containsSpecialCharacter(username,specialChars))
			 {
				 result.put("validate", "Username cannot have special character");
			 }
			 else 
			 {
				 ZapUser findByUsername = repo.findByUsername(username);
				 if(findByUsername!=null)
				 {
					 result.put("validate", "Username is already taken");
				 }
				 else 
				 {
					 result.put("validate", "");
				 }
			 }
			
		 }
		 catch(Exception e) 
		 {
			 e.printStackTrace();	
		 }
		 
		 
		 return ResponseEntity.ok(result.toString());
	 }
	 
	 public static boolean containsSpecialCharacter(String input, String specialCharacters) {
		 
		 	for(int i=0;i<input.length();i++)
		 	{
		 		if(specialCharacters.contains(input.charAt(i)+""))return true;
		 	}
		 
		 	return false;
	    }
	 
	 @GetMapping("/validate_password_strength/{password}")
	 public ResponseEntity<?> validatePasswordStrength(@PathVariable String password) 
	 {
		 JSONObject result= new JSONObject();
		 try 
		 {
			boolean oneUpper=false;
			boolean oneSpecialChar=false;
			boolean oneDigit=false;
			
			
			if(password.length()<8)
			{
				result.put("validate", "Weak Password");
			}
			else 
			{
				for(int i=0;i<password.length();i++)
				{
					char ch=password.charAt(i);
					
					if(Character.isUpperCase(ch))oneUpper=true;
					if(specialChars.contains(ch+""))oneSpecialChar=true;
					if(Character.isDigit(ch))oneDigit=true;
				}
				
				if(oneUpper && oneSpecialChar && oneDigit)
				{
					result.put("validate", "Strong Password");
				}
				else 
				{
					result.put("validate", "Weak Password");
				}
				
			}
			 
			 
		 }
		 catch(Exception e) 
		 {
			 e.printStackTrace();	
		 }
		 
		 
		 return ResponseEntity.ok(result.toString());
	 }
	
	 @PostMapping("/register")
	 public ResponseEntity<?> register(@RequestBody ZapUserPojo pojo)
	 {
		 JSONObject result= new JSONObject();
		 
		 try 
		 {
			 System.out.println("registration called.....");
			 String validateForm = validateForm(pojo);
			 
			 if(validateForm.isEmpty())
			 {
				 ZapUser user = getObjectFromPojo(pojo,null);
				 repo.save(user);
				 result.put("output", "Sucessfully Registered");
			 }
			 else 
			 {
				 result.put("output", validateForm); 
			 }
			 
		 }catch(Exception e) 
		 {
			 e.printStackTrace();
		 }
		 
		 return ResponseEntity.ok(result.toString());
	 }
	 
	 @PostMapping("/register_update")
	 public ResponseEntity<?> registertationUpdate(@RequestBody ZapUserPojo pojo)
	 {
		 JSONObject result= new JSONObject();
		 
		 try 
		 {
			 String validateForm = validateForm(pojo);
			 
			 if(validateForm.isEmpty())
			 {
				 ZapUser prevObj = repo.findByUsername(pojo.getUsername());
				 
				 if(prevObj==null) 
				 {
					 result.put("output", "Invalid User");
				 }
				 else 
				 {	
					 ZapUser user = getObjectFromPojo(pojo,prevObj);
					 repo.save(user);
					 result.put("output", "Sucessfully Updated");
				 }
			 }
			 else 
			 {
				 result.put("output", validateForm); 
			 }
			 
		 }catch(Exception e) 
		 {
			 e.printStackTrace();
		 }
		 
		 return ResponseEntity.ok(result);
	 }
	 
	 @GetMapping("/profile_updation_req/{username}")
	 public ResponseEntity<?> profileUpdationReq(@PathVariable String username) 
	 {
		 JSONObject result= new JSONObject();
		 
		 try 
		 {
			 ZapUser user = repo.findByUsername(username);
			 
			 if(user!=null)
			 {
				 result.put("output", "Invalid User");
			 }
			 else 
			 {
				 int count=0;
				 
				 if(user.getFirstname()==null)count++;
				 if(user.getLastname()==null)count++;
				 if(user.getUsername()==null)count++;
				 if(user.getEmail()==null)count++;
				 if(user.getCountry()==null)count++;
				 if(user.getPhoneno()==null)count++;
				 
				 result.put("ouput", count);
			 }	
		 }
		 catch(Exception e) 
		 {
			 e.printStackTrace();
		 }
		 
		 return ResponseEntity.ok(result);
	 }
	 
	 public ZapUser getObjectFromPojo(ZapUserPojo pojo,ZapUser prevUserObject) 
	 {
		 ZapUser user=null;
		 
		 if(prevUserObject==null)user=new ZapUser();
		 else user=prevUserObject;
				 
		 user.setFirstname(pojo.getFirstname());
		 user.setLastname(pojo.getLastname());
		 user.setUsername(pojo.getUsername());
		 user.setPassword(new BCryptPasswordEncoder().encode(pojo.getPassword()));
		 user.setEmail(pojo.getEmail());
		 user.setCountry(pojo.getCountry());
		 user.setPhoneno(pojo.getPhone());
		 
		 return user;
	 }
	 
	 public String validateForm(ZapUserPojo pojo) 
	 {
		 String msg="";
		 String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                 						"(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		 
		 System.out.println(pojo);
		 Pattern emailPattern = Pattern.compile(emailRegex);
		 
		 String phoneRegex = "^[0-9]{10}$";
		 Pattern phonePattern = Pattern.compile(phoneRegex);
		 
		 if(pojo.getFirstname()==null)
			 msg="First Name cannot be Empty";
		 else if(pojo.getLastname()==null)
			 msg="Last Name cannot be Empty";
		 else if(pojo.getUsername()==null)
			 msg="Username cannot be Empty";
		 else if(pojo.getEmail()==null)
			 msg="Email cannot be Empty";
		 else if(pojo.getCountry()==null)
			 msg="Country cannot be Empty";
		 else if(pojo.getPhone()==null)
			 msg="Phone Number cannot be Empty";
		 else if(pojo.getPassword()==null)
			 msg="Password cannot be Empty";
		 else if(!emailPattern.matcher(pojo.getEmail()).matches())
			 msg="Invalid Email";
		 else if(!phonePattern.matcher(pojo.getPhone()).matches())
			 msg="Invalid Phone Number";
		 
		
		 return msg;
	 }
	
	 
	
}
