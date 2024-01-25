package com.example.task_service.repositories;

import com.example.task_service.model.entity.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

  List<Task> findByProjectId(long projectId);

}
