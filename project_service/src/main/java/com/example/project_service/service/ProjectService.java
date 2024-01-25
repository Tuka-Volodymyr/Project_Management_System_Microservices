package com.example.project_service.service;

import com.example.project_service.model.entity.Project;
import com.example.project_service.model.project.ChangeStatusRequest;
import com.example.project_service.model.project.ProjectRequest;
import com.example.project_service.model.project.ProjectResponse;
import java.time.LocalDateTime;

public interface ProjectService {

  void save(Project project);

  ProjectResponse addProject(ProjectRequest projectRequest);

  String changeProjectStatus(ChangeStatusRequest changeStatusRequest);

  String deleteProject(long projectId);

  String checkAndReturnStatus(String statusInString);

  ProjectResponse getProjectById(Long id);

  LocalDateTime stringToLocalDateTime(String deadline);
}
