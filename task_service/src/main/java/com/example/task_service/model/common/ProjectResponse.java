package com.example.task_service.model.common;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {

  private String name;

  private String description;

  private LocalDateTime deadline;

  private String status;
}
