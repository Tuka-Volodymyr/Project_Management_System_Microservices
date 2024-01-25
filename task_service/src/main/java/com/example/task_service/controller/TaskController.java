package com.example.task_service.controller;

import com.example.task_service.model.task.ChangeStatusRequest;
import com.example.task_service.service.TaskService;
import com.example.task_service.model.task.TaskRequest;
import com.example.task_service.model.task.TaskResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

  private final TaskService taskService;

  @PostMapping("/add")
  public ResponseEntity<TaskResponse> addTask(@RequestBody @Valid TaskRequest taskRequest) {
    return new ResponseEntity<>(taskService.addTask(taskRequest), HttpStatus.OK);
  }

  @GetMapping("/existence")
  public ResponseEntity<Boolean> existTask(@RequestParam Long taskId) {
    return new ResponseEntity<>(taskService.existenceTask(taskId), HttpStatus.OK);
  }

  @PostMapping("/change/status")
  public ResponseEntity<String> changeTaskStatus(
      @RequestBody @Valid ChangeStatusRequest changeStatusRequest) {
    return new ResponseEntity<>(taskService.changeTaskStatus(changeStatusRequest),
        HttpStatus.OK);
  }

  @GetMapping("/delete/{id}")
  public ResponseEntity<String> deleteTask(@PathVariable("id") Long id) {
    return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.OK);
  }

  @GetMapping("/existence/by")
  public ResponseEntity<Boolean> existenceTasksByProjectId(@RequestParam Long projectId) {
    return new ResponseEntity<>(taskService.existenceTasksByProjectId(projectId), HttpStatus.OK);
  }

}
