package com.zap.authentication;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.zap.authentication.dao.ZapUser;
import com.zap.authentication.repo.ZapUserRepo;
import com.zap.authentication.util.Util;

@Component
public class OauthSuccessHandler  extends SimpleUrlAuthenticationSuccessHandler
{
	@Autowired
	ZapUserRepo repo;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtil jwtUtil;
	

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws IOException, ServletException 
	{
	    if (authentication.getPrincipal() instanceof OAuth2User) 
	    {
	        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
	        Map<String, Object> attributes = oAuth2User.getAttributes();
	        System.out.println(attributes);
	        saveOauthLoginUserToDataBase(attributes);
	        
	        String jwtToken = jwtUtil.generateToken(oAuth2User.getName());

	     // Redirect to frontend with JWT token as a query parameter
	        String redirectUrl = "http://localhost:5173/oauth-success?token=" + jwtToken;
	        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	    }

	    super.onAuthenticationSuccess(request, response, authentication);
	}
	
	public void saveOauthLoginUserToDataBase(Map<String, Object> attributes) 
	{
		ZapUser oauthUser = repo.findByUsername((String)attributes.get("name"));
		
		if(oauthUser==null)
		{
			oauthUser=new ZapUser();
			if(attributes.containsKey("name"))
				oauthUser.setUsername((String)attributes.get("name"));
			else
				oauthUser.setUsername((String)attributes.get("login"));
				
			if(attributes.containsKey("email") && attributes.get("email")!=null)
				oauthUser.setEmail((String)attributes.get("email"));
			else
				oauthUser.setEmail("Not Avaialble");	
			
			oauthUser.setPassword(passwordEncoder.encode(Util.RandomPasswordGenerator()));
			repo.save(oauthUser);
		}
		
	}

	
}
