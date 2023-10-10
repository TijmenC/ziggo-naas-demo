package com.naasdemo.ziggonaasdemo;

import com.naasdemo.ziggonaasdemo.controller.PinTerminalController;
import com.naasdemo.ziggonaasdemo.model.PinTerminal;
import com.naasdemo.ziggonaasdemo.service.PinTerminalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZiggoNaasDemoApplication {
    private static final Logger logger = LogManager.getLogger(ZiggoNaasDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ZiggoNaasDemoApplication.class, args);

        PinTerminalService pinTerminalService = new PinTerminalService();

        PinTerminalController pinTerminalController = new PinTerminalController();

        // Start the WireMock server
        pinTerminalService.startWireMockServer();

        // Create 3 Pin Terminal scenarios which correlates to the WireMock stubs
        PinTerminal pinTerminalSuccessful = new PinTerminal(12345, "AA:BB:CC:DD:EE:FF");
        PinTerminal pinTerminalNotRegistered = new PinTerminal(12345, "AA:BB:CC:DD:EE:AA");
        PinTerminal pinTerminalIsAttached = new PinTerminal(11111, "AA:BB:CC:DD:EE:FF");

        // Make the API calls to the Southbound system and save the responses
        String resultSuccessful = pinTerminalController.activatePinTerminal(pinTerminalSuccessful);
        String resultNotRegistered = pinTerminalController.activatePinTerminal(pinTerminalNotRegistered);
        String resultIsAttached = pinTerminalController.activatePinTerminal(pinTerminalIsAttached);

        // Stop the WireMock server
        pinTerminalService.stopWireMockserver();

        // Log the results of each call
        logger.info("Result for pinTerminalSuccessful: " + resultSuccessful);
        logger.info("Result for pinTerminalNotRegistered: " + resultNotRegistered);
        logger.info("Result for pinTerminalIsAttached: " + resultIsAttached);
    }
}
