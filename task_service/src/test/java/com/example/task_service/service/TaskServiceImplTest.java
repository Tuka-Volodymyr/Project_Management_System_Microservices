package com.example.task_service.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.task_service.client.ProjectServiceClient;
import com.example.task_service.model.entity.Task;
import com.example.task_service.model.exceptions.BadRequestException;
import com.example.task_service.model.task.ChangeStatusRequest;
import com.example.task_service.model.task.TaskRequest;
import com.example.task_service.model.task.TaskResponse;
import com.example.task_service.repositories.TaskRepository;
import com.example.task_service.service.impl.TaskServiceImpl;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {

  @Mock
  private TaskRepository taskRepository;

  @Mock
  private ProjectServiceClient projectServiceClient;

  @InjectMocks
  private TaskServiceImpl taskService;


  @Test
  public void addTaskTest_Success() {
    TaskRequest taskRequest = TaskRequest.builder()
        .name("main task")
        .description("description in file")
        .deadline("2024-02-19 12:30:12")
        .project_id(2L)
        .status("IN_WORK")
        .build();

    when(projectServiceClient.getStatusOfProject(taskRequest.getProject_id()))
        .thenReturn("IN_WORK");

    TaskResponse taskResponse = taskService.addTask(taskRequest);

    assertEquals(taskRequest.getStatus(), taskResponse.getStatus());
    assertEquals(taskRequest.getDescription(), taskResponse.getDescription());
    assertEquals(
        LocalDateTime.of(2024, 2, 19, 12, 30, 12),
        taskResponse.getDeadline()
    );

  }

  @Test
  public void addTaskTest_WrongStatus_Fail() {
    TaskRequest taskRequest = TaskRequest.builder()
        .name("main task")
        .description("description in file")
        .deadline("2024-02-19 12:30:12")
        .project_id(2L)
        .status("Some status")
        .build();

    when(projectServiceClient.getStatusOfProject(taskRequest.getProject_id()))
        .thenReturn("IN_WORK");

    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> taskService.addTask(taskRequest));

    assertEquals("Status must be IN_WORK or DONE", exception.getMessage());
  }

  @Test
  public void addTaskTest_WrongDeadLine_Fail() {
    TaskRequest taskRequest = TaskRequest.builder()
        .name("main task")
        .description("description in file")
        .deadline("2024-202-1")
        .project_id(2L)
        .status("IN_work")
        .build();

    when(projectServiceClient.getStatusOfProject(taskRequest.getProject_id()))
        .thenReturn("IN_WORK");

    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> taskService.addTask(taskRequest));

    assertEquals("Wrong date format, must be yyyy-MM-dd HH:mm:ss", exception.getMessage());
  }

  @Test
  public void changeTaskStatusTest_Success() {
    ChangeStatusRequest changeStatusRequest = ChangeStatusRequest.builder()
        .taskId(1)
        .status("Done")
        .build();

    Task task = Task.builder()
        .name("Garbage collector ")
        .description("Some description")
        .project_id(2L)
        .status("In_Work")
        .registered(LocalDateTime.now())
        .deadline(LocalDateTime.of(2024, 2, 19, 12, 30, 12))
        .build();

    when(taskRepository
        .findById(changeStatusRequest.getTaskId()))
        .thenReturn(Optional.of(task));

    String response = taskService.changeTaskStatus(changeStatusRequest);

    assertEquals("New status: " + changeStatusRequest.getStatus(), response);

  }

  @Test
  public void deleteTaskStatusTest_Success() {
    long projectId = 2;

    Task task = Task.builder()
        .name("Garbage collector ")
        .description("Some description")
        .project_id(2L)
        .status("In_Work")
        .registered(LocalDateTime.now())
        .deadline(LocalDateTime.of(2024, 2, 19, 12, 30, 12))
        .build();

    when(taskRepository
        .findById(projectId))
        .thenReturn(Optional.of(task));

    String response = taskService.deleteTask(projectId);

    assertEquals(task.getName() + " task was deleted", response);

  }
}
