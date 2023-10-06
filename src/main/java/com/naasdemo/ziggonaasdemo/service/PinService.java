package com.naasdemo.ziggonaasdemo.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.naasdemo.ziggonaasdemo.config.WireMockConfigurator;
import com.naasdemo.ziggonaasdemo.model.Pin;
import org.apache.logging.log4j.LogManager;

public class PinService {
    private static Logger logger = (Logger) LogManager.getLogger(WireMockConfigurator.class);
    private static final String SOUTHBOUND_URL = "http://localhost:8080/activate"; // Replace with the actual WireMock URL

    public static void main(String[] args) {
        WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8080));
        wireMockServer.start();

        // Configure WireMock stubs
        WireMockConfigurator.configureWireMockStubs();

        Pin pinSuccesfull = new Pin(12345, "AA:BB:CC:DD:EE:FF");
        Pin pinNotRegistered = new Pin(12345, "AA:BB:CC:DD:EE:AA");
        Pin pinIsAttachted = new Pin(1111, "AA:BB:CC:DD:EE:AA");

        activatePinTerminal(pinSuccesfull);
        activatePinTerminal(pinNotRegistered);
        activatePinTerminal(pinIsAttachted);

        
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
                logger.info("Status code:" + statusCode +"The PIN terminal was not attached to any customer ID and has been successfully activated." + "Request:" + request);
                return("ACTIVE");
            case 404:
                logger.warning("Status code:" + statusCode + "The PIN terminal was not registered in the southbound system and cannot be activated");
                return("INACTIVE");
            case 409:
                logger.warning("Status code:" + statusCode + "The PIN terminal is already attached to a different customer ID and cannot be activated for the new customer");
                return("INACTIVE");
            default:
                logger.warning("Unknown status code detected:" + statusCode);
                return("UNKNOWN");
        }
    }


}
