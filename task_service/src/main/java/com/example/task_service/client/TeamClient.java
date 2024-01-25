package com.example.task_service.client;

public interface TeamClient {

  boolean existsByTaskIdsContains(Long taskId);
}
