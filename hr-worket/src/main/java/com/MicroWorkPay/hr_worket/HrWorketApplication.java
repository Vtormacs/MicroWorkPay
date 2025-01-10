package com.MicroWorkPay.hr_worket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient	// Registra o serviço no Eureka
public class HrWorketApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrWorketApplication.class, args);
	}

}
