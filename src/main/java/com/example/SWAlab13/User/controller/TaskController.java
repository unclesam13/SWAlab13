package com.example.SWAlab13.User.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class TaskController {

    // Temporary storage for tasks (You might want to store them in a database later)
    private List<String> tasks = new ArrayList<>();

    // Handle adding a task
    @PostMapping("/add-task")
    public String addTask(@RequestParam("task") String task, Model model) {
        tasks.add(task);
        model.addAttribute("tasks", tasks);  // Pass tasks to the view
        return "dashboard";  // Redirect to dashboard page
    }

    // Optionally, you can handle other task-related features here
}
