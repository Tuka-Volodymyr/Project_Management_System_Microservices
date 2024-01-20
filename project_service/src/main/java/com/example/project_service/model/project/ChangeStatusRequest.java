package com.example.project_service.model.project;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusRequest {

  @NumberFormat
  @Min(1)
  private long projectId;

  @NotBlank
  private String status;


}
