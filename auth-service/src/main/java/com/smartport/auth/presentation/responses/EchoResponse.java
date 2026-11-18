package com.smartport.auth.presentation.responses;

public class EchoResponse {

    private boolean received;
    private String message;

    public EchoResponse(boolean received, String message) {
        this.received = received;
        this.message = message;
    }

    public boolean isReceived() {
        return received;
    }

    public String getMessage() {
        return message;
    }
}