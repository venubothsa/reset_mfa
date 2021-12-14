package com.credit.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ResetMfaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResetMfaApplication.class, args);
	}

}
