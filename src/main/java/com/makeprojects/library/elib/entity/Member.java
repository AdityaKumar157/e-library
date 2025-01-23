package com.makeprojects.library.elib.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.makeprojects.library.elib.enums.SubscriptionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@With
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String role;

    private String mobileNumber;

    @Email
    @Column(unique = true)
    private String email;

    @Builder.Default
    private SubscriptionStatus subscriptionStatus = SubscriptionStatus.INACTIVE;

}
