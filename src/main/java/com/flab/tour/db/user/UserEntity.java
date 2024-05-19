package com.flab.tour.db.user;

import com.flab.tour.common.validation.PhoneNumber;
import com.flab.tour.db.BaseEntity;
import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {
    @Id
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @PrePersist // persist 연산을 통해 처음으로 데이터베이스에 저장되기 전에 메소드가 실행
    // DB에 처음 저장될때만 실행(Update할때마다 바꾸고 싶으면 @PreUpdate사용)
    private void generateUUID(){
        this.userId = UuidCreator.getTimeOrderedEpoch().toString();
    }

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
}
