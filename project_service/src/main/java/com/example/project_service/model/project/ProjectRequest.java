package com.example.project_service.model.project;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {
  @NotBlank
  private String name;
  @NotBlank
  private String owner;
  @NotBlank
  private String description;
  @NotBlank
  private String deadline;
  @NotBlank
  private String status;

}
