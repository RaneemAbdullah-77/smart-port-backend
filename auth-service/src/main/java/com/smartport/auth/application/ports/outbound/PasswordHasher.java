package com.smartport.auth.application.ports.outbound;

public interface PasswordHasher {

    String hash(String password);

    boolean verify(String password, String hash);
}
