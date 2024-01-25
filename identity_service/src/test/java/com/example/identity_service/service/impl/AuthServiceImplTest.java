package com.example.identity_service.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.identity_service.model.User;
import com.example.identity_service.model.auth.LoginRequest;
import com.example.identity_service.model.auth.LoginResponse;
import com.example.identity_service.model.auth.UserRequest;
import com.example.identity_service.model.exception.BadRequestException;
import com.example.identity_service.model.exception.UnauthorizedException;
import com.example.identity_service.repository.UserRepository;
import com.example.identity_service.service.JwtService;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private JwtService jwtService;

  @InjectMocks
  private AuthServiceImpl authService;
  private final User user = User.builder()
      .email("tata@gmail.com")
      .name("VOLODYMYR")
      .password("hashedPassword")
      .build();

  @Test
  public void addUserTest_Success() {
    UserRequest userRequest = UserRequest.builder()
        .email("tata@gmail.com")
        .name("VOLODYMYR")
        .password("1111")
        .build();

    when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("hashedPassword");

    String response = authService.addUser(userRequest);

    assertEquals(userRequest.getName() + " was added!", response);
  }

  @Test
  public void addUserTest_ExistedEmail_Fail() {
    UserRequest userRequest = UserRequest.builder()
        .email("tata@gmail.com")
        .name("VOLODYMYR")
        .password("1111")
        .build();

    when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.of(new User()));

    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> authService.addUser(userRequest));

    assertEquals("Email has already used", exception.getMessage());
    assertNotNull(exception);
  }

  @Test
  public void loginTest_Success() {

    LoginRequest loginRequest = LoginRequest.builder()
        .email("tata@gmail.com")
        .password("1111")
        .build();

    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
    when(jwtService.generateToken(loginRequest.getEmail())).thenReturn("token");

    LoginResponse response = authService.login(loginRequest);

    assertEquals("token", response.getToken());
    assertEquals(loginRequest.getEmail(), response.getEmail());
  }

  @Test
  public void loginTest_WrongPassword_Fail() {

    LoginRequest loginRequest = LoginRequest.builder()
        .email("tata@gmail.com")
        .password("11111")
        .build();

    when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);

    UnauthorizedException exception = assertThrows(UnauthorizedException.class,
        () -> authService.login(loginRequest));

    assertNotNull(exception);
  }

  @Test
  public void existUserTest_Success() {
    String username = "tata@gmail.com";

    when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));

    Boolean response = authService.existUser(username);

    assertEquals(true, response);
  }

}
