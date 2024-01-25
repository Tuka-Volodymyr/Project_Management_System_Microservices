package com.example.project_service.client.impl;

import com.example.project_service.client.TaskClient;
import com.example.project_service.model.exceptions.BadRequestException;
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
  public boolean notExistenceTasksByProjectId(long projectId) {
    try {
      return Boolean.TRUE.equals(
          restTemplate.getForObject("http://TASK-SERVICE/task/existence/by?projectId=" + projectId,
              Boolean.class));
    } catch (RuntimeException e) {
      throw new BadRequestException("Some problem with TASK-SERVICE");
    }
  }

}
