package com.example.task_service.model.task;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
  @NotBlank
  private String name;
  @NotBlank
  private String description;
  @NumberFormat
  private long project_id;
  @NotBlank
  private String status;
  @NotBlank
  private String deadline;
}
