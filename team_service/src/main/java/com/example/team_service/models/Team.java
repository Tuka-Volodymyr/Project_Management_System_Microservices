package com.example.team_service.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Team {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long team_id;

  private String name;

  private LocalDateTime registered;

  @OneToMany(fetch = FetchType.EAGER)
  @JsonManagedReference
  private List<Participant> participants;


}
