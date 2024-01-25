package com.example.team_service.repositories;

import com.example.team_service.models.Participant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant,Long> {

  Optional<Participant> findByEmail(String email);

  boolean existsByTaskIdsContains(Long taskId);
}
