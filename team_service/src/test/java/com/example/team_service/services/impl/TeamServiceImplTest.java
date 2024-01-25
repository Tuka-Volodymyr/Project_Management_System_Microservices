package com.example.team_service.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.team_service.models.Team;
import com.example.team_service.models.team.TeamRequest;
import com.example.team_service.repositories.TeamRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TeamServiceImplTest {

  @Mock
  private TeamRepository teamRepository;

  @InjectMocks
  private TeamServiceImpl teamService;

  @Test
  public void createTeamTest_Success() {

    TeamRequest teamRequest = TeamRequest.builder().owner("VOLODYMYR").name("BETS TIME").build();

    String response = teamService.createTeam(teamRequest);

    assertEquals("Team was created, admin: " + teamRequest.getOwner(), response);
  }

  @Test
  public void deleteTeamTest_Success() {
    long teamId = 2L;

    Team team = Team.builder().team_id(2L).name("BETS TIME").registered(LocalDateTime.now())
        .build();

    when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
    String response = teamService.deleteTeam(teamId);

    assertEquals(team.getName() + " team was deleted", response);
  }
}
