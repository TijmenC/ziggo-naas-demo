package com.naasdemo.ziggonaasdemo.controller;

import com.naasdemo.ziggonaasdemo.model.PinTerminal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static com.naasdemo.ziggonaasdemo.service.PinTerminalService.handleWireMockResponse;

public class PinTerminalController {
    private static final String SOUTHBOUND_URL = "http://localhost:8081/activate";

    public String activatePinTerminal(PinTerminal pinTerminal) {
        // Create JSON payload for the request
        String requestBody = "{\"customerId\": \"" + pinTerminal.getCustomerId() + "\", \"macAddress\": \"" + pinTerminal.getMacAddress() + "\"}";

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
}
