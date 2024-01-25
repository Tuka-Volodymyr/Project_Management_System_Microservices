package com.example.project_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.project_service.client.IdentityClient;
import com.example.project_service.client.TaskClient;
import com.example.project_service.model.entity.Project;
import com.example.project_service.model.exceptions.BadRequestException;
import com.example.project_service.model.project.ChangeStatusRequest;
import com.example.project_service.model.project.ProjectRequest;
import com.example.project_service.model.project.ProjectResponse;
import com.example.project_service.repositories.ProjectRepository;
import com.example.project_service.service.impl.ProjectServiceImpl;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public final class ProjectServiceImplTest {

  @Mock
  private IdentityClient identityClient;

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private TaskClient taskClient;

  @InjectMocks
  private ProjectServiceImpl projectService;


  public Project project = Project.builder()
      .id(2L)
      .status("Done")
      .registered(LocalDateTime.of(2023, 1, 14, 10, 30))
      .deadline(LocalDateTime.of(2024, 1, 14, 10, 30))
      .description("This project is very difficult")
      .name("System of management")
      .owner("Volodymyr")
      .build();

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
        .description("This project is very difficult")
        .name("System of management")
        .status("In_work")
        .build();

    when(identityClient.existUser(projectRequest.getOwner())).thenReturn(true);

    ProjectResponse responseEntity = projectService.addProject(projectRequest);

    assertEquals(responseEntity, expectedResponse);
  }

  @Test
  public void changeProjectStatusTest_Success() {
    ChangeStatusRequest changeStatusRequest = ChangeStatusRequest.builder()
        .projectId(2)
        .status("Done")
        .build();

    when(projectRepository.findById(changeStatusRequest.getProjectId())).thenReturn(
        Optional.of(project));
    String response = projectService.changeProjectStatus(changeStatusRequest);

    assertEquals(response, "New status: " + changeStatusRequest.getStatus());
  }

  @Test
  public void changeProjectStatusTest_WrongStatus_Fail() {
    ChangeStatusRequest changeStatusRequest = ChangeStatusRequest.builder()
        .projectId(2)
        .status("Some")
        .build();

    when(projectRepository.findById(changeStatusRequest.getProjectId())).thenReturn(
        Optional.of(project));

    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> projectService.changeProjectStatus(changeStatusRequest));
    assertEquals("Status must be IN_WORK or DONE", exception.getMessage());
  }

  @Test
  public void deleteProjectTest_Success() {
    long projectId = 2;

    when(taskClient.notExistenceTasksByProjectId(projectId)).thenReturn(true);
    when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

    String response = projectService.deleteProject(projectId);

    assertEquals(response, project.getName() + " project was deleted");
  }

  @Test
  public void getProjectByIdTest_Success() {

    ProjectResponse expectedResponse = ProjectResponse.builder()
        .deadline(LocalDateTime.of(2024, 1, 14, 10, 30))
        .description("This project is very difficult")
        .name("System of management")
        .status("Done")
        .build();

    when(projectRepository.findById(2L)).thenReturn(Optional.of(project));

    ProjectResponse response = projectService.getProjectById(2L);

    assertEquals(response, expectedResponse);
  }

}
