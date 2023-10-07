# ZiggoNaasDemo Application

## Overview
ZiggoNaasDemo is a Java application that demonstrates the interaction between a northbound and a southbound system using WireMock for simulating HTTP responses. The application is designed to activate PIN terminals based on certain conditions, such as whether the terminal is attached to a customer ID or registered in the southbound system.

## Components

### 1. ZiggoNaasDemoApplication
- **Location**: `com.naasdemo.ziggonaasdemo.ZiggoNaasDemoApplication`
- **Description**: The main class of the application that initializes the Spring Boot application, starts the WireMock server, activates PIN terminals, and logs the results.

### 2. PinService
- **Location**: `com.naasdemo.ziggonaasdemo.service.PinService`
- **Description**: Manages the WireMock server, starts and stops it, and handles responses based on the simulated HTTP status codes.

### 3. Pin
- **Location**: `com.naasdemo.ziggonaasdemo.model.Pin`
- **Description**: Represents a PIN with attributes such as customer ID and MAC address.

### 4. PinController
- **Location**: `com.naasdemo.ziggonaasdemo.controller.PinController`
- **Description**: Sends HTTP requests to activate PIN terminals, converts PIN objects to JSON payloads, and processes the WireMock responses.

### 5. WireMockConfigurator
- **Location**: `com.naasdemo.ziggonaasdemo.config.WireMockConfigurator`
- **Description**: Configures WireMock stubs for different scenarios, such as successful activation, inactive terminal, and terminal already attached.

## Usage
1. Run the `ZiggoNaasDemoApplication` class to start the application.
2. The application will activate PIN terminals with different scenarios (success, not registered, already attached).
3. View the results in the logs, which will indicate the status of each activation attempt.

## Dependencies
- Spring Boot
- Apache Log4j
- WireMock
