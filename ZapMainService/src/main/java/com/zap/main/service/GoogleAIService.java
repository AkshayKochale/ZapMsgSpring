package com.zap.main.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GoogleAIService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_KEY = "AIzaSyDPECtkXNRVsibef63VCn1gN_wuwy1yNbU";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";

    
    public String callGenerateContentAPI(String prompt) {
        // Construct the request URL with API key
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("key", API_KEY)
                .toUriString();

        // Set headers for JSON content
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the complex request body structure
        Map<String, Object> partsMap = new HashMap<>();
        partsMap.put("text", prompt);

        List<Map<String, Object>> partsList = new ArrayList<>();
        partsList.add(partsMap);

        Map<String, Object> contentsMap = new HashMap<>();
        contentsMap.put("parts", partsList);

        List<Map<String, Object>> contentsList = new ArrayList<>();
        contentsList.add(contentsMap);

        Map<String, Object> body = new HashMap<>();
        body.put("contents", contentsList);


        // Create the HTTP entity
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        // Make the POST request
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            JSONObject obj=new JSONObject(response.getBody());
            String output=obj.getJSONArray("candidates")
            		.getJSONObject(0)
            			.getJSONObject("content")
            			.getJSONArray("parts").getJSONObject(0).getString("text");
            
            System.out.println(output);
           
            return output;
            
        } catch (Exception e) {
            System.err.println("Error calling API: " + e.getMessage());
            
            return "";
        }
    }
}
