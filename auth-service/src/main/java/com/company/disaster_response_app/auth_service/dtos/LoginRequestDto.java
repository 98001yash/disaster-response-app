package com.company.disaster_response_app.auth_service.dtos;


import lombok.Data;

@Data
public class LoginRequestDto {

    private String email;
    private String password;
}
