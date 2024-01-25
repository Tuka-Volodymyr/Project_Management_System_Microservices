package com.example.task_service.service.impl;

import com.example.task_service.client.ProjectServiceClient;
import com.example.task_service.model.entity.Task;
import com.example.task_service.model.exceptions.BadRequestException;
import com.example.task_service.model.exceptions.TaskNotFoundException;
import com.example.task_service.model.task.ChangeStatusRequest;
import com.example.task_service.repositories.TaskRepository;
import com.example.task_service.service.TaskService;
import com.example.task_service.model.task.Status;
import com.example.task_service.model.task.TaskRequest;
import com.example.task_service.model.task.TaskResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;

  private final ProjectServiceClient projectServiceClient;

  @Override
  public void save(Task task) {
    if (task != null) {
      taskRepository.save(task);
    } else {
      throw new NullPointerException();
    }
  }

  @Override
  public TaskResponse addTask(TaskRequest taskRequest) {

    String projectStatus = projectServiceClient.getStatusOfProject(taskRequest.getProject_id());
    if (!checkAndReturnStatus(projectStatus).equalsIgnoreCase(Status.IN_WORK.name())) {
      throw new BadRequestException("Project status must be IN_WORK.");
    }
    Task task = Task.builder()
        .name(taskRequest.getName())
        .description(taskRequest.getDescription())
        .project_id(taskRequest.getProject_id())
        .status(checkAndReturnStatus(taskRequest.getStatus()))
        .registered(LocalDateTime.now())
        .deadline(stringToLocalDateTime(taskRequest.getDeadline()))
        .build();

    save(task);

    return taskToResponse(task);

  }

  @Override
  public String changeTaskStatus(ChangeStatusRequest changeStatusRequest) {
    Task task = taskRepository
        .findById(changeStatusRequest.getTaskId())
        .orElseThrow(TaskNotFoundException::new);
    String status = checkAndReturnStatus(changeStatusRequest.getStatus());
    task.setStatus(status);
    save(task);
    return "New status: " + status;
  }

  @Override
  public String deleteTask(long projectId) {
    Task task = taskRepository
        .findById(projectId)
        .orElseThrow(TaskNotFoundException::new);
    try {
      taskRepository.delete(task);
    } catch (Exception e) {
      throw new BadRequestException("First finish task in team.");
    }
    return task.getName() + " task was deleted";
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
  public LocalDateTime stringToLocalDateTime(String deadline) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    try {
      return LocalDateTime.parse(deadline, formatter);
    } catch (RuntimeException e) {
      throw new BadRequestException("Wrong date format, must be yyyy-MM-dd HH:mm:ss");
    }
  }


  @Override
  public boolean existenceTask(long taskId) {
    Optional<Task> task = taskRepository.findById(taskId);
    return task.isPresent();
  }

  @Override
  public TaskResponse taskToResponse(Task task) {
    return TaskResponse.builder()
        .name(task.getName())
        .description(task.getDescription())
        .status(task.getStatus())
        .deadline(task.getDeadline())
        .build();
  }
}
