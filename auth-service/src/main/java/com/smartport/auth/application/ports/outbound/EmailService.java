package com.smartport.auth.application.ports.outbound;

public interface EmailService {

    void send(String to, String subject, String htmlBody);
}
