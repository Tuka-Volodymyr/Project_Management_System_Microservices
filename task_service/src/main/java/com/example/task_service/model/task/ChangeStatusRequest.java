package com.example.task_service.model.task;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeStatusRequest {

  @NumberFormat
  @Min(1)
  private long taskId;

  @NotBlank
  private String status;


}
