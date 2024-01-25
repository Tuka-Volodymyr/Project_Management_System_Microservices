package com.example.identity_service.service.impl;


import com.example.identity_service.model.User;
import com.example.identity_service.model.exception.BadRequestException;
import com.example.identity_service.model.exception.UnauthorizedException;
import com.example.identity_service.repository.UserRepository;
import com.example.identity_service.service.AuthService;
import com.example.identity_service.model.auth.LoginRequest;
import com.example.identity_service.model.auth.LoginResponse;
import com.example.identity_service.model.auth.UserRequest;
import com.example.identity_service.service.JwtService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtService jwtService;

  @Override
  public void save(User user) {
    if (user != null) {
      userRepository.save(user);
    } else {
      throw new NullPointerException();
    }
  }

  @Override
  public String addUser(UserRequest userRequest) {
    Optional<User> existUser = userRepository.findByEmail(userRequest.getEmail());
    if (existUser.isPresent()) {
      throw new BadRequestException("Email has already used");
    }
    User user = User.builder()
        .email(userRequest.getEmail())
        .name(userRequest.getName())
        .password(passwordEncoder.encode(userRequest.getPassword()))
        .build();
    save(user);
    return user.getName() + " was added!";
  }

  @Override
  public LoginResponse login(LoginRequest request) {
    Optional<User> userOptional = validUsernameAndPassword(request.getEmail(),
        request.getPassword());
    if (userOptional.isPresent()) {
      return LoginResponse.builder()
          .email(request.getEmail())
          .token(jwtService.generateToken(request.getEmail()))
          .build();
    } else {
      throw new UnauthorizedException();
    }
  }

  @Override
  public void validateToken(String token) {
    jwtService.validateToken(token);
  }

  @Override
  public Optional<User> validUsernameAndPassword(String username, String password) {
    return userRepository.findByEmail(username)
        .filter(user -> passwordEncoder.matches(password, user.getPassword()));
  }

  @Override
  public boolean existUser(String username) {
    Optional<User> existUser = userRepository.findByEmail(username);
    return existUser.isPresent();
  }
}
