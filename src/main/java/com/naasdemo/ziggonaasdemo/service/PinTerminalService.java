package com.naasdemo.ziggonaasdemo.service;

import java.net.http.HttpRequest;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.naasdemo.ziggonaasdemo.config.WireMockConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public class PinTerminalService {
    private static final Logger logger = LogManager.getLogger(PinTerminalService.class);
    private static WireMockServer wireMockServer;

    public void startWireMockServer() {
        // Start WireMock Server
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8081));
        wireMockServer.start();
        configureFor("localhost", 8081);

        // Configure WireMock stubs
        WireMockConfigurator.configureWireMockStubs();
    }

    public void stopWireMockserver() {
        // Stop the WireMock server
        wireMockServer.stop();
    }

    public static String handleWireMockResponse(int statusCode, HttpRequest request) {
        switch (statusCode) {
            case 201:
                logger.info("StatusCode: " + statusCode +  " The PIN terminal was not attached to any customer ID and has been successfully activated." + " Request: " + request);
                return "ACTIVE";
            case 404:
                logger.warn("StatusCode: " + statusCode + " The PIN terminal was not registered in the southbound system and cannot be activated");
                return "INACTIVE";
            case 409:
                logger.warn("StatusCode: " + statusCode + " The PIN terminal is already attached to a different customer ID and cannot be activated for the new customer");
                return "INACTIVE";
            default:
                logger.warn("Unknown status code detected:" + statusCode);
                return "UNKNOWN";
        }
    }
}
