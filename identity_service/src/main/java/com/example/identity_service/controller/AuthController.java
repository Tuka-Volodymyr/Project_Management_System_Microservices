package com.example.identity_service.controller;

import com.example.identity_service.service.AuthService;
import com.example.identity_service.model.auth.LoginRequest;
import com.example.identity_service.model.auth.LoginResponse;
import com.example.identity_service.model.auth.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<String> addNewUser(@RequestBody UserRequest user) {
    return new ResponseEntity<>(authService.addUser(user), HttpStatus.OK);
  }

  @PostMapping("/authenticate")
  public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginRequest loginRequest) {
    return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
  }

  @GetMapping("/validate")
  public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
    authService.validateToken(token);
    return new ResponseEntity<>("Token is validate", HttpStatus.OK);
  }

  @GetMapping("/exist")
  public ResponseEntity<Boolean> existUser(@RequestParam String username) {
    return new ResponseEntity<>(authService.existUser(username), HttpStatus.OK);
  }



}
