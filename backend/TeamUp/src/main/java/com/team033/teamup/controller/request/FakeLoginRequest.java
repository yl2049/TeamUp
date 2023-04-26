package com.team033.teamup.controller.request;

import lombok.Data;

@Data
public class FakeLoginRequest {
    private String email_address;
    private String password;
}
