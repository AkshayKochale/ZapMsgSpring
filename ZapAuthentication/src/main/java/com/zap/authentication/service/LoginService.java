package com.zap.authentication.service;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zap.authentication.JwtUtil;
import com.zap.authentication.pojo.LoginReqPojo;

@RestController
public class LoginService 
{

		
	 	@Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private JwtUtil jwtUtil;

	    @Autowired
	    private ZapUserDetailsService userDetailsService;
	
	    
	    @Autowired
	    private OAuth2Service oauth2Service;
	
	    @PostMapping("/login")
	    public ResponseEntity<?> loginService(@RequestBody LoginReqPojo pojo) {
	        try {
	            authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(pojo.getUsername(), pojo.getPassword())
	            );
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	        }

	        final UserDetails userDetails = userDetailsService.loadUserByUsername(pojo.getUsername());
	        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

	        return ResponseEntity.ok(jwt);
	    }

		
		@GetMapping("/login/google")
		public String googleLogin() 
		{
			System.out.println("server hit");
			return "redirect:/oauth2/authorization/google";
		}
	
		@GetMapping("/login/github")
		public String gitHublogin() 
		{
			System.out.println("server hit");
			return "redirect:/oauth2/authorization/google";
		}
	
		
	
	  @PostMapping("/login/token")
	  public ResponseEntity<Map<String, String>> getToken(@RequestBody Map<String, String> payload)
	  {
		  System.out.println("token serviec called");
		  String code =payload.get("code");
		  String provider=payload.get("provider");
	  
	  // Exchange the code for an access token 
		  String accessToken =oauth2Service.exchangeCodeForAccessToken(code,provider);
	  
	  // Use the access token to get user details
		  String username =oauth2Service.getUsernameFromAccessToken(accessToken,provider);
	  
	  // Generate JWT 
		  	String jwtToken = jwtUtil.generateToken(username);
	  
		  	System.out.println(jwtToken);
		  	
	  // Return JWT
		  	Map<String, String> response = new HashMap<>();
		  	response.put("token", jwtToken); 
	  		return ResponseEntity.ok(response); 
	  }
	 
	  @PostMapping("/login/validate")
	  public ResponseEntity<?> validateExternalToken(@RequestBody String token) throws Exception
	  {
		  JSONObject obj=new JSONObject();
		  try 
		  {
			  System.out.println("token :"+token);
			  String extractUsername = jwtUtil.extractUsername(token);
			  UserDetails userDetails = userDetailsService.loadUserByUsername(extractUsername);
			  boolean tokenValidation=false;
			  
			  if(userDetails!=null) 
				  tokenValidation = jwtUtil.validateToken(token, userDetails);
			
			 
			  obj.put("valid", tokenValidation);
			  obj.put("username", userDetails.getUsername());
			  
		  }
		  catch(Exception e) 
		  {
			  obj.put("valid", "Error");
		  }
		  
		  return ResponseEntity.ok(obj);
		  
	  }
			
}
