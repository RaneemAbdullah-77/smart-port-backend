package com.smartport.auth.application.usecases;

import com.smartport.auth.application.dto.requests.AddEmployeeRequest;
import com.smartport.auth.application.dto.responses.AddEmployeeResponse;
import com.smartport.auth.application.exception.AuthException;
import com.smartport.auth.application.ports.outbound.AuthenticationRepository;
import com.smartport.auth.application.ports.outbound.EmailService;
import com.smartport.auth.domain.entities.Employee;
import com.smartport.auth.domain.entities.Email;
import com.smartport.auth.domain.entities.SetPasswordToken;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HexFormat;
import java.util.Locale;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddEmployeeCommand {

    private final AuthenticationRepository repository;
    private final EmailService emailService;
    private final String frontendUrl;
    private final long tokenMinutes;

    public AddEmployeeCommand(
            AuthenticationRepository repository,
            EmailService emailService,
            @Value("${app.frontend-url}") String frontendUrl,
            @Value("${app.set-password-token-minutes:60}") long tokenMinutes) {
        this.repository = repository;
        this.emailService = emailService;
        this.frontendUrl = frontendUrl;
        this.tokenMinutes = tokenMinutes;
    }

    @Transactional
    public AddEmployeeResponse execute(AddEmployeeRequest request) {

        String emailAddress = request.email().trim().toLowerCase(Locale.ROOT);

        if (repository.getUserIdByEmail(emailAddress).isPresent()) {
            throw new AuthException(409, "This email is already registered.");
        }

        Employee employee = new Employee(
                null,
                request.firstName().trim(),
                request.lastName().trim()
        );

        Employee savedEmployee = repository.saveEmployee(employee);

        Email email = new Email();
        email.setEmailId(UUID.randomUUID());
        email.setUserId(savedEmployee.getUserId());
        email.setEmailAddress(emailAddress);
        email.setPrimary(true);
        email.setVerified(false);
        email.setDeleted(false);

        repository.saveEmail(email);

        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        String token = HexFormat.of().formatHex(randomBytes);

        Instant now = Instant.now();

        SetPasswordToken setPasswordToken = new SetPasswordToken();
        setPasswordToken.setSetPasswordTokenId(UUID.randomUUID());
        setPasswordToken.setUserId(savedEmployee.getUserId());
        setPasswordToken.setToken(token);
        setPasswordToken.setCreatedAt(now);
        setPasswordToken.setExpireAt(now.plus(tokenMinutes, ChronoUnit.MINUTES));
        setPasswordToken.setUsed(false);

        repository.saveSetPasswordToken(setPasswordToken);

        String link = frontendUrl + "/#/set-password?token=" + token;

        emailService.send(
                emailAddress,
                "Smart Port - Set Your Password",
                """
                <h2>Welcome to Smart Port</h2>
                <p>Your employee account has been created.</p>
                <p>Please click below to set your password and activate your account:</p>
                <a href="%s">Set Your Password</a>
                <p>This link expires in %d minutes.</p>
                """.formatted(link, tokenMinutes)
        );

        return new AddEmployeeResponse(
                savedEmployee.getUserId(),
                savedEmployee.getFirstName(),
                savedEmployee.getLastName(),
                emailAddress,
                savedEmployee.getStatus(),
                "Employee created. Password setup invitation sent."
        );
    }
}
