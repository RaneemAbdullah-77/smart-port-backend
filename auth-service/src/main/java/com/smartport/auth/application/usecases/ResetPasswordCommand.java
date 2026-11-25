package com.smartport.auth.application.usecases;

import com.smartport.auth.application.dto.requests.*;
import com.smartport.auth.application.dto.responses.*;
import com.smartport.auth.application.ports.outbound.AuthenticationRepository;
import com.smartport.auth.application.ports.outbound.PasswordHasher;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Kept for parity with the .NET code; its controller was commented out there (ChangePasswordCommand is the live endpoint). */
@Service
@RequiredArgsConstructor
public class ResetPasswordCommand {

    private final AuthenticationRepository authenticationRepository;
    private final PasswordHasher passwordHasher;

    @Transactional
    public ResetPasswordResponse execute(ResetPasswordRequest request) {
        var resetToken = authenticationRepository.getPasswordResetToken(request.token()).orElse(null);

        if (resetToken == null) {
            return new ResetPasswordResponse(false, "Invalid reset link.");
        }
        if (resetToken.isUsed()) {
            return new ResetPasswordResponse(false, "This link has already been used.");
        }
        if (!resetToken.getExpireAt().isAfter(Instant.now())) {
            return new ResetPasswordResponse(false, "Reset link expired.");
        }

        authenticationRepository.resetPassword(
                resetToken.getUserId(), passwordHasher.hash(request.newPassword()));

        resetToken.setUsed(true);
        authenticationRepository.updatePasswordResetToken(resetToken);

        return new ResetPasswordResponse(true, "Password reset successfully.");
    }
}
