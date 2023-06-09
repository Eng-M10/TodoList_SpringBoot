package com.muvi.todolist.repo;

import com.muvi.todolist.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {


}
