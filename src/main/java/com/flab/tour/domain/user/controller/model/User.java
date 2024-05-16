package com.flab.tour.domain.user.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String phoneNumber;

    private LocalDate joinedDate;

    private boolean emailMarketingConsent;

    private LocalDateTime emailMarketingConsentLastModifiedAt;

    private boolean smsMarketingConsent;

    private LocalDateTime smsMarketingConsentLastModifiedAt;

    private boolean pushMarketingConsent;

    private LocalDateTime pushMarketingConsentLastModifiedAt;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
