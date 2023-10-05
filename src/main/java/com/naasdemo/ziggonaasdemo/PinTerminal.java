package com.naasdemo.ziggonaasdemo;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PinTerminal {
    public static void activatePinTerminal(String customerId, String macAddress) {
        // Construct the URL for the southbound system
        String southboundUrl = "http://southbound-system-api/activate";

        // Create JSON payload for the request
        String requestBody = "{\"customerId\": \"" + customerId + "\", \"macAddress\": \"" + macAddress + "\"}";

        try {
            // Open connection
            URL url = new URL(southboundUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Send request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get response
            int responseCode = connection.getResponseCode();

            // Read and process response
            if (responseCode == 200) {
                // Successful activation
                System.out.println("PIN terminal activated successfully for customer " + customerId);
                sendStatusToOrchestrator("SUCCESS");
            } else {
                // Activation failed
                System.out.println("Failed to activate PIN terminal. Response code: " + responseCode);
                sendStatusToOrchestrator("FAILURE");
            }

        } catch (Exception e) {
            e.printStackTrace();
            sendStatusToOrchestrator("ERROR");
        }
    }

    private static void sendStatusToOrchestrator(String status) {
        // Implement logic to send status to orchestrator
        System.out.println("Sending status to orchestrator: " + status);
    }
}
