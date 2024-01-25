package com.example.team_service.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.team_service.client.IdentityClient;
import com.example.team_service.client.TaskClient;
import com.example.team_service.models.Participant;
import com.example.team_service.models.Team;
import com.example.team_service.models.exception.BadRequestException;
import com.example.team_service.models.participant.ChangePositionRequest;
import com.example.team_service.models.participant.DeleteTaskRequest;
import com.example.team_service.models.participant.ParticipantRequest;
import com.example.team_service.models.participant.ParticipantResponse;
import com.example.team_service.models.participant.ParticipantTaskRequest;
import com.example.team_service.repositories.ParticipantRepository;
import com.example.team_service.services.TeamService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParticipantServiceImplTest {

  @Mock
  private ParticipantRepository participantRepository;

  @Mock
  private TeamService teamService;

  @Mock
  private IdentityClient identityClient;

  @Mock
  private TaskClient taskClient;

  @InjectMocks
  private ParticipantServiceImpl participantService;

  public Participant participant = Participant.builder()
      .member_id(1L)
      .email("l@gmail.com")
      .position("Junior")
      .taskIds(new HashSet<>(Arrays.asList(33L, 22L)))
      .registered(LocalDateTime.now())
      .build();

  @Test
  public void addParticipantTest_Success() {

    ParticipantRequest participantRequest = ParticipantRequest.builder()
        .email("l@gmail.com")
        .team_id(2L)
        .position("Junior")
        .build();

    Team team = Team.builder()
        .team_id(2L)
        .name("BETS TIME")
        .registered(LocalDateTime.now())
        .build();

    when(teamService.getById(participantRequest.getTeam_id())).thenReturn(team);
    when(identityClient.existUser(participantRequest.getEmail())).thenReturn(true);

    ParticipantResponse response = participantService.addParticipant(participantRequest);

    assertEquals(participantRequest.getEmail(), response.getEmail());
    assertEquals(team.getName(), response.getTeamName());
  }

  @Test
  public void addParticipantTest_WrongEmail_Fail() {

    ParticipantRequest participantRequest = ParticipantRequest.builder()
        .email("lgiysaduh@gmail.com")
        .team_id(2L)
        .position("Junior")
        .build();

    Team team = Team.builder()
        .team_id(2L)
        .name("BETS TIME")
        .registered(LocalDateTime.now())
        .build();

    when(teamService.getById(participantRequest.getTeam_id())).thenReturn(team);
    when(identityClient.existUser(participantRequest.getEmail())).thenReturn(false);

    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> participantService.addParticipant(participantRequest));

    assertEquals("Participant don`t exist", exception.getMessage());
  }

  @Test
  public void addTaskForParticipantTest_Success() {

    ParticipantTaskRequest participantTaskRequest = ParticipantTaskRequest.builder()
        .participantEmail("l@gmail.com")
        .task_id(List.of(2L))
        .build();

    when(taskClient.existenceTask(2L)).thenReturn(true);
    when(participantRepository.findByEmail(participantTaskRequest.getParticipantEmail()))
        .thenReturn(Optional.of(participant));

    String response = participantService.addTaskForParticipant(participantTaskRequest);

    assertEquals("Task(s) was added", response);
  }

  @Test
  public void addTaskForParticipantTest_EmptyTaskId_Fail() {

    ParticipantTaskRequest participantTaskRequest = ParticipantTaskRequest.builder()
        .participantEmail("l@gmail.com")
        .task_id(List.of())
        .build();

    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> participantService.addTaskForParticipant(participantTaskRequest));

    assertEquals("Task_id can`t be empty", exception.getMessage());
  }

  @Test
  public void addTaskForParticipantTest_WrongTaskId_Fail() {

    ParticipantTaskRequest participantTaskRequest = ParticipantTaskRequest.builder()
        .participantEmail("l@gmail.com")
        .task_id(List.of(2L, 3L))
        .build();

    when(taskClient.existenceTask(2L)).thenReturn(true);
    when(taskClient.existenceTask(3L)).thenReturn(false);

    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> participantService.addTaskForParticipant(participantTaskRequest));

    assertEquals("Some task(s) don`t exist", exception.getMessage());
  }

  @Test
  public void deleteParticipantTest_Success() {
    String participantEmail = "l@gmail.com";

    when(participantRepository.findByEmail(participantEmail))
        .thenReturn(Optional.of(participant));

    String response = participantService.deleteParticipant(participantEmail);

    assertEquals(participantEmail + " participant was deleted", response);
  }

  @Test
  public void deleteTaskForParticipantTest_Success() {
    DeleteTaskRequest deleteTaskRequest = DeleteTaskRequest.builder()
        .taskId(22L)
        .participantEmail("l@gmail.com")
        .build();

    when(participantRepository.findByEmail(deleteTaskRequest.getParticipantEmail()))
        .thenReturn(Optional.of(participant));

    String response = participantService.deleteTaskForParticipant(deleteTaskRequest);

    assertEquals("Task was delete from participant", response);
  }

  @Test
  public void changePositionTest_Success() {
    ChangePositionRequest changePositionRequest = ChangePositionRequest.builder()
        .participantEmail("l@gmail.com")
        .position("Team lead")
        .build();
    when(participantRepository.findByEmail(changePositionRequest.getParticipantEmail()))
        .thenReturn(Optional.of(participant));

    String response = participantService.changePosition(changePositionRequest);

    assertEquals(
        participant.getEmail() + " your new position: " + changePositionRequest.getPosition(),
        response
    );
  }


}
