import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.model.Task;
import com.example.model.User;
import com.example.repository.TaskRepository;
import com.example.service.TaskService;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);

        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task :) ");
        task.setUser(user);
    }

    @Test
    void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        assertEquals("Test Task :) ", createdTask.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testGetUserTasks() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        when(taskRepository.findByUserId(1L)).thenReturn(taskList);

        List<Task> retrievedTasks = taskService.getUserTasks();

        assertEquals(1, retrievedTasks.size());
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }
}
