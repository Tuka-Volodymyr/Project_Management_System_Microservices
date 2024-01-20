package com.example.project_service.controller;

import com.example.project_service.model.project.ChangeStatusRequest;
import com.example.project_service.service.ProjectService;
import com.example.project_service.model.project.ProjectRequest;
import com.example.project_service.model.project.ProjectResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

  private final ProjectService projectService;

  @PostMapping("/add")
  public ResponseEntity<ProjectResponse> addProject(
      @RequestBody @Valid ProjectRequest projectRequest) {
    return new ResponseEntity<>(projectService.addProject(projectRequest), HttpStatus.OK);
  }

  @PostMapping("/change/status")
  public ResponseEntity<String> changeProjectStatus(
      @RequestBody @Valid ChangeStatusRequest changeStatusRequest) {
    return new ResponseEntity<>(projectService.changeProjectStatus(changeStatusRequest),
        HttpStatus.OK);
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<ProjectResponse> getById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
  }

  @GetMapping("/delete/{id}")
  public ResponseEntity<String> deleteProject(@PathVariable("id") Long id) {
    return new ResponseEntity<>(projectService.deleteProject(id), HttpStatus.OK);
  }


}
