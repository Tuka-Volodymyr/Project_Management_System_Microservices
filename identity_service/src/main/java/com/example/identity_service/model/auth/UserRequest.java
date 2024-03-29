package com.example.identity_service.model.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

  @NotBlank
  private String name;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Size(min = 4)
  private String password;


}
