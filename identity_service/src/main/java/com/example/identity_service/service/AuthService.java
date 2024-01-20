package com.example.identity_service.service;

import com.example.identity_service.model.User;
import com.example.identity_service.model.auth.LoginRequest;
import com.example.identity_service.model.auth.UserRequest;
import com.example.identity_service.model.auth.LoginResponse;
import java.security.Key;
import java.util.Map;
import java.util.Optional;

public interface AuthService {

  void save(User user);

  String addUser(UserRequest userRequest);

  LoginResponse login(LoginRequest request);

  void validateToken(String token);

  Optional<User> validUsernameAndPassword(String username, String password);

  boolean existUser(String username);
}
