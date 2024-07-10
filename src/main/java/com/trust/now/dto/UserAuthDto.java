package com.trust.now.dto;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

public record UserAuthDto(
        UUID id,
        String username,
        String firstName,
        String middleName,
        String lastName,
        Timestamp lastLogin,
        String lastLoginIpAddress,
        String loginApp,
        Timestamp lockoutTimeTo,
        Boolean isEmailVerified,
        Boolean isPhoneVerified,
        Boolean isActive,
        Set<String> roles
) {}