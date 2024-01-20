package com.example.task_service.service;

import com.example.task_service.model.entity.Task;
import com.example.task_service.model.task.ChangeStatusRequest;
import com.example.task_service.model.task.TaskRequest;
import com.example.task_service.model.task.TaskResponse;
import java.util.Date;

public interface TaskService {

  void save(Task task);

  TaskResponse addTask(TaskRequest taskRequest);

  String changeTaskStatus(ChangeStatusRequest changeStatusRequest);

  String deleteTask(long projectId);

  String checkAndReturnStatus(String statusInString);

  Date stringToDate(String deadline);

  boolean existenceTask(long taskId);

  TaskResponse taskToResponse(Task task);
}
