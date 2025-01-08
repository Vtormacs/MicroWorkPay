package com.MicroWorkPay.hr_playroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HrPlayrollApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrPlayrollApplication.class, args);
	}

}
