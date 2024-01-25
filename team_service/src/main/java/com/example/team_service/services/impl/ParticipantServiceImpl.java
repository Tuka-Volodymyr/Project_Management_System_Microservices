package com.example.team_service.services.impl;

import com.example.team_service.client.IdentityClient;
import com.example.team_service.client.TaskClient;
import com.example.team_service.models.Participant;
import com.example.team_service.models.Team;
import com.example.team_service.models.exception.BadRequestException;
import com.example.team_service.models.exception.ParticipantNotFoundException;
import com.example.team_service.models.participant.ChangePositionRequest;
import com.example.team_service.models.participant.DeleteTaskRequest;
import com.example.team_service.models.participant.ParticipantRequest;
import com.example.team_service.models.participant.ParticipantResponse;
import com.example.team_service.models.participant.ParticipantTaskRequest;
import com.example.team_service.repositories.ParticipantRepository;
import com.example.team_service.services.ParticipantService;
import com.example.team_service.services.TeamService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

  private final ParticipantRepository participantRepository;

  private final TeamService teamService;

  private final IdentityClient identityClient;

  private final TaskClient taskClient;

  @Override
  public void save(Participant participant) {
    if (participant != null) {
      participantRepository.save(participant);
    } else {
      throw new NullPointerException();
    }
  }

  @Override
  public void checkExistenceParticipant(String email) {
    if (!identityClient.existUser(email)) {
      throw new BadRequestException("Participant don`t exist");
    }
  }

  @Override
  public void checkExistenceTask(List<Long> taskId) {
    taskId.stream()
        .filter(id -> !taskClient.existenceTask(id))
        .findAny()
        .ifPresent(e -> {
          throw new BadRequestException("Some task(s) don`t exist");
        });
  }

  @Override
  public ParticipantResponse toParticipantResponse(Participant participant, String teamName) {
    return ParticipantResponse.builder()
        .email(participant.getEmail())
        .position(participant.getPosition())
        .teamName(teamName)
        .build();
  }

  @Override
  public ParticipantResponse addParticipant(ParticipantRequest participantRequest) {
    Team team = teamService.getById(participantRequest.getTeam_id());
    checkExistenceParticipant(participantRequest.getEmail());
    Participant participant = Participant.builder()
        .team(team)
        .position(participantRequest.getPosition())
        .email(participantRequest.getEmail())
        .registered(LocalDateTime.now())
        .build();
    save(participant);
    return toParticipantResponse(participant, team.getName());
  }

  @Override
  public String addTaskForParticipant(ParticipantTaskRequest participantTaskRequest) {
    if (participantTaskRequest.getTask_id().isEmpty()) {
      throw new BadRequestException("Task_id can`t be empty");
    }
    checkExistenceTask(participantTaskRequest.getTask_id());
    Participant participant = getParticipantByEmail(participantTaskRequest.getParticipantEmail());
    Set<Long> combinedTaskIdList = Stream.concat(
            participantTaskRequest.getTask_id().stream(),
            participant.getTaskIds().stream())
        .collect(Collectors.toSet());
    participant.setTaskIds(combinedTaskIdList);
    save(participant);
    return "Task(s) was added";
  }

  @Override
  public String deleteParticipant(String participantEmail) {
    Participant participant = getParticipantByEmail(participantEmail);
    try {
      participantRepository.delete(participant);
    } catch (Exception e) {
      throw new BadRequestException("First delete all task for this participant");
    }
    return participant.getEmail() + " participant was deleted";
  }

  @Override
  public String deleteTaskForParticipant(DeleteTaskRequest deletedTask) {
    Participant participant = getParticipantByEmail(deletedTask.getParticipantEmail());
    participant.getTaskIds().remove(deletedTask.getTaskId());
    save(participant);
    return "Task was delete from participant";
  }

  @Override
  public String changePosition(ChangePositionRequest changePosition) {
    Participant participant = getParticipantByEmail(changePosition.getParticipantEmail());
    participant.setPosition(changePosition.getPosition());
    save(participant);
    return participant.getEmail() + " your new position: " + changePosition.getPosition();
  }

  @Override
  public Participant getParticipantByEmail(String email) {
    return participantRepository
        .findByEmail(email)
        .orElseThrow(ParticipantNotFoundException::new);
  }
}
