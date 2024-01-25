package com.example.team_service.services;

import com.example.team_service.models.Participant;
import com.example.team_service.models.participant.ChangePositionRequest;
import com.example.team_service.models.participant.DeleteTaskRequest;
import com.example.team_service.models.participant.ParticipantRequest;
import com.example.team_service.models.participant.ParticipantResponse;
import com.example.team_service.models.participant.ParticipantTaskRequest;
import java.util.List;

public interface ParticipantService {

  void save(Participant participant);

  void checkExistenceParticipant(String email);

  void checkExistenceTask(List<Long> taskId);

  ParticipantResponse toParticipantResponse(Participant participant, String teamName);

  ParticipantResponse addParticipant(ParticipantRequest participantRequest);

  String addTaskForParticipant(ParticipantTaskRequest participantTaskRequest);

  String deleteParticipant(String participantEmail);

  String deleteTaskForParticipant(DeleteTaskRequest deletedTask);

  String changePosition(ChangePositionRequest changePosition);

  Participant getParticipantByEmail(String email);

  boolean existsByTaskIdsContains(long taskId);
}
