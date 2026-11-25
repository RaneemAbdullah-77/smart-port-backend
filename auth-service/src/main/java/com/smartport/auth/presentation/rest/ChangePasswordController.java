package com.smartport.auth.presentation.rest;

import com.smartport.auth.application.dto.requests.*;
import com.smartport.auth.application.dto.responses.*;
import com.smartport.auth.application.usecases.ChangePasswordCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Set-password (first time) and reset-password (after "forgot password") share this endpoint. */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ChangePasswordController {

    private final ChangePasswordCommand command;

    @PostMapping("/password")
    public ResponseEntity<ChangePasswordResponse> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {
        var response = command.execute(request);
        return response.success()
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(response);
    }
}
