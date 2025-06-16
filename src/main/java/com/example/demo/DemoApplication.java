package com.example.demo;

import com.example.demo.middleware.RateLimitingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilter() {
		FilterRegistrationBean<RateLimitingFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new RateLimitingFilter());
		registrationBean.addUrlPatterns("/api/transactions/*"); // Register filter for API endpoints
		return registrationBean;
	}

}
