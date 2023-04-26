package com.team033.teamup;

import com.team033.teamup.util.LoginInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TeamUpApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(TeamUpApplication.class, args);
	}

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		// login interceptor
//		registry.addInterceptor(new LoginInterceptor());
//	}

}
