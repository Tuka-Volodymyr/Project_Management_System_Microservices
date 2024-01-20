package com.example.team_service.models.participant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ParticipantResponse {

  private String email;

  private String position;

  private String teamName;

}
