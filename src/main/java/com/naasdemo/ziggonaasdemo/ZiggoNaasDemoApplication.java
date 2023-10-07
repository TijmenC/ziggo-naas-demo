package com.naasdemo.ziggonaasdemo;

import com.naasdemo.ziggonaasdemo.controller.PinController;
import com.naasdemo.ziggonaasdemo.model.Pin;
import com.naasdemo.ziggonaasdemo.service.PinService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZiggoNaasDemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(ZiggoNaasDemoApplication.class, args);

		PinService pinService = new PinService();

		PinController pinController = new PinController();

		pinService.startWireMockServer();

		Pin pinSuccessful = new Pin(12345, "AA:BB:CC:DD:EE:FF");
		Pin pinNotRegistered = new Pin(12345, "AA:BB:CC:DD:EE:AA");
		Pin pinIsAttached = new Pin(11111, "AA:BB:CC:DD:EE:FF");

		String resultSuccessful = pinController.activatePinTerminal(pinSuccessful);
		String resultNotRegistered = pinController.activatePinTerminal(pinNotRegistered);
		String resultIsAttached = pinController.activatePinTerminal(pinIsAttached);

		pinService.stopWireMockserver();

		System.out.println("Result for pinSuccessful: " + resultSuccessful);
		System.out.println("Result for pinNotRegistered: " + resultNotRegistered);
		System.out.println("Result for pinIsAttached: " + resultIsAttached);
	}

}
