package com.example.team_service.controllers;

import com.example.team_service.models.team.TeamRequest;
import com.example.team_service.repositories.TeamRepository;
import com.example.team_service.services.TeamService;
import com.example.team_service.services.impl.TeamServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

  private final TeamService teamService;

  @PostMapping("/create")
  public ResponseEntity<String> createTeam(@RequestBody @Valid TeamRequest teamRequest) {
    return new ResponseEntity<>(teamService.createTeam(teamRequest), HttpStatus.OK);
  }

  @GetMapping("/delete/{id}")
  public ResponseEntity<String> deleteTeam(@PathVariable("id") Long id) {
    return new ResponseEntity<>(teamService.deleteTeam(id), HttpStatus.OK);
  }

}
