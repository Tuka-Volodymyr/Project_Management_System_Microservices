package com.example.project_service.controller;

import com.example.project_service.model.project.ChangeStatusRequest;
import com.example.project_service.model.project.ProjectRequest;
import com.example.project_service.model.project.ProjectResponse;
import com.example.project_service.service.impl.ProjectServiceImpl;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
public class ProjectControllerTest {

  @Mock
  private ProjectServiceImpl projectService;

  @InjectMocks
  private ProjectController projectController;

  @Test
  public void addProjectTest_Success() {
    ProjectRequest projectRequest = ProjectRequest.builder()
        .name("System of management")
        .owner("Volodimir")
        .deadline("2024-01-14 10:30:00")
        .status("In_work")
        .description("This project is very difficult")
        .build();

    ProjectResponse expectedResponse = ProjectResponse.builder()
        .deadline(LocalDateTime.of(2024, 1, 14, 10, 30))
        .description(projectRequest.getDescription()).name(projectRequest.getName())
        .status(projectRequest.getStatus()).build();

    when(projectService.addProject(projectRequest)).thenReturn(expectedResponse);

    ResponseEntity<ProjectResponse> responseEntity = projectController.addProject(projectRequest);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(responseEntity.getBody(), expectedResponse);

  }

  @Test
  public void changeProjectStatusTest_Success() {
    ChangeStatusRequest changeStatusRequest = ChangeStatusRequest.builder()
        .projectId(1)
        .status("Done")
        .build();

    String expectedResponse = "Status changed successfully";

    when(projectService.changeProjectStatus(changeStatusRequest)).thenReturn(expectedResponse);

    ResponseEntity<String> responseEntity = projectController.changeProjectStatus(
        changeStatusRequest);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(expectedResponse, responseEntity.getBody());

    verify(projectService, times(1)).changeProjectStatus(changeStatusRequest);
  }

  @Test
  public void getByIdTest_Success() {
    ProjectResponse expectedResponse = ProjectResponse.builder()
        .deadline(LocalDateTime.of(2024, 1, 14, 10, 30))
        .description("This project is very difficult").name("System of management")
        .status("In_work").build();

    when(projectService.getProjectById(2L)).thenReturn(expectedResponse);

    ResponseEntity<ProjectResponse> responseEntity = projectController.getById(2L);

    assertEquals(responseEntity.getBody(), expectedResponse);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  public void deleteProjectTest_Success() {
    String response = "Project was deleted";

    when(projectService.deleteProject(2L)).thenReturn(response);

    ResponseEntity<String> responseEntity = projectController.deleteProject(2L);

    assertEquals(responseEntity.getBody(), response);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
