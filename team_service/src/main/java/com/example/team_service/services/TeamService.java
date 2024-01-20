package com.example.team_service.services;

import com.example.team_service.models.Team;
import com.example.team_service.models.team.TeamRequest;


public interface TeamService {

  void save(Team team);

  Team getById(long id);

  String createTeam(TeamRequest teamRequest);

  String deleteTeam(long projectId);
}
