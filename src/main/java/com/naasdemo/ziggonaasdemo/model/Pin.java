package com.naasdemo.ziggonaasdemo.model;

public class Pin {
    private final Integer customerId;
    private final String macAddress;

    public Pin(Integer customerId, String macAddress) {
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
