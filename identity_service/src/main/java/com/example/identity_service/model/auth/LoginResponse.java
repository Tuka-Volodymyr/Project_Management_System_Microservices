package com.example.identity_service.model.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

  private String email;

  private String token;
}
