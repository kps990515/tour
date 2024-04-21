package com.flab.tour.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
public class TestVo {
    private String email;
    private String name;
    private LocalDateTime registerdAt;
}
