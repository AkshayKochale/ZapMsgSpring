package com.zap.authentication.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OAuth2Service 
{
	


    @Value("${oauth2.google.token-url}")
    private String googleTokenUrl;

    @Value("${oauth2.google.userinfo-url}")
    private String googleUserinfoUrl;

    @Value("${oauth2.google.client-id}")
    private String googleClientId;

    @Value("${oauth2.google.client-secret}")
    private String googleClientSecret;

    @Value("${oauth2.github.token-url}")
    private String githubTokenUrl;

    @Value("${oauth2.github.userinfo-url}")
    private String githubUserinfoUrl;

    @Value("${oauth2.github.client-id}")
    private String githubClientId;

    @Value("${oauth2.github.client-secret}")
    private String githubClientSecret;

    private final RestTemplate restTemplate;

    public OAuth2Service(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String exchangeCodeForAccessToken(String code, String provider) {
        String tokenUrl = getTokenUrl(provider);
        String clientId = getClientId(provider);
        String clientSecret = getClientSecret(provider);

        String url = UriComponentsBuilder.fromHttpUrl(tokenUrl)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .queryParam("redirect_uri", "http://localhost:5173") // Your redirect URI
                .queryParam("grant_type", "authorization_code")
                .toUriString();

        ResponseEntity<Map> response = restTemplate.postForEntity(url, null, Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (responseBody != null && responseBody.containsKey("access_token")) {
            return (String) responseBody.get("access_token");
        } else {
            throw new RuntimeException("Failed to retrieve access token");
        }
    }

    public String getUsernameFromAccessToken(String accessToken, String provider) {
        String userinfoUrl = getUserinfoUrl(provider);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(userinfoUrl, HttpMethod.GET, entity, Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (responseBody != null) {
            if (provider.equals("google")) {
                return (String) responseBody.get("name"); // Google returns 'name'
            } else if (provider.equals("github")) {
                return (String) responseBody.get("login"); // GitHub returns 'login'
            }
        }
        throw new RuntimeException("Failed to retrieve user information");
    }

    private String getTokenUrl(String provider) {
        return provider.equals("google") ? googleTokenUrl : githubTokenUrl;
    }

    private String getUserinfoUrl(String provider) {
        return provider.equals("google") ? googleUserinfoUrl : githubUserinfoUrl;
    }

    private String getClientId(String provider) {
        return provider.equals("google") ? googleClientId : githubClientId;
    }

    private String getClientSecret(String provider) {
        return provider.equals("google") ? googleClientSecret : githubClientSecret;
    }

	
}
