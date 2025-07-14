package ru.mentee.power.methods.taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;


/**
 * Тесты для класса TaskManager
 */
public class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();

        // Добавляем тестовые задачи
        taskManager.addTask(
                "Срочная задача",
                "Выполнить скорее",
                createDate(2023, 5, 15),
                Task.Priority.HIGH
        );
        taskManager.addTask(
                "Обычная задача",
                "В течение недели",
                createDate(2023, 6, 1),
                Task.Priority.MEDIUM
        );
        taskManager.addTask(
                "Несрочная задача",
                "Когда будет время",
                createDate(2023, 7, 1),
                Task.Priority.LOW
        );
        taskManager.addTask("Задача без описания");
    }

    /**
     * Тест добавления задачи
     */
    @Test
    void testAddTask() {
        // Добавляем новую задачу
        Task newTask = taskManager.addTask(
                "Проверка добавления",
                "Описание задачи",
                createDate(2023, 8, 10),
                Task.Priority.MEDIUM
        );

        // Проверяем поля созданной задачи
        assertThat(newTask.getId()).isPositive();
        assertThat(newTask.getTitle()).isEqualTo("Проверка добавления");
        assertThat(newTask.getDescription()).isEqualTo("Описание задачи");
        assertThat(newTask.getDueDate()).isEqualTo(createDate(2023, 8, 10));
        assertThat(newTask.getPriority()).isEqualTo(Task.Priority.MEDIUM);

        // Проверяем, что задача попала в общий список
        List<Task> allTasks = taskManager.getAllTasks();
        assertThat(allTasks).contains(newTask);
    }

    /**
     * Тест получения задачи по ID
     */
    @Test
    void testGetTaskById() {
        // Берём первую задачу из списка
        Task first = taskManager.getAllTasks().get(0);
        int id = first.getId();

        // Получаем задачу по её ID
        Task fetched = taskManager.getTaskById(id);
        assertThat(fetched).isNotNull();
        assertThat(fetched.getId()).isEqualTo(id);
        assertThat(fetched.getTitle()).isEqualTo(first.getTitle());

        // Граничный случай: задача с несуществующим ID
        Task missing = taskManager.getTaskById(-999);
        assertThat(missing).isNull();
    }

    /**
     * Тест фильтрации задач по приоритету
     */
    @Test
    void testGetTasksByPriority() {
        // Высокий приоритет
        List<Task> high = taskManager.getTasksByPriority(Task.Priority.HIGH);
        assertThat(high)
                .hasSize(1)
                .allMatch(t -> t.getPriority() == Task.Priority.HIGH)
                .extracting(Task::getTitle)
                .containsExactly("Срочная задача");

        // Низкий приоритет
        List<Task> low = taskManager.getTasksByPriority(Task.Priority.LOW);
        assertThat(low)
                .hasSize(1)
                .allMatch(t -> t.getPriority() == Task.Priority.LOW)
                .extracting(Task::getTitle)
                .containsExactly("Несрочная задача");

        // Приоритета, которого нет
        List<Task> none = taskManager.getTasksByPriority(null);
        assertThat(none).isEmpty();
    }

    /**
     * Тест поиска задач по тексту
     */
    @Test
    void testSearchTasks() {
        // Поиск по части заголовка
        List<Task> foundTitle = taskManager.searchTasks("Срочн");
        assertThat(foundTitle)
                .hasSize(2)
                .first()
                .extracting(Task::getTitle)
                .isEqualTo("Срочная задача");

        // Поиск по части описания
        List<Task> foundDesc = taskManager.searchTasks("время");
        assertThat(foundDesc)
                .hasSize(1)
                .first()
                .extracting(Task::getDescription)
                .isEqualTo("Когда будет время");

        // Ничего не найдено
        List<Task> nothing = taskManager.searchTasks("абракадабра");
        assertThat(nothing).isEmpty();
    }

    /**
     * Тест сортировки задач по приоритету
     */
    @Test
    void testSortTasksByPriority() {
        // Перемешаем список специально (если у вас есть метод shuffle)
        // taskManager.shuffleTasks();
        // Или просто проверим, что по умолчанию список не отсортирован:

        List<Task> before = taskManager.getAllTasks();
        // допустим, до сортировки они идут в порядке добавления:
        assertThat(before)
                .extracting(Task::getPriority)
                .containsExactly(
                        Task.Priority.HIGH,
                        Task.Priority.MEDIUM,
                        Task.Priority.LOW,
                        Task.Priority.MEDIUM // т.к. "Задача без описания" по умолчанию MEDIUM
                );

        // Выполняем сортировку
        taskManager.sortTasksByPriority();

        // После сортировки ожидаем порядок HIGH → MEDIUM → MEDIUM → LOW
        List<Task> after = taskManager.getAllTasks();
        assertThat(after)
                .extracting(Task::getPriority)
                .containsExactly(
                        Task.Priority.HIGH,
                        Task.Priority.MEDIUM,
                        Task.Priority.MEDIUM,
                        Task.Priority.LOW
                );
    }


    /**
     * Вспомогательный метод для создания даты
     */
    private static Date createDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
}
