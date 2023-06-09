package com.muvi.todolist.taskcontroller;
import com.muvi.todolist.exception.ResourceNotFoundException;
import com.muvi.todolist.repo.TaskRepository;
import com.muvi.todolist.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class TaskController {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public String getAllTasks(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "list";
    }

    @GetMapping("/{id}")
    public String getTaskById(@PathVariable Long id, Model model) throws Throwable {
        Task task = (Task) taskRepository.findById(id)
                .orElseThrow();
        model.addAttribute("task", task);
        return "details";
    }

    @GetMapping("/tasks/create")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        return "create";
    }

    @PostMapping
    public String createTask(@ModelAttribute("task") Task task) {
        taskRepository.save(task);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) throws Throwable {
        Task task = (Task) taskRepository.findById(id)
                .orElseThrow();
        model.addAttribute("task", task);
        return "edit";
    }

    @PostMapping("/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute("task") Task taskDetails) throws Throwable {
        Task task = (Task) taskRepository.findById(id)
                .orElseThrow();
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        taskRepository.save(task);
        return "redirect:/";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/";
    }
}
