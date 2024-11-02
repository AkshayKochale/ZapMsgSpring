package com.zap.notification;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;



public class TestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get the current time
        LocalDateTime currentTime = LocalDateTime.now();

        // Get the requested URL
        String requestUrl = request.getRequestURI();

        String remoteAddr = request.getRemoteAddr();
        
        // Print the time and URL to the console
        System.out.println("Incoming request at " + currentTime + " for URL: " + requestUrl+" : "+ remoteAddr);

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
