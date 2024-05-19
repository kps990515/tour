package com.flab.tour.domain.user.controller.model;

import com.flab.tour.common.validation.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @PhoneNumber
    private String phoneNumber;

    private boolean emailMarketingConsent;

    private boolean smsMarketingConsent;

    private boolean pushMarketingConsent;
}
