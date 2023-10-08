package com.naasdemo.ziggonaasdemo.model;

public class PinTerminal {
    private final Integer customerId;
    private final String macAddress;

    public PinTerminal(Integer customerId, String macAddress) {
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
