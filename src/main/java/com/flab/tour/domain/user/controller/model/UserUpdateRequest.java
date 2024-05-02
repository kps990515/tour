package com.flab.tour.domain.user.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String name;

    private String password;

    private boolean emailMarketingConsent;

    private boolean smsMarketingConsent;

    private boolean pushMarketingConsent;
}
