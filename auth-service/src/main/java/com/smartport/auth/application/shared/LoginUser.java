package com.smartport.auth.application.shared;

import java.util.UUID;

/** Minimal projection used by the login use case. */
public record LoginUser(UUID userId, String email, String passwordHash) {
}
