package com.smartport.auth.application.usecases;

import com.smartport.auth.application.dto.requests.*;
import com.smartport.auth.application.dto.responses.*;
import com.smartport.auth.application.ports.outbound.AuthenticationRepository;
import com.smartport.auth.application.ports.outbound.EmailService;
import com.smartport.auth.domain.entities.PasswordResetToken;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ForgotPasswordCommand {

    private final AuthenticationRepository authenticationRepository;
    private final EmailService emailService;
    private final String frontendUrl;
    private final long tokenMinutes;

    public ForgotPasswordCommand(
            AuthenticationRepository authenticationRepository,
            EmailService emailService,
            @Value("${app.frontend-url}") String frontendUrl,
            @Value("${app.reset-password-token-minutes:15}") long tokenMinutes) {
        this.authenticationRepository = authenticationRepository;
        this.emailService = emailService;
        this.frontendUrl = frontendUrl;
        this.tokenMinutes = tokenMinutes;
    }

    @Transactional
    public ForgotPasswordResponse execute(ForgotPasswordRequest request) {
        var userId = authenticationRepository.getUserIdByEmail(request.email()).orElse(null);

        // Do not reveal whether the e-mail exists
        if (userId == null) {
            return new ForgotPasswordResponse("If the email exists, a reset link has been sent.");
        }

        authenticationRepository.invalidatePasswordResetTokens(userId);

        String token = UUID.randomUUID().toString().replace("-", "");
        Instant now = Instant.now();

        var resetToken = new PasswordResetToken();
        resetToken.setPasswordResetTokenId(UUID.randomUUID());
        resetToken.setUserId(userId);
        resetToken.setToken(token);
        resetToken.setExpireAt(now.plus(tokenMinutes, ChronoUnit.MINUTES));
        resetToken.setUsed(false);
        resetToken.setCreatedAt(now);
        authenticationRepository.savePasswordResetToken(resetToken);

        String link = frontendUrl + "/#/set-password?token=" + token;

        emailService.send(request.email(), "Reset Password", """
                <h2>Reset Password</h2>

                <p>Click the link below to set your password.</p>

                <a href="%s">
                    Reset Password
                </a>

                <p>This link expires in %d minutes.</p>
                """.formatted(link, tokenMinutes));

        return new ForgotPasswordResponse("Check your email.");
    }
}
