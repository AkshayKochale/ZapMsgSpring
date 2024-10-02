package com.zap.main.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="ZapAuthentication")
public interface ZapAuthClient {

	
	@PostMapping("/login/validate")
	public ResponseEntity<?> getAutheticationStatus(@RequestBody String token);
	
}
