package com.devcv.member.domain.dto;

import lombok.Getter;

@Getter
public class GoogleProfile {
    private String id;
    private String email;
    private Boolean verified_email;
    private String name;
    private String given_name;
    private String picture;
}
