package com.smartport.auth.application.ports.outbound;

import com.smartport.auth.application.shared.LoginUser;
import com.smartport.auth.domain.entities.Email;
import com.smartport.auth.domain.entities.Employee;
import com.smartport.auth.domain.entities.Password;
import com.smartport.auth.domain.entities.PasswordResetToken;
import com.smartport.auth.domain.entities.RefreshToken;
import com.smartport.auth.domain.entities.SetPasswordToken;
import java.util.Optional;
import java.util.UUID;

/** Outbound port (was IAuthenticationRepository). */
public interface AuthenticationRepository {

    // ---- employees ----
    Employee saveEmployee(Employee employee);

    // ---- users ----
    Optional<LoginUser> getByEmail(String email);

    Optional<UUID> getUserIdByEmail(String email);

    void saveEmail(Email email);

    void savePassword(Password password);

    boolean hasActivePassword(UUID userId);

    /** Inserts the first active password of a user. */
    void setPassword(UUID userId, String passwordHash);

    /** Deactivates the current active password and inserts a new one. */
    void resetPassword(UUID userId, String passwordHash);

    // ---- refresh tokens ----
    void saveRefreshToken(RefreshToken refreshToken);

    Optional<RefreshToken> getRefreshToken(String token);

    void updateRefreshToken(RefreshToken refreshToken);

    // ---- set-password tokens ----
    void saveSetPasswordToken(SetPasswordToken token);

    Optional<SetPasswordToken> getSetPasswordToken(String token);

    void updateSetPasswordToken(SetPasswordToken token);

    // ---- password-reset tokens ----
    void savePasswordResetToken(PasswordResetToken token);

    Optional<PasswordResetToken> getPasswordResetToken(String token);

    void updatePasswordResetToken(PasswordResetToken token);

    void invalidatePasswordResetTokens(UUID userId);
}

