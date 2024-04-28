package com.flab.tour.db.user;

import com.flab.tour.common.validation.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "email", length = 256, unique = true)
    @Email
    private String email;

    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Column(name = "phone_number", nullable = false, length = 256, unique = true)
    @PhoneNumber
    private String phoneNumber;

    @Column(name = "joined_date", nullable = false)
    private LocalDate joinedDate;

    @Column(name = "email_marketing_consent", nullable = false)
    private boolean emailMarketingConsent;

    @Column(name = "email_marketing_consent_last_modifed_at")
    private LocalDateTime emailMarketingConsentLastModifiedAt;

    @Column(name = "sms_marketing_consent", nullable = false)
    private boolean smsMarketingConsent;

    @Column(name = "sms_marketing_consent_last_modifed_at")
    private LocalDateTime smsMarketingConsentLastModifiedAt;

    @Column(name = "push_marketing_consent", nullable = false)
    private boolean pushMarketingConsent;

    @Column(name = "push_marketing_consent_last_modifed_at")
    private LocalDateTime pushMarketingConsentLastModifiedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;
}
