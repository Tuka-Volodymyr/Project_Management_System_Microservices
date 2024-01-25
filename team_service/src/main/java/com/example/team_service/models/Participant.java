package com.example.team_service.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Participant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long member_id;

  @ElementCollection
  private Set<Long> taskIds;

  private String email;

  private String position;

  private LocalDateTime registered;

  @ManyToOne
  @JoinColumn(name = "team_id")
  @JsonBackReference
  private Team team;
}
