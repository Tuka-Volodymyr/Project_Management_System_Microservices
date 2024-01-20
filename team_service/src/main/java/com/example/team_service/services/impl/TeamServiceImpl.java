package com.example.team_service.services.impl;

import com.example.team_service.models.Team;
import com.example.team_service.models.exception.BadRequestException;
import com.example.team_service.models.exception.TeamNotFoundException;
import com.example.team_service.models.team.TeamRequest;
import com.example.team_service.repositories.TeamRepository;
import com.example.team_service.services.TeamService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

  private final TeamRepository teamRepository;

  @Override
  public void save(Team team) {
    if (team != null) {
      teamRepository.save(team);
    } else {
      throw new NullPointerException();
    }
  }

  @Override
  public Team getById(long id) {
    return teamRepository
        .findById(id)
        .orElseThrow(TeamNotFoundException::new);
  }

  @Override
  public String createTeam(TeamRequest teamRequest) {
    Team team = Team.builder()
        .registered(new Date())
        .name(teamRequest.getName())
        .build();
    save(team);
    return "Team was created, admin: " + teamRequest.getOwner();
  }

  @Override
  public String deleteTeam(long teamId) {
    Team team = teamRepository
        .findById(teamId)
        .orElseThrow(TeamNotFoundException::new);
    try {
      teamRepository.delete(team);
    } catch (Exception e) {
      throw new BadRequestException("First delete all participants in team.");
    }
    return team.getName() + " team was deleted";
  }
}
