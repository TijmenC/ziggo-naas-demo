package com.naasdemo.ziggonaasdemo;

import com.naasdemo.ziggonaasdemo.controller.PinController;
import com.naasdemo.ziggonaasdemo.model.Pin;
import com.naasdemo.ziggonaasdemo.service.PinService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZiggoNaasDemoApplication {
	private static final Logger logger = LogManager.getLogger(ZiggoNaasDemoApplication.class);

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

		logger.info("Result for pinSuccessful: " + resultSuccessful);
		logger.info("Result for pinNotRegistered: " + resultNotRegistered);
		logger.info("Result for pinIsAttached: " + resultIsAttached);
	}

}
