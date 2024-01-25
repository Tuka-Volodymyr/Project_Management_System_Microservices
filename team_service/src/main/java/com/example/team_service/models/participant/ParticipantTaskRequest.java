package com.example.team_service.models.participant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ParticipantTaskRequest {

  private List<Long> task_id;

  @NotBlank
  @Email
  private String participantEmail;

}
