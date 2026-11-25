package com.smartport.auth.application.usecases;

import com.smartport.auth.application.dto.requests.*;
import com.smartport.auth.application.dto.responses.*;
import com.smartport.auth.application.ports.outbound.AuthenticationRepository;
import com.smartport.auth.application.ports.outbound.PasswordHasher;
import com.smartport.auth.domain.entities.PasswordResetToken;
import com.smartport.auth.domain.entities.SetPasswordToken;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Handles both "set password" (first time) and "reset password" (forgot password) tokens. */
@Service
@RequiredArgsConstructor
public class ChangePasswordCommand {

    private final AuthenticationRepository authenticationRepository;
    private final PasswordHasher passwordHasher;

    @Transactional
    public ChangePasswordResponse execute(ChangePasswordRequest request) {
        var setPasswordToken = authenticationRepository.getSetPasswordToken(request.token());
        if (setPasswordToken.isPresent()) {
            return handleSetPassword(setPasswordToken.get(), request.newPassword());
        }

        var resetPasswordToken = authenticationRepository.getPasswordResetToken(request.token());
        if (resetPasswordToken.isPresent()) {
            return handleResetPassword(resetPasswordToken.get(), request.newPassword());
        }

        return new ChangePasswordResponse(false, "Invalid token.");
    }

    // ===================== SET PASSWORD =====================

    private ChangePasswordResponse handleSetPassword(SetPasswordToken token, String newPassword) {
        if (token.isUsed()) {
            return new ChangePasswordResponse(false, "Token has already been used.");
        }
        if (!token.getExpireAt().isAfter(Instant.now())) {
            return new ChangePasswordResponse(false, "Token has expired.");
        }
        if (authenticationRepository.hasActivePassword(token.getUserId())) {
            return new ChangePasswordResponse(false, "Password is already set.");
        }

        authenticationRepository.setPassword(token.getUserId(), passwordHasher.hash(newPassword));

        token.setUsed(true);
        authenticationRepository.updateSetPasswordToken(token);

        return new ChangePasswordResponse(true, "Password has been set successfully.");
    }

    // ===================== RESET PASSWORD =====================

    private ChangePasswordResponse handleResetPassword(PasswordResetToken token, String newPassword) {
        if (token.isUsed()) {
            return new ChangePasswordResponse(false, "This link has already been used.");
        }
        if (!token.getExpireAt().isAfter(Instant.now())) {
            return new ChangePasswordResponse(false, "Reset link expired.");
        }

        authenticationRepository.resetPassword(token.getUserId(), passwordHasher.hash(newPassword));

        token.setUsed(true);
        authenticationRepository.updatePasswordResetToken(token);

        return new ChangePasswordResponse(true, "Password reset successfully.");
    }
}
