package com.example.team_service.client.impl;

import com.example.team_service.client.TaskClient;
import com.example.team_service.models.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class TaskClientImpl implements TaskClient {
  @Lazy
  private final RestTemplate restTemplate;

  @Override
  public boolean existenceTask(long taskId) {
    try {
      return Boolean.TRUE.equals(
          restTemplate.getForObject("http://TASK-SERVICE/task/existence?taskId=" + taskId,
              Boolean.class));
    } catch (RuntimeException e) {
      throw new BadRequestException("Some problem with TASK-SERVICE");
    }
  }

}
