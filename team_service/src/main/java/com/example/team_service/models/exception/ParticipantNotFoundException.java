package com.example.team_service.models.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Participant not found!")
public class ParticipantNotFoundException extends RuntimeException{

}
