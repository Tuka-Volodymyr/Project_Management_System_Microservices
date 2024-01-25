package com.example.project_service.service.impl;

import com.example.project_service.client.IdentityClient;
import com.example.project_service.model.entity.Project;
import com.example.project_service.model.exceptions.BadRequestException;
import com.example.project_service.model.exceptions.ProjectNotFoundException;
import com.example.project_service.model.exceptions.UserNotFoundException;
import com.example.project_service.model.project.ChangeStatusRequest;
import com.example.project_service.repositories.ProjectRepository;
import com.example.project_service.service.ProjectService;
import com.example.project_service.model.project.ProjectRequest;
import com.example.project_service.model.project.ProjectResponse;
import com.example.project_service.model.project.Status;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;

  private final IdentityClient identityClient;

  @Override
  public void save(Project project) {
    if (project != null) {
      projectRepository.save(project);
    } else {
      throw new NullPointerException();
    }
  }

  @Override
  public ProjectResponse addProject(ProjectRequest projectRequest) {
    boolean existSUser = identityClient.existUser(projectRequest.getOwner());
    if (!existSUser) {
      throw new UserNotFoundException();
    }
    Project project = Project.builder()
        .name(projectRequest.getName())
        .description(projectRequest.getDescription())
        .registered(LocalDateTime.now())
        .deadline(stringToLocalDateTime(projectRequest.getDeadline()))
        .owner(projectRequest.getOwner())
        .status(checkAndReturnStatus(projectRequest.getStatus()))
        .build();
    save(project);
    return projectToResponse(project);
  }

  @Override
  public String changeProjectStatus(ChangeStatusRequest changeStatusRequest) {
    Project project = projectRepository
        .findById(changeStatusRequest.getProjectId())
        .orElseThrow(ProjectNotFoundException::new);
    String status = checkAndReturnStatus(changeStatusRequest.getStatus());
    project.setStatus(status);
    save(project);
    return "New status: " + status;
  }

  @Override
  public String deleteProject(long projectId) {
    Project project = projectRepository
        .findById(projectId)
        .orElseThrow(ProjectNotFoundException::new);
    try {
      projectRepository.delete(project);
    } catch (Exception e) {
      throw new BadRequestException("First delete all task for this project");
    }
    return project.getName() + " project was deleted";
  }


  @Override
  public String checkAndReturnStatus(String statusInString) {
    Optional<Status> status = Arrays.stream(Status.values())
        .filter(s -> s.name().equalsIgnoreCase(statusInString))
        .findFirst();
    if (status.isPresent()) {
      return statusInString;
    }
    throw new BadRequestException("Status must be IN_WORK or DONE");
  }

  @Override
  public ProjectResponse getProjectById(Long id) {
    return projectToResponse(projectRepository
        .findById(id)
        .orElseThrow(ProjectNotFoundException::new));
  }

  @Override
  public LocalDateTime stringToLocalDateTime(String deadline) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    try {
      return LocalDateTime.parse(deadline, formatter);
    } catch (RuntimeException e) {
      throw new BadRequestException("Wrong date format, must be yyyy-MM-dd HH:mm:ss");
    }
  }

  public ProjectResponse projectToResponse(Project project) {
    return ProjectResponse.builder()
        .deadline(project.getDeadline())
        .description(project.getDescription())
        .name(project.getName())
        .status(project.getStatus())
        .build();
  }

}
