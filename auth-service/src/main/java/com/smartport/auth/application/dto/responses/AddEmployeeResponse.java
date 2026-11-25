package com.smartport.auth.application.dto.responses;

import com.smartport.auth.domain.enums.EmployeeStatus;
import java.util.UUID;

public record AddEmployeeResponse(
    UUID userId,
    String firstName,
    String lastName,
    String email,
    EmployeeStatus status,
    String message
) {}
