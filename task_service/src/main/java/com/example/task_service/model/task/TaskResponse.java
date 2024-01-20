package com.example.task_service.model.task;

import java.util.Date;
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

  private Date deadline;

}
