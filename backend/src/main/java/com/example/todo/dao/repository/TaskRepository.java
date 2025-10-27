package com.example.todo.dao.repository;

import com.example.todo.dao.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT t.* FROM task AS t WHERE t.completed = '0' ORDER BY t.created_at DESC",
            countQuery = "SELECT COUNT(*) FROM task AS t WHERE t.completed = '0'",
            nativeQuery = true)
    Page<Task> findIncompleteTasks(Pageable pageable);
}
