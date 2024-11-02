package com.zap.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ZapNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZapNotificationApplication.class, args);
	}
	
	@Bean
	public CorsFilter corsFilter() {
	    CorsConfiguration config = new CorsConfiguration();
	    config.addAllowedOriginPattern("*");
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("*");
	    config.setAllowCredentials(true);
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", config);
	    return new CorsFilter(source);
	}


    @Bean
    public FilterRegistrationBean<TestFilter> loggingFilter() {
        FilterRegistrationBean<TestFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new TestFilter());
        registrationBean.addUrlPatterns("/*"); // Apply to all URLs
        registrationBean.setOrder(1); // Set order of execution if you have multiple filters

        return registrationBean;
    }	

}
