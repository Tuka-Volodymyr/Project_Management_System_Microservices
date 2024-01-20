package com.example.team_service.client.impl;

import com.example.team_service.client.IdentityClient;
import com.example.team_service.models.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class IdentityClientImpl implements IdentityClient {

  @Lazy
  private final RestTemplate restTemplate;

  @Override
  public boolean existUser(String username) {
    try {
      return Boolean.TRUE.equals(
          restTemplate.getForObject("http://IDENTITY-SERVICE/auth/exist?username=" + username,
              Boolean.class));
    } catch (RuntimeException e) {
      throw new BadRequestException("Some problem with IDENTITY-SERVICE");
    }
  }
}
