package com.example.task_service.client.impl;

import com.example.task_service.client.ProjectServiceClient;
import com.example.task_service.model.exceptions.ProjectNotFoundException;
import com.example.task_service.model.common.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProjectServiceClientImpl implements ProjectServiceClient {

  @Lazy
  private final RestTemplate restTemplate;


  @Override
  public String getStatusOfProject(long idProject) {
    try {
      ProjectResponse projectResponse = restTemplate.getForObject(
          "http://PROJECT-SERVICE/project/get/{id}", ProjectResponse.class, idProject);
      assert projectResponse != null;
      return projectResponse.getStatus();
    } catch (RuntimeException e) {
      throw new ProjectNotFoundException();
    }
  }
}
