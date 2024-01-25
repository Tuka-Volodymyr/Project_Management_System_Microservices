package com.example.task_service.client.impl;

import com.example.task_service.client.TeamClient;
import com.example.task_service.model.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class TeamClientImpl implements TeamClient {

  @Lazy
  private final RestTemplate restTemplate;


  @Override
  public boolean existsByTaskIdsContains(Long taskId) {
    String path="http://TEAM-SERVICE/team/participant/existence/by?taskId=" + taskId;
    System.out.println(path);
    try {
      return Boolean.TRUE.equals(
          restTemplate.getForObject(path, Boolean.class));
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
      throw new BadRequestException("Some problem with TEAM-SERVICE");
    }
  }

}
