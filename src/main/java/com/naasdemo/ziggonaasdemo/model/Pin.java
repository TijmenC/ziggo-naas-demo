package com.naasdemo.ziggonaasdemo.model;

public class Pin {
    private final long customerId;
    private final String macAddress;

    public Pin(long customerId, String macAddress) {
        this.customerId = customerId;
        this.macAddress = macAddress;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getMacAddress() {
        return macAddress;
    }

}
