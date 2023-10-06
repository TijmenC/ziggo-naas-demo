package com.naasdemo.ziggonaasdemo;

import com.naasdemo.ziggonaasdemo.service.PinService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZiggoNaasDemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(ZiggoNaasDemoApplication.class, args);

		PinService pinService = new PinService();

		pinService.startWireMockServer();
	}

}
