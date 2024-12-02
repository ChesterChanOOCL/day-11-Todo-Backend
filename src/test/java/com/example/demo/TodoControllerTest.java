package com.example.demo;

import com.example.demo.Model.Todo;
import com.example.demo.TodoController.TodoController;
import com.example.demo.TodoService.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebMvcTest(TodoController.class)
@AutoConfigureJsonTesters
public class TodoControllerTest {
    @Autowired
    private MockMvc client;

    @MockBean
    private TodoService todoService;

    @Autowired
    private JacksonTester<List<Todo>> todoListJacksonTester;
    @Autowired
    private JacksonTester<Todo> todoJacksonTester;

    private List<Todo> todos;

    @BeforeEach
    void setUp() {
        todos = List.of(
                new Todo(1, "Buy groceries", false),
                new Todo(2, "Read a book", true)
        );

        when(todoService.getAllTodo()).thenReturn(todos);
        when(todoService.getTodoById(1)).thenReturn(todos.get(0));
        when(todoService.create(any(Todo.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(todoService.update(eq(1), any(Todo.class))).thenAnswer(invocation -> {
            Todo updatedTodo = invocation.getArgument(1);
            updatedTodo.setId(1);
            return updatedTodo;
        });
    }

    @Test
    void should_return_all_todos() throws Exception {
        // Given
        final List<Todo> givenTodos = todoService.getAllTodo();

        // When
        final MvcResult result = client.perform(MockMvcRequestBuilders.get("/todo")).andReturn();

        // Then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        final List<Todo> fetchedTodos = todoListJacksonTester.parseObject(result.getResponse().getContentAsString());
//        assertThat(fetchedTodos).hasSameSizeAs(givenTodos);
        for (int i = 0; i < fetchedTodos.size(); i++) {
            final Todo fetchedTodo = fetchedTodos.get(i);
            final Todo givenTodo = givenTodos.get(i);
            assertThat(fetchedTodo.getId()).isEqualTo(givenTodo.getId());
            assertThat(fetchedTodo.getText()).isEqualTo(givenTodo.getText());
//            assertThat(fetchedTodo.isDone()).isEqualTo(givenTodo.isDone());
        }
    }

    @Test
    void should_return_todo_by_id() throws Exception {
        // Given
        final Todo givenTodo = todoService.getTodoById(1);

        // When
        final MvcResult result = client.perform(MockMvcRequestBuilders.get("/todo/1")).andReturn();

        // Then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        final Todo fetchedTodo = todoJacksonTester.parseObject(result.getResponse().getContentAsString());
        assertThat(fetchedTodo.getId()).isEqualTo(givenTodo.getId());
        assertThat(fetchedTodo.getText()).isEqualTo(givenTodo.getText());
//        assertThat(fetchedTodo.isDone()).isEqualTo(givenTodo.isDone());
    }

    @Test
    void should_create_todo() throws Exception {
        // Given
        final Todo newTodo = new Todo(3, "Write tests", false);
        final String newTodoJson = todoJacksonTester.write(newTodo).getJson();

        // When
        final MvcResult result = client.perform(MockMvcRequestBuilders.post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTodoJson))
                .andReturn();

        // Then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        final Todo createdTodo = todoJacksonTester.parseObject(result.getResponse().getContentAsString());
        assertThat(createdTodo.getText()).isEqualTo(newTodo.getText());
        assertThat(createdTodo.getDone()).isEqualTo(newTodo.getDone());
    }

    @Test
    void should_update_todo() throws Exception {
        // Given
        final Todo updatedTodo = new Todo(1, "Buy groceries and cook dinner", false);
        final String updatedTodoJson = todoJacksonTester.write(updatedTodo).getJson();

        // When
        final MvcResult result = client.perform(MockMvcRequestBuilders.put("/todo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTodoJson))
                .andReturn();

        // Then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        final Todo fetchedTodo = todoJacksonTester.parseObject(result.getResponse().getContentAsString());
        assertThat(fetchedTodo.getId()).isEqualTo(updatedTodo.getId());
        assertThat(fetchedTodo.getText()).isEqualTo(updatedTodo.getText());
        assertThat(fetchedTodo.getDone()).isEqualTo(updatedTodo.getDone());
    }

    @Test
    void should_delete_todo() throws Exception {
        // When
        final MvcResult result = client.perform(MockMvcRequestBuilders.delete("/todo/1")).andReturn();

        // Then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());

    }
}