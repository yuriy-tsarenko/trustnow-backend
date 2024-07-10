package com.trust.now.entity;

import com.trust.now.config.common.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "user_account")
public class UserAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(length = 100, unique = true, nullable = false)
    private String username;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String middleName;

    @Column(length = 100)
    private String lastName;

    @Column(length = 255, nullable = false)
    private String password;

    @Column
    private Timestamp lastLogin;

    @Column(length = 45)
    private String lastLoginIpAddress;

    @Column(length = 50)
    private String loginApp;

    @Column
    private Integer failedLoginAttempts = 0;

    @Column
    private Timestamp lockoutTimeTo;

    @Column
    private Boolean isEmailVerified;

    @Column
    private Boolean isPhoneVerified;

    @Column
    private Boolean isActive;

    @Column(columnDefinition = "TEXT")
    private String avatarBase64;

    @Column(nullable = false, updatable = false)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Column(nullable = false)
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

    public Set<String> getRoles() {
        //TODO: Implement db based roles here
        return Arrays.stream(Roles.values())
                .map(Roles::name)
                .collect(Collectors.toSet());
    }
}