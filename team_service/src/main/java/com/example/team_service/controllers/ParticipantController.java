package com.example.team_service.controllers;

import com.example.team_service.models.participant.ChangePositionRequest;
import com.example.team_service.models.participant.DeleteTaskRequest;
import com.example.team_service.models.participant.ParticipantRequest;
import com.example.team_service.models.participant.ParticipantResponse;
import com.example.team_service.models.participant.ParticipantTaskRequest;
import com.example.team_service.services.ParticipantService;
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
@RequestMapping("/team/participant")
public class ParticipantController {

  private final ParticipantService participantService;

  @PostMapping("/add")
  public ResponseEntity<ParticipantResponse> addParticipant(
      @RequestBody @Valid ParticipantRequest participantRequest) {
    return new ResponseEntity<>(participantService.addParticipant(participantRequest),
        HttpStatus.OK);
  }

  @PostMapping("/add/task")
  public ResponseEntity<String> addTaskParticipant(
      @RequestBody @Valid ParticipantTaskRequest participantTaskRequest) {
    return new ResponseEntity<>(participantService.addTaskForParticipant(participantTaskRequest),
        HttpStatus.OK);
  }

  @GetMapping("/delete/{email}")
  public ResponseEntity<String> deleteParticipant(@PathVariable("email") String email) {
    return new ResponseEntity<>(participantService.deleteParticipant(email), HttpStatus.OK);
  }

  @PostMapping("/change/position")
  public ResponseEntity<String> changePosition(
      @RequestBody @Valid ChangePositionRequest changePositionRequest) {
    return new ResponseEntity<>(participantService.changePosition(changePositionRequest),
        HttpStatus.OK);
  }

  @PostMapping("/delete/task")
  public ResponseEntity<String> deleteTask(
      @RequestBody @Valid DeleteTaskRequest deleteTaskRequest) {
    return new ResponseEntity<>(participantService.deleteTaskForParticipant(deleteTaskRequest),
        HttpStatus.OK);
  }


}
