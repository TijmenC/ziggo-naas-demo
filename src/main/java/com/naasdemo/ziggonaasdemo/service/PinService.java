package com.naasdemo.ziggonaasdemo.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.naasdemo.ziggonaasdemo.config.WireMockConfigurator;
import com.naasdemo.ziggonaasdemo.model.Pin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public class PinService {
    private static final Logger logger = LogManager.getLogger(PinService.class);
    private static final String SOUTHBOUND_URL = "http://localhost:8081/activate";

    public void startWireMockServer() {

        // Start WireMock Server
        WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8081));
        wireMockServer.start();
        configureFor("localhost", 8081);

        // Configure WireMock stubs
        WireMockConfigurator.configureWireMockStubs();

        Pin pinSuccessful = new Pin(12345, "AA:BB:CC:DD:EE:FF");
        Pin pinNotRegistered = new Pin(12345, "AA:BB:CC:DD:EE:AA");
        Pin pinIsAttached = new Pin(11111, "AA:BB:CC:DD:EE:FF");

        activatePinTerminal(pinSuccessful);
        activatePinTerminal(pinNotRegistered);
        activatePinTerminal(pinIsAttached);

        // Stop WireMock Server after tests
        wireMockServer.stop();
    }


    public static String activatePinTerminal(Pin pin) {
        // Create JSON payload for the request
        String requestBody = "{\"customerId\": \"" + pin.getCustomerId() + "\", \"macAddress\": \"" + pin.getMacAddress() + "\"}";

        try {
            // Build URI
            URI uri = new URI(SOUTHBOUND_URL);

            // Create HttpClient
            HttpClient httpClient = HttpClient.newHttpClient();

            // Build the request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            // Send the request and get the response
            HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

            // Read and process response
            return handleWireMockResponse(response.statusCode(), response.request());

        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static String handleWireMockResponse(int statusCode, HttpRequest request) {
        switch (statusCode) {
            case 201:
                logger.info("StatusCode: " + statusCode +"The PIN terminal was not attached to any customer ID and has been successfully activated." + "Request:" + request);
                return("ACTIVE");
            case 404:
                logger.warn("StatusCode: " + statusCode + "The PIN terminal was not registered in the southbound system and cannot be activated");
                return("INACTIVE");
            case 409:
                logger.warn("StatusCode: " + statusCode + "The PIN terminal is already attached to a different customer ID and cannot be activated for the new customer");
                return("INACTIVE");
            default:
                logger.warn("Unknown status code detected:" + statusCode);
                return("UNKNOWN");
        }
    }


}
