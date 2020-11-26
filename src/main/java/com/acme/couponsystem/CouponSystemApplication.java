package com.acme.couponsystem;

import com.acme.couponsystem.db.storage.StorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(StorageConfig.class)
public class CouponSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponSystemApplication.class, args);
	}
}
