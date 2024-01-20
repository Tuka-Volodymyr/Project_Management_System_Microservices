package com.example.team_service.models.participant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangePositionRequest {

  @Email
  @NotBlank
  private String participantEmail;

  @NotBlank
  private String position;

}
