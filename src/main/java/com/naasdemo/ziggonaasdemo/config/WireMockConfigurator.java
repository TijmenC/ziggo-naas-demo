package com.naasdemo.ziggonaasdemo.config;
import com.github.tomakehurst.wiremock.client.WireMock;

public class WireMockConfigurator {

    public static void configureWireMockStubs() {
        // Configures WireMock for appropriate requests
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/activate"))
                .withRequestBody(WireMock.equalToJson("{\"customerId\": \"12345\", \"macAddress\": \"AA:BB:CC:DD:EE:FF\"}"))
                .willReturn(WireMock.aResponse().withStatus(201)));

        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/activate"))
                .withRequestBody(WireMock.equalToJson("{\"customerId\": \"12345\", \"macAddress\": \"AA:BB:CC:DD:EE:AA\"}"))
                .willReturn(WireMock.aResponse().withStatus(404)));

        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/activate"))
                .withRequestBody(WireMock.equalToJson("{\"customerId\": \"11111\", \"macAddress\": \"AA:BB:CC:DD:EE:FF\"}"))
                .willReturn(WireMock.aResponse().withStatus(409)));
    }
}
