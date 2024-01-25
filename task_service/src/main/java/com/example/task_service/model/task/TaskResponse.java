package com.example.task_service.model.task;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
  private String name;

  private String description;

  private String status;

  private LocalDateTime deadline;

}
