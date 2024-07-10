package com.trust.now.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "organization")
public class OrganizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(length = 100, unique = true, nullable = false)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(length = 100)
    private String industry;

    @Column
    private Integer size;

    @Column(length = 50, nullable = false)
    private String status = "active";

    @Column(nullable = false, updatable = false)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Column(nullable = false)
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

    @Column
    private Timestamp deactivatedAt;

    @Column
    private Timestamp lastActivityAt;

    @Column(columnDefinition = "TEXT")
    private String logoBase64;

    @Column
    private UUID ownerId;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserAccountEntity> userAccountEntities = new HashSet<>();

    public void addUserAccount(UserAccountEntity userAccountEntity) {
        this.getUserAccountEntities().add(userAccountEntity);
        userAccountEntity.setOrganization(this);
    }

    public void removeUserAccount(UserAccountEntity userAccountEntity) {
        this.getUserAccountEntities().remove(userAccountEntity);
        userAccountEntity.setOrganization(null);
    }

}