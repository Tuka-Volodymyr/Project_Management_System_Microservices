package com.example.team_service.models.participant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteTaskRequest {
  @Email
  @NotBlank
  private String participantEmail;

  @NumberFormat
  @Min(1)
  private long taskId;


}
