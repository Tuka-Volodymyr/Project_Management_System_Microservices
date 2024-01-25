package com.example.team_service.models.team;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamRequest {

  @Email
  @NotBlank
  private String owner;

  @NotBlank
  private String name;
}
